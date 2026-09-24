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
import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.AutonomousSubsys;
import frc.robot.subsystems.VisionSubsys;
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
    public static final class Controller {
        public static final int XBOX_P = 0;
        public static final CommandXboxController XBOX = new CommandXboxController(XBOX_P);

        public static final int PS5_P = 1;        
        public static final CommandPS5Controller PS5 = new CommandPS5Controller(PS5_P);
    }

    public static final class Subsystems {
        public static final IntakeSubsys INTAKE_SUBSYS = new IntakeSubsys();
        public static final ShooterSubsys SHOOTER_SUBSYS = new ShooterSubsys();
        public static final SwerveSubsys SWERVE_SUBSYS = new SwerveSubsys();
        public static final AutonomousSubsys AUTO_SUBSYS = new AutonomousSubsys();
        public static final VisionSubsys VISION_SUBSYS = new VisionSubsys();
    }

    public static final class Gyro {
        public static final Pigeon2 GYRO = new Pigeon2(CAN_IDs.GYRO_ID);
    }

    // (hardware side)
    public static final class CAN_IDs {
        // Shooter
        public static final int SHOOTER_KICKER_WHEEL_ID = 13;
        public static final int SHOOTER_LEFT_WHEEL_ID = 11; //Left Shooter motor when facing the Intake
        public static final int SHOOTER_RIGHT_WHEEL_ID = 12; //Right Shooter motor when facing the intake

        // Intake
        public static final int INTAKE_PIVOT_MOTOR_ID = 10;
        public static final int FR_ACTIVE_INTAKE_ID = 15;
        public static final int FL_ACTIVE_INTAKE_ID = 14; 

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
        public static final TalonFX SHOOTER_KICKER_MOTOR = new TalonFX(CAN_IDs.SHOOTER_KICKER_WHEEL_ID);
        public static final TalonFX FLYWHEEL_LEFT_MOTOR = new TalonFX(CAN_IDs.SHOOTER_LEFT_WHEEL_ID);
        public static final TalonFX FLYWHEEL_RIGHT_MOTOR = new TalonFX(CAN_IDs.SHOOTER_RIGHT_WHEEL_ID);

        // Blender
        public static final TalonFX BLENDER_MOTOR = new TalonFX(CAN_IDs.BLENDER_MOTOR_ID); // Check to make sure this ID is right

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

        // Intake
        public static final TalonFX INTAKE_PIVOT_MOTOR = new TalonFX(CAN_IDs.INTAKE_PIVOT_MOTOR_ID); // Check to make sure this ID is right
        public static final TalonFX FR_ACTIVE_INTAKE_MOTOR = new TalonFX(CAN_IDs.FR_ACTIVE_INTAKE_ID);
        public static final TalonFX FL_ACTIVE_INTAKE_MOTOR = new TalonFX(CAN_IDs.FL_ACTIVE_INTAKE_ID);

        
    }

    // Software things below
    public static final class DIO {
        public static final DigitalInput INTAKE_LIMIT_SWITCH = new DigitalInput(0); //This does exist! Hooray!
        // public static final DigitalInput INTAKE_BOTTOM_LIMIT_SWITCH = new DigitalInput(1); THIS DOES NOT EXIST
    }

    public static final class DigitalValues {
        public static final class Speeds {
            public static final double SUPER_LOW = 0.05;
            public static final double LOW = 0.33;
            public static final double MEDIUM = 0.66;
            public static final double HIGH = 1;

            public static final double INTAKE_PIVOT = 0.2;
            public static final double INTAKE_SPEED = -0.3;

            public static final double SHOOTER_LOW_SPEED = 1500.0 / 60.0;
            public static final double SHOOTER_HIGH_SPEED = 2500.0 / 60.0;
            public static final double SHOOTER_BOTTOM_SPEED = -50;
        }
        
        public static final double CONTROLLER_DEADZONE = 0.12;

        public static final class Chassis {

            // Distance between centers of right and left wheels on robot
            public static final double TRACK_WIDTH = Units.inchesToMeters(21.525);

            // Distance between centers of front and back wheels on robot
            public static final double WHEEL_BASE = Units.inchesToMeters(21.525);

            // public static final RobotConfig ROBOT_CONFIGURATION = new RobotConfig(null, null, null, null);
        }
    }

    public static final class SoftwareObjects {
        public static final NetworkTableInstance NETWORK_TABLE_INSTANCE = NetworkTableInstance.getDefault();

        public static final MAXSwerveModule FRONT_LEFT_MODULE = new MAXSwerveModule(MotorControllers.FL_DRIVE_MOTOR, MotorControllers.FL_TURN_MOTOR, DriveConstants.FRONT_LEFT_CHASSIS_ANGULAR_OFFSET);
        public static final MAXSwerveModule FRONT_RIGHT_MODULE = new MAXSwerveModule(MotorControllers.FR_DRIVE_MOTOR, MotorControllers.FR_TURN_MOTOR, DriveConstants.FRONT_RIGHT_CHASSIS_ANGULAR_OFFSET);
        public static final MAXSwerveModule BACK_LEFT_MODULE = new MAXSwerveModule(MotorControllers.BL_DRIVE_MOTOR, MotorControllers.BL_TURN_MOTOR, DriveConstants.BACK_LEFT_CHASSIS_ANGULAR_OFFSET);
        public static final MAXSwerveModule BACK_RIGHT_MODULE = new MAXSwerveModule(MotorControllers.BR_DRIVE_MOTOR, MotorControllers.BR_TURN_MOTOR, DriveConstants.BACK_RIGHT_CHASSIS_ANGULAR_OFFSET);
        public static final SwerveDrivePoseEstimator3d POSE_ESTIMATOR = new SwerveDrivePoseEstimator3d(DriveConstants.DRIVE_KINEMATICS, Gyro.GYRO.getRotation3d(), new SwerveModulePosition[] {FRONT_LEFT_MODULE.getPosition(), FRONT_RIGHT_MODULE.getPosition(), BACK_LEFT_MODULE.getPosition(), BACK_RIGHT_MODULE.getPosition()}, new Pose3d());

        
    }

    // TODO: Ensure that the name of the enums and the name of the actual commands are identical
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
        PIVOT_INTAKE_DOWN_CMD,
        PIVOT_INTAKE_DOWN_LS_CMD,
        INTAKE_PIVOT_UP_CMD,
        PURGE_INTAKE_CMD,

        // Shooter
        ACTIVATE_SHOOTER_CMD,

        // Swerve
        TELEOP_DRIVE_CMD,
        XFORMATION_CMD,

        // Other
        LOCK_ON_CMD,
        RESET_GYRO_CMD,
    }

    public enum AutonomousEnums {
        BlueDepotTrench, 
        BlueHumanTrench, 
        RedDepotTrench, 
        RedHumanTrench, 
        Practice, 
        BlueDepotTrenchDouble, 
        RedDepotTrenchDouble,
    }

    private Constants() {} // Overrides default constructor. Don't want anybody instantiating this class, even though likely no one would.
}
