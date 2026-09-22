package frc.robot;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator3d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
import frc.robot.subsystems.swerve.MAXSwerveModule;
import frc.robot.subsystems.swerve.SwerveConstants.DriveConstants;

/*
 * Hardware CAN IDs: (Verify that all are correct sometime) All motor controllers below, down to the BR Swerve Turn, are SparkMax Front Left Swerve Drive - 1 | Neo (Rev Robotics) Front Left Swerve Turn - 2 | Neo 550 (Rev Robotics) Front Right Swerve Drive - 3 | Neo (Rev Robotics) Front Right Swerve Turn - 4 | Neo 550 (Rev Robotics) Back Left Swerve Drive - 5 | Neo (Rev Robotics) Back Left Swerve Turn - 6 | Neo 550 (Rev Robotics) Back Right Swerve Drive - 7 | Neo (Rev Robotics) Back Right Swerve Turn - 8 | Neo 550 (Rev Robotics)
 * 
 * All motor controllers below, down to the blender motor, are TalonFX Top Wheel of Shooter - 11 | Kraken (CTRE) (Motor for the top wheel, physically this motor is actually on the bottom of the shooter) Bottom Wheel of Shooter - 13 | Falcon 500 (CTRE) All motor controllers below, down to the blender motor, are TalonFX Top Wheel of Shooter - 11 | Kraken (CTRE) (Motor for the top wheel, physically this motor is actually on the bottom of the shooter) Bottom Wheel of Shooter - 13 | Falcon 500 (CTRE)
 * 
 * Up and Down Intake Motor - 10 | Kraken (CTRE) Active Intake Motor - 12 | Falcon 500 (CTRE) (Not entirely sure this is actually a Falcon 500) Up and Down Intake Motor - 10 | Kraken (CTRE) Active Intake Motor - 12 | Falcon 500 (CTRE) (Not entirely sure this is actually a Falcon 500)
 * 
 * Blender Motor - 9 | Falcon 500 (CTRE) Blender Motor - 9 | Falcon 500 (CTRE)
 * 
 * Gyro (Pigeon2) - 20 PDH - 21 Gyro (Pigeon2) - 20 PDH - 21
 * 
 * PWM Port 3 for LEDs
 */

/**
 * TODO: Update the readme for this class
 * TODO: Add another inner class for keeping track of digital values?
 * This class holds important information regarding hardware and related
 * things like CAN ID's, Motor controllers, Chassis measurements, etc.
 */
public final class Constants {
    public static final class Subsystems {
        public static final ShooterSubsys SHOOTER_SUBSYS = new ShooterSubsys();
        public static final SwerveSubsys SWERVE_SUBSYS = new SwerveSubsys();
    }

    public static final class Gyro {
        public static final Pigeon2 GYRO = new Pigeon2(CAN_IDs.GYRO_ID);
    }

    public static final class Controller {
        public static final int XBOX_P = 0;
        public static final int PS5_P = 1;

        public static final CommandXboxController XBOX = new CommandXboxController(XBOX_P);
        public static final CommandPS5Controller PS5 = new CommandPS5Controller(PS5_P);
    }

    // TODO: reorder can ids and make them more logical than what is currently here
    // (hardware side)
    public static final class CAN_IDs {
        // Shooter
        public static final int SHOOTER_KICKER_WHEEL_ID = 13;
        public static final int SHOOTER_LEFT_WHEEL_ID = 11; //Left Shooter motor when facing the Intake
        public static final int SHOOTER_RIGHT_WHEEL_ID = 12; //Right Shooter motor when facing the intake

        // Intake
        public static final int INTAKE_PIVOT_MOTOR_ID = 10;
        public static final int INTAKE_ACTIVE_MOTORS_ID = -1; // Two motors should both have this ID
        // public static final int INTAKE_ACTIVE_LEFT_MOTOR_ID = -1; 
        // public static final int INTAKE_ACTIVE_RIGHT_MOTOR_ID = -1;

        // Blender
        public static final int BLENDER_MOTOR_ID = 9;

        // Swerve
        public static final int FL_DRIVE_MOTOR_ID = 5;
        public static final int FL_TURN_MOTOR_ID = 6;

        public static final int FR_DRIVE_MOTOR_ID = 1;
        public static final int FR_TURN_MOTOR_ID = 2;

        public static final int BL_DRIVE_MOTOR_ID = 7;
        public static final int BL_TURN_MOTOR_ID = 8;

        public static final int BR_DRIVE_MOTOR_ID = 3;
        public static final int BR_TURN_MOTOR_ID = 4;

        // Other
        public static final int GYRO_ID = 20;
    }

    public static final class MotorConstants {
        public static final double TOP_SHOOTER_KS = 0.029245;
        public static final double TOP_SHOOTER_KV = 0.11449;
        public static final double TOP_SHOOTER_KA = 0.005078;
        public static final double TOP_SHOOTER_KP = 0.070927;
        public static final Slot0Configs TOP_SHOOTER_CONFIG = new Slot0Configs().withKS(TOP_SHOOTER_KS).withKV(TOP_SHOOTER_KV).withKA(TOP_SHOOTER_KA).withKP(TOP_SHOOTER_KP);

        public static final double KICKER_SHOOTER_KS = 0.027235;
        public static final double KICKER_SHOOTER_KV = 0.11021;
        public static final double KICKER_SHOOTER_KA = 0.0019461;
        public static final double KICKER_SHOOTER_KP = 0.13694;
        public static final Slot0Configs KICKER_SHOOTER_CONFIG = new Slot0Configs().withKS(KICKER_SHOOTER_KS).withKV(KICKER_SHOOTER_KV).withKA(KICKER_SHOOTER_KA).withKP(KICKER_SHOOTER_KP);

        //TODO: check if this motor needs to be retested, things seem to have changed here for some reason physically
        public static final double BLENDER_SHOOTER_KS = 0.049802;
        public static final double BLENDER_SHOOTER_KV = 0.11111;
        public static final double BLENDER_SHOOTER_KA = 0.0029793;
        public static final double BLENDER_SHOOTER_KP = 0.14905;
        public static final Slot0Configs BLENDER_SHOOTER_CONFIG = new Slot0Configs().withKS(BLENDER_SHOOTER_KS).withKV(BLENDER_SHOOTER_KV).withKA(BLENDER_SHOOTER_KA).withKP(BLENDER_SHOOTER_KP);

        //TODO: Find the feedforward constants because we haven't found them yet
        public static final double ACTIVE_INTAKE_KS = 0;
        public static final double ACTIVE_INTAKE_KV = 0;
        public static final double ACTIVE_INTAKE_KA = 0;
        public static final double ACTIVE_INTAKE_KP = 0;
        public static final Slot0Configs ACTIVE_INTAKE_CONFIG = new Slot0Configs().withKS(ACTIVE_INTAKE_KS).withKV(ACTIVE_INTAKE_KV).withKA(ACTIVE_INTAKE_KA).withKP(ACTIVE_INTAKE_KP);
    }

    public static final class MotorControllers {
        // Shooter Motors
        public static final TalonFX BOTTOM_SHOOTER_WHEEL = new TalonFX(CAN_IDs.SHOOTER_KICKER_WHEEL_ID);
        public static final TalonFX TOP_SHOOTER_WHEEL = new TalonFX(CAN_IDs.SHOOTER_LEFT_WHEEL_ID);

        // Swerve
        // Left-side Drive Motors
        public static final SparkMax FL_DRIVE_MOTOR = new SparkMax(CAN_IDs.FL_DRIVE_MOTOR_ID, MotorType.kBrushless);
        public static final SparkMax BL_DRIVE_MOTOR = new SparkMax(CAN_IDs.BL_DRIVE_MOTOR_ID, MotorType.kBrushless);
        // Left-side Turn Motors
        public static final SparkMax FL_TURN_MOTOR = new SparkMax(CAN_IDs.FL_TURN_MOTOR_ID, MotorType.kBrushless);
        public static final SparkMax BL_TURN_MOTOR = new SparkMax(CAN_IDs.BL_TURN_MOTOR_ID, MotorType.kBrushless);

        // Right-side Drive Motors
        public static final SparkMax FR_DRIVE_MOTOR = new SparkMax(CAN_IDs.FR_DRIVE_MOTOR_ID, MotorType.kBrushless);
        public static final SparkMax BR_DRIVE_MOTOR = new SparkMax(CAN_IDs.BR_DRIVE_MOTOR_ID, MotorType.kBrushless);
        // Right-side Turn Motors
        public static final SparkMax FR_TURN_MOTOR = new SparkMax(CAN_IDs.FR_TURN_MOTOR_ID, MotorType.kBrushless);
        public static final SparkMax BR_TURN_MOTOR = new SparkMax(CAN_IDs.BR_TURN_MOTOR_ID, MotorType.kBrushless);


            //TODO: Make intake work :)
        // Intake
        public static final TalonFX PIVOT_INTAKE_MOTOR = new TalonFX(CAN_IDs.INTAKE_PIVOT_MOTOR_ID); // Check to make sure this ID is right
        public static final TalonFX ACTIVE_INTAKE_MOTORS = new TalonFX(CAN_IDs.INTAKE_ACTIVE_MOTORS_ID); // Currently set to -1

        // Blender
        public static final TalonFX BLENDER_MOTOR = new TalonFX(CAN_IDs.BLENDER_MOTOR_ID); // Check to make sure this ID is right
    }

    public static final class Chassis {
        /**
         * This assumes your robot is rectangular.
         * TRACK_WIDTH is the distance between the left and right wheels,
         * I'm using the distance between the left and right drive motors for this.
         * WHEEL_BASE is the distance between the front and back wheels,
         * I'm using the same reference point for this: centers of motors.
         */

        // Distance between centers of right and left wheels on robot
        public static final double TRACK_WIDTH = Units.inchesToMeters(21.525); // This may be off, but we'll see. Measurement taken via CAD

        // Distance between front and back wheels on robot
        public static final double WHEEL_BASE = Units.inchesToMeters(21.525);

        // public static final RobotConfig ROBOT_CONFIGURATION = new RobotConfig(null, null, null, null);
    }

    // Software things below
    public static final class DigitalInputOutput {
        public static final DigitalInput INTAKE_LIMIT_SWITCH = new DigitalInput(6); //This does exist! Hooray!
        // public static final DigitalInput INTAKE_BOTTOM_LIMIT_SWITCH = new DigitalInput(1); THIS DOES NOT EXIST
    }

    public static final class DigitalValues {
        public static final double SUPER_LOW = 0.05;
        public static final double LOW = 0.33;
        public static final double MEDIUM = 0.66;
        public static final double HIGH = 1;

        public static final double INTAKE_PIVOT = 0.2;
        public static final double INTAKE_SPEED = -0.3;

        public static final double CONTROLLER_DEADZONE = 0.12;

        public static final double SHOOTER_LOW_SPEED = 1500.0 / 60.0;
        public static final double SHOOTER_HIGH_SPEED = 2500.0 / 60.0;
        public static final double SHOOTER_BOTTOM_SPEED = -50;
    }

    public static final class SoftwareObjects {
        public static final NetworkTableInstance NETWORK_TABLE_INSTANCE = NetworkTableInstance.getDefault();

        public static final MAXSwerveModule FRONT_LEFT_MODULE = new MAXSwerveModule(MotorControllers.FL_DRIVE_MOTOR, MotorControllers.FL_TURN_MOTOR, DriveConstants.FRONT_LEFT_CHASSIS_ANGULAR_OFFSET);
        public static final MAXSwerveModule FRONT_RIGHT_MODULE = new MAXSwerveModule(MotorControllers.FR_DRIVE_MOTOR, MotorControllers.FR_TURN_MOTOR, DriveConstants.FRONT_RIGHT_CHASSIS_ANGULAR_OFFSET);
        public static final MAXSwerveModule BACK_LEFT_MODULE = new MAXSwerveModule(MotorControllers.BL_DRIVE_MOTOR, MotorControllers.BL_TURN_MOTOR, DriveConstants.BACK_LEFT_CHASSIS_ANGULAR_OFFSET);
        public static final MAXSwerveModule BACK_RIGHT_MODULE = new MAXSwerveModule(MotorControllers.BR_DRIVE_MOTOR, MotorControllers.BR_TURN_MOTOR, DriveConstants.BACK_RIGHT_CHASSIS_ANGULAR_OFFSET);
        public static final SwerveDrivePoseEstimator3d POSE_ESTIMATOR = new SwerveDrivePoseEstimator3d(DriveConstants.kDriveKinematics, Gyro.GYRO.getRotation3d(), new SwerveModulePosition[] {FRONT_LEFT_MODULE.getPosition(), FRONT_RIGHT_MODULE.getPosition(), BACK_LEFT_MODULE.getPosition(), BACK_RIGHT_MODULE.getPosition()}, new Pose3d());

        public static boolean IS_BLUE_ALLIANCE = false; //THESE ARE NOT FINAL ON PURPOSE, DO NOT MAKE THEM FINAL, CODE WILL BREAK
        public static boolean IS_RED_ALLIANCE = false;

    }

    // TODO: Ensure that the name of the enums and the name of the actual commands are identical
    // Implicitly static
    public enum CommandEnums {
        // Blender
        BLENDER_CMD,

        // Flywheel
        FLYWHEEL_HIGH_SPEED_CMD,
        FLYWHEEL_LOW_SPEED_CMD,

        // Intake
        AUTONOMOUS_INTAKE_DOWN_CMD,
        AUTONOMOUS_START_INTAKE_CMD,
        ACTIVATE_INTAKE_CMD,
        INTAKE_MANAGER_CMD,
        INTAKE_PIVOT_CMD,
        PIVOT_INTAKE_DOWN_TIMED,
        PIVOT_INTAKE_DOWN_LS_CMD,
        INTAKE_PIVOT_UP_CMD,
        PURGE_INTAKE_CMD,

        // Shooter
        CHANGE_RPM_CMD,
        HIGH_SHOOTER_RPM_CMD,
        LOW_SHOOTER_RPM_CMD,
        PRESET_SHOOT_CMD,
        SHOOT_AT_SPEED_CMD,
        SHOOTER_CMD,
        SHOOTER_RPM_CONTROL_CMD,
        TEMP_SHOOTER_CMD,

        // Swerve
        TELEOP_DRIVE_CMD,
        XFORMATION_CMD,

        // Other
        LOCK_ON_CMD,
        RESET_GYRO_CMD,

    }

    private Constants() {} // Overrides default constructor. Don't want anybody instantiating this class, even though likely no one would.
}
