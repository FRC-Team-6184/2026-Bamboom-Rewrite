package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.Inch;
import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.MetersPerSecond;
import com.ctre.phoenix6.hardware.Pigeon2;
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
import frc.robot.Constants;
import frc.robot.Constants.Controller;
import frc.robot.Constants.DigitalValues;
import frc.robot.Constants.Gyro;
import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.SoftwareObjects;
import frc.robot.subsystems.swerve.MAXSwerveModule;
import frc.robot.subsystems.swerve.SwerveConstants;
import frc.robot.subsystems.swerve.SwerveConstants.DriveConstants;
import frc.robot.subsystems.swerve.SwerveConstants.ModuleConstants;

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
    private final MAXSwerveModule m_frontLeft = Constants.SoftwareObjects.FRONT_LEFT_MODULE;
    private final MAXSwerveModule m_frontRight = Constants.SoftwareObjects.FRONT_RIGHT_MODULE;
    private final MAXSwerveModule m_rearLeft = Constants.SoftwareObjects.BACK_LEFT_MODULE;
    private final MAXSwerveModule m_rearRight = Constants.SoftwareObjects.BACK_RIGHT_MODULE;
    private final NetworkTableInstance network = Constants.SoftwareObjects.networkTableInstance;

    private DoubleEntry positionXEntry = network.getDoubleTopic("PositionX").getEntry(0);
    private DoubleEntry positionYEntry = network.getDoubleTopic("PositionY").getEntry(0);
    private DoubleEntry positionZEntry = network.getDoubleTopic("PositionZ").getEntry(0);

    private Field2d field = new Field2d();
    // private GenericEntry field2dEntry = network.getTopic("Field2d").getGenericEntry();

    private Pigeon2 gyro = Gyro.GYRO;
    private SwerveDrivePoseEstimator3d odometry = Constants.SoftwareObjects.poseEstimator;
    private SwerveDriveKinematics kinematics = DriveConstants.kDriveKinematics;

    private static SwerveModuleState xFormation1 = new SwerveModuleState(MetersPerSecond.of(0.0), new Rotation2d(Degree.of(45)));
    private static SwerveModuleState xFormation2 = new SwerveModuleState(MetersPerSecond.of(0.0), new Rotation2d(Degree.of(135)));

    private double desiredRot = 0.0;

    public SwerveSubsys() {
        super();
        // The MaxSwerve template does this, no clue what this is
        HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);

        positionXEntry.set(0.0);
        positionYEntry.set(0.0);
        positionZEntry.set(0.0);

        gyro.reset();

        field.setRobotPose(odometry.getEstimatedPosition().getMeasureX(), odometry.getEstimatedPosition().getMeasureY(), gyro.getRotation2d());
        SmartDashboard.putData(field);
        SmartDashboard.updateValues();

    }

    // Mostly copied from MaxSwerve template, simply updates
    // the odometry every cycle
    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        Pose3d pos = odometry.getEstimatedPosition();
        positionXEntry.set(pos.getX());
        positionYEntry.set(pos.getY());
        positionZEntry.set(pos.getZ());

        field.setRobotPose(pos.toPose2d());
        SmartDashboard.putData(field);
        SmartDashboard.updateValues();

        // System.out.println(m_frontLeft.getState().angle);
    }

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

        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered, gyro.getRotation3d().toRotation2d()) : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
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
                // Done this way in order to easily enforce controller deadzones since this
                // isn't already done in drive()

                if (controller.getHID().getRightBumperButton()) {
                    x = -0.3;
                } else if (controller.getHID().getLeftBumperButton()) {
                    x = 0.3;
                } else {
                    x = 0;
                }
                x = Math.abs(x) > Constants.DigitalValues.CONTROLLER_DEADZONE ? x : 0.0;

                if (controller.getRightTriggerAxis() > 0) {
                    y = controller.getRightTriggerAxis() * 0.3;
                } else if (controller.getLeftTriggerAxis() > 0) {
                    y = -controller.getLeftTriggerAxis() * 0.3;
                } else {
                    y = 0;
                }
                y = Math.abs(y) > Constants.DigitalValues.CONTROLLER_DEADZONE ? y : 0.0; // Both X and Y are reversed in order to make the shooter the front of the robot

                if (Math.abs(x) >= 0.95 && Math.abs(y) <= 0.2) {
                    x = 1 * Math.signum(x);
                    y = 0;
                } else if (Math.abs(y) >= 0.95 && Math.abs(x) <= 0.2) {
                    x = 0;
                    y = 1 * Math.signum(y);
                }

            // TODO: Set this back to true when robot is in better shape, false to be easier
            // to work with for now.
            // Realistically, it needs to be possible to make it not field relative, maybe a
            // hold or something.
            drive(x, y, rot, false);
        });
    }

    public void setXFormation() {
        m_frontLeft.setDesiredState(xFormation1);
        m_frontRight.setDesiredState(xFormation2);
        m_rearLeft.setDesiredState(xFormation2);
        m_rearRight.setDesiredState(xFormation1);
    }
}
