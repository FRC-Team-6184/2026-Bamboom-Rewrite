package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Inch;
import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.MetersPerSecond;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator3d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.Odometry;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotMap;
import frc.robot.RobotMap.Controller;
import frc.robot.RobotMap.DigitalValues;
import frc.robot.RobotMap.Gyro;
import frc.robot.RobotMap.MotorControllers;
import frc.robot.RobotMap.SoftwareObjects;
import frc.robot.subsystems.swerve.MAXSwerveModule;
import frc.robot.subsystems.swerve.SwerveConstants;
import frc.robot.subsystems.swerve.SwerveConstants.DriveConstants;
import frc.robot.subsystems.swerve.SwerveConstants.ModuleConstants;
import frc.robot.utilities.DumbGyroWrapper;

public class SwerveSubsys extends SubsystemBase {
    // This is directly copied from MAXSwerve template

    // private GameController controller = Controller.GAME_CONTROLLER;
    // Variables used in the run lambda:
    private double x;
    private double y;
    private double rot;
    private boolean canRotate = true;
    private boolean canMove = true;
    private final CommandXboxController controller = Controller.XBOX;

    // Create MAXSwerveModules
    private final MAXSwerveModule m_frontLeft = RobotMap.SoftwareObjects.FRONT_LEFT_MODULE;
    private final MAXSwerveModule m_frontRight = RobotMap.SoftwareObjects.FRONT_RIGHT_MODULE;
    private final MAXSwerveModule m_rearLeft = RobotMap.SoftwareObjects.BACK_LEFT_MODULE;
    private final MAXSwerveModule m_rearRight = RobotMap.SoftwareObjects.BACK_RIGHT_MODULE;
    private final NetworkTableInstance network = RobotMap.SoftwareObjects.networkTableInstance;

    private DoubleEntry positionXEntry = network.getDoubleTopic("PositionX").getEntry(0);
    private DoubleEntry positionYEntry = network.getDoubleTopic("PositionY").getEntry(0);
    private DoubleEntry positionZEntry = network.getDoubleTopic("PositionZ").getEntry(0);

    private Field2d field = new Field2d();
    // private GenericEntry field2dEntry = network.getTopic("Field2d").getGenericEntry();

    private Pigeon2 gyro = Gyro.GYRO;
    private SwerveDrivePoseEstimator3d odometry = RobotMap.SoftwareObjects.poseEstimator;
    private SwerveDriveKinematics kinematics = DriveConstants.kDriveKinematics;
    private RobotConfig autoConfig;

    private PIDConstants autoDrivePID = new PIDConstants(1.0, 0, 0);
    private PIDConstants autoRotatePID = new PIDConstants(1.0, 0, 0);
    private PPHolonomicDriveController autoDriveController = new PPHolonomicDriveController(autoDrivePID, autoRotatePID);

    private static SwerveModuleState xFormation1 = new SwerveModuleState(MetersPerSecond.of(0.0), new Rotation2d(Degree.of(45)));
    private static SwerveModuleState xFormation2 = new SwerveModuleState(MetersPerSecond.of(0.0), new Rotation2d(Degree.of(135)));

    private double desiredRot = 0.0;

    private PathPlannerPath path;

    private DumbGyroWrapper odometryGyro = new DumbGyroWrapper(gyro);


    public SwerveSubsys() {
        super();
        // The MaxSwerve template does this, no clue what this is
        HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);

        positionXEntry.set(0.0);
        positionYEntry.set(0.0);
        positionZEntry.set(0.0);

        // field.setRobotPose(Meter.convertFrom(651.22, Inch) - 1.88, Meter.convertFrom(158.84, Inch), new Rotation2d());



        gyro.reset();
        // gyro.setYaw(90);

        try {
            autoConfig = RobotConfig.fromGUISettings(); //TODO: find the true max drive speed (0.85x free )
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        try {
            path = PathPlannerPath.fromPathFile("Test Path Red");
            if (path.getStartingHolonomicPose().isPresent()) {
                odometry.resetPosition(odometryGyro.getRotation3d(), new SwerveModulePosition[] {m_frontLeft.getPosition(), m_frontRight.getPosition(), m_rearLeft.getPosition(), m_rearRight.getPosition()}, new Pose3d(path.getStartingHolonomicPose().get()));
            } else {
                odometry.resetPosition(odometryGyro.getRotation3d(), new SwerveModulePosition[] {m_frontLeft.getPosition(), m_frontRight.getPosition(), m_rearLeft.getPosition(), m_rearRight.getPosition()}, new Pose3d(new Pose2d(12.866, 7.498, gyro.getRotation2d())));
                System.out.println("Auto path didn't load mf");
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        // path.get

        field.setRobotPose(odometry.getEstimatedPosition().getMeasureX(), odometry.getEstimatedPosition().getMeasureY(), gyro.getRotation2d());
        SmartDashboard.putData(field);
        SmartDashboard.updateValues();

        AutoBuilder.configure(this::get2dPose, this::resetPose, this::getRobotRelativeChassisSpeeds, (speeds, feedforwards) -> driveRobotAutonomous(speeds), autoDriveController, autoConfig, this::determineAlliance, this);
    }

    // Mostly copied from MaxSwerve template, simply updates
    // the odometry every cycle
    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        odometry.update(odometryGyro.getRotation3d(), new SwerveModulePosition[] {m_frontLeft.getPosition(), m_frontRight.getPosition(), m_rearLeft.getPosition(), m_rearRight.getPosition()});

        Pose3d pos = odometry.getEstimatedPosition();
        positionXEntry.set(pos.getX());
        positionYEntry.set(pos.getY());
        positionZEntry.set(pos.getZ());

        field.setRobotPose(pos.toPose2d());
        SmartDashboard.putData(field);
        SmartDashboard.updateValues();

        // System.out.println(m_frontLeft.getState().angle);
    }

    int counter = 0;

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed        Speed of the robot in the x direction (forward).
     * @param ySpeed        Speed of the robot in the y direction (sideways).
     * @param rot           Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the
     *                      field.
     */
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        // Convert the commanded speeds into the correct units for the drivetrain
        double xSpeedDelivered = xSpeed * DriveConstants.MAX_SPEED_METERS_PER_SECOND;
        double ySpeedDelivered = ySpeed * DriveConstants.MAX_SPEED_METERS_PER_SECOND;
        double rotDelivered = rot * DriveConstants.MAX_ANGULAR_SPEED;

        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered, odometryGyro.getRotation3d().toRotation2d()) : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DriveConstants.MAX_SPEED_METERS_PER_SECOND);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_rearLeft.setDesiredState(swerveModuleStates[2]);
        m_rearRight.setDesiredState(swerveModuleStates[3]);
    }

    /**
     * Run periodically during teleop
     * 
     * @return
     */

    // TODO: Make this a separate command class
    public Command teleopDrive() {
        return run(() -> {
            if (canMove) {
                // Done this way in order to easily enforce controller deadzones since this
                // isn't already done in drive()

                if (controller.getHID().getRightBumperButton()) {
                    x = -0.3;
                } else if (controller.getHID().getLeftBumperButton()) {
                    x = 0.3;
                } else {
                    x = 0;
                }
                x = Math.abs(x) > RobotMap.DigitalValues.CONTROLLER_DEADZONE ? x : 0.0;

                if (controller.getRightTriggerAxis() > 0) {
                    y = controller.getRightTriggerAxis() * 0.3;
                } else if (controller.getLeftTriggerAxis() > 0) {
                    y = -controller.getLeftTriggerAxis() * 0.3;
                } else {
                    y = 0;
                }
                y = Math.abs(y) > RobotMap.DigitalValues.CONTROLLER_DEADZONE ? y : 0.0; // Both X and Y are reversed in order to make the shooter the front of the robot

                if (Math.abs(x) >= 0.95 && Math.abs(y) <= 0.2) {
                    x = 1 * Math.signum(x);
                    y = 0;
                } else if (Math.abs(y) >= 0.95 && Math.abs(x) <= 0.2) {
                    x = 0;
                    y = 1 * Math.signum(y);
                }
            } else {
                x = 0;
                y = 0;
            }

            // System.out.println(x + " | " + y);
            if (canRotate) {
                rot = -controller.getLeftX();
                rot = Math.abs(rot) > RobotMap.DigitalValues.CONTROLLER_DEADZONE ? rot * 0.85 : 0.0; //rot * -0.85 to reverse direction of rotation and slow it down since it was overly responsive
            } else {
                rot = desiredRot;
            }
            // TODO: Set this back to true when robot is in better shape, false to be easier
            // to work with for now.
            // Realistically, it needs to be possible to make it not field relative, maybe a
            // hold or something.
            drive(x, -y, rot * 0.45, false);
        });
    }

    public boolean getCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean getCanRotate() {
        return canRotate;
    }

    public void setCanRotate(boolean canRotate) {
        this.canRotate = canRotate;
    }

    private Pose2d get2dPose() {
        return odometry.getEstimatedPosition().toPose2d();
    }

    private void resetPose(Pose2d newPose) {
        odometry.resetPose(new Pose3d(newPose));
    }

    private ChassisSpeeds getRobotRelativeChassisSpeeds() { //TODO: check that the wheel circumference is accurate
        // System.out.println(m_frontLeft.getState().angle);

        return kinematics.toChassisSpeeds(m_frontLeft.getState(), m_frontRight.getState(), m_rearLeft.getState(), m_rearRight.getState());
    }

    private void driveRobotAutonomous(ChassisSpeeds speeds) {
        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(speeds);

        // for (SwerveModuleState state : swerveModuleStates) {
        //     double temp = state.angle.getDegrees() + 90;
        //     if (temp >= 180) {
        //         temp -= 360;
        //     }
        //     state.angle = new Rotation2d(Degree.of(temp));
        // }

        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DriveConstants.MAX_SPEED_METERS_PER_SECOND);
        m_frontLeft.setDesiredState(swerveModuleStates[0]);
        m_frontRight.setDesiredState(swerveModuleStates[1]);
        m_rearLeft.setDesiredState(swerveModuleStates[2]);
        m_rearRight.setDesiredState(swerveModuleStates[3]);

        System.out.println(swerveModuleStates[0].angle);
    }

    private boolean determineAlliance() {
        var alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
            return alliance.get() == DriverStation.Alliance.Red;
        }
        return false;
    }

    public void setXFormation() {
        m_frontLeft.setDesiredState(xFormation1);
        m_frontRight.setDesiredState(xFormation2);
        m_rearLeft.setDesiredState(xFormation2);
        m_rearRight.setDesiredState(xFormation1);
    }

    public void setDesiredRot(double desiredRot) {
        this.desiredRot = desiredRot;
    }


}
