package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Degree;
import static edu.wpi.first.units.Units.MetersPerSecond;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator3d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.Constants.Controller;
import frc.robot.Constants.Gyro;
import frc.robot.subsystems.swerve.MAXSwerveModule;
import frc.robot.subsystems.swerve.SwerveConstants.DriveConstants;

public class SwerveSubsys extends SubsystemBase {
    // This is directly copied from MAXSwerve template

    // Create MAXSwerveModules
    private final MAXSwerveModule FL_MODULE = Constants.SoftwareObjects.FRONT_LEFT_MODULE;
    private final MAXSwerveModule FR_MODULE = Constants.SoftwareObjects.FRONT_RIGHT_MODULE;
    private final MAXSwerveModule BL_MODULE = Constants.SoftwareObjects.BACK_LEFT_MODULE;
    private final MAXSwerveModule BR_MODULE = Constants.SoftwareObjects.BACK_RIGHT_MODULE;
    private final NetworkTableInstance NETWORK = Constants.SoftwareObjects.NETWORK_TABLE_INSTANCE;

    private DoubleEntry positionXEntry = NETWORK.getDoubleTopic("PositionX").getEntry(0);
    private DoubleEntry positionYEntry = NETWORK.getDoubleTopic("PositionY").getEntry(0);
    private DoubleEntry positionZEntry = NETWORK.getDoubleTopic("PositionZ").getEntry(0);

    private Field2d field = new Field2d();
    // private GenericEntry field2dEntry = network.getTopic("Field2d").getGenericEntry();

    private final Pigeon2 GYRO = Gyro.GYRO;
    private SwerveDrivePoseEstimator3d odometry = Constants.SoftwareObjects.POSE_ESTIMATOR;
    private SwerveDriveKinematics kinematics = DriveConstants.DRIVE_KINEMATICS;

    private static SwerveModuleState xFormation1 = new SwerveModuleState(MetersPerSecond.of(0.0), new Rotation2d(Degree.of(45)));
    private static SwerveModuleState xFormation2 = new SwerveModuleState(MetersPerSecond.of(0.0), new Rotation2d(Degree.of(135)));

    public SwerveSubsys() {
        super();
        // The MaxSwerve template does this, no clue what this is
        HAL.report(tResourceType.kResourceType_RobotDrive, tInstances.kRobotDriveSwerve_MaxSwerve);

        positionXEntry.set(0.0);
        positionYEntry.set(0.0);
        positionZEntry.set(0.0);

        GYRO.reset();

        field.setRobotPose(odometry.getEstimatedPosition().getMeasureX(), odometry.getEstimatedPosition().getMeasureY(), GYRO.getRotation2d());
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

        // System.out.println(FL_MODULE.getState().angle);
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

        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered, GYRO.getRotation3d().toRotation2d()) : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, DriveConstants.MAX_SPEED_METERS_PER_SECOND);
        FL_MODULE.setDesiredState(swerveModuleStates[0]);
        FR_MODULE.setDesiredState(swerveModuleStates[1]);
        BL_MODULE.setDesiredState(swerveModuleStates[2]);
        BR_MODULE.setDesiredState(swerveModuleStates[3]);
    }

    public void setXFormation() {
        FL_MODULE.setDesiredState(xFormation1);
        FR_MODULE.setDesiredState(xFormation2);
        BL_MODULE.setDesiredState(xFormation2);
        BR_MODULE.setDesiredState(xFormation1);
    }
}
