// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
 * Note from William H. : Stole this straight from the REV MaxSwerve Template, added additional notes and such though to help make this more approachable and understandable
 */

package frc.robot.subsystems.swerve;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.RobotMap.Chassis;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes) wherever the constants are needed, to reduce verbosity.
 */
public final class SwerveConstants {
    /**
     * Generic constants regarding the overall use of Swerve, including the CAN IDs
     * of the motors.
     */
    public static final class DriveConstants {
        private static final double TRACK_WIDTH = Chassis.TRACK_WIDTH;
        private static final double WHEEL_BASE = Chassis.WHEEL_BASE;

        /*
         * Driving Parameters - Note that these are not the maximum capable speeds of
         * the robot, but rather the allowed maximum speeds
         */

        /** Hard set max speed for the overall robot */
        public static final double MAX_SPEED_METERS_PER_SECOND = 4.8;
        /** Hard set max turning/rotation speed for the robot */
        public static final double MAX_ANGULAR_SPEED = 2 * Math.PI; // radians per second

        /**
         * You shouldn't have to touch this at all ever,
         * this is just making sure the robot knows where the wheels are relative to the
         * center of the robot.
         */
        public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(new Translation2d(WHEEL_BASE / 2, TRACK_WIDTH / 2), new Translation2d(WHEEL_BASE / 2, -TRACK_WIDTH / 2), new Translation2d(-WHEEL_BASE / 2, TRACK_WIDTH / 2), new Translation2d(-WHEEL_BASE / 2, -TRACK_WIDTH / 2));

        // Angular offsets of the modules relative to the chassis in radians
        /**
         * Yet another way for the robot to keep reference of where the modules are in
         * reference to the center of the robot.
         * You shouldn't have to touch this if you have a square frame, possibly if it's
         * rectangular.
         */
        public static final double FRONT_LEFT_CHASSIS_ANGULAR_OFFSET = -Math.PI / 2;
        public static final double FRONT_RIGHT_CHASSIS_ANGULAR_OFFSET = 0;
        public static final double BACK_LEFT_CHASSIS_ANGULAR_OFFSET = Math.PI;
        public static final double BACK_RIGHT_CHASSIS_ANGULAR_OFFSET = Math.PI / 2;

        public static final boolean IS_GYRO_REVERSED = false;
    }

    /**
     * This is concerning the actual math of some of the mechanisms of the swerve
     * modules.
     * You shouldn't ever need to touch anything other than
     * DRIVING_MOTOR_PINION_TEETH.
     * 
     * Only do so if you know what you're doing or if you have swapped out of the
     * pinion gears.
     */
    public static final class ModuleConstants {
        // The MAXSwerve module can be configured with one of three pinion gears: 12T,
        // 13T, or 14T. This changes the drive speed of the module (a pinion gear with
        // more teeth will result in a robot that drives faster).
        public static final int DRIVING_MOTOR_PINION_TEETH = 14;

        // Calculations required for driving motor conversion factors and feed forward
        public static final double DRIVING_MOTOR_FREE_SPEED_RPS = NeoMotorConstants.FREE_SPEED_RPM / 60;
        public static final double WHEEL_DIAMETER_METERS = 0.0759;
        public static final double WHEEL_CIRCUMFERENCE_METERS = WHEEL_DIAMETER_METERS * Math.PI;
        // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
        // teeth on the bevel pinion
        public static final double DRIVING_MOTOR_REDUCTION = (45.0 * 22) / (DRIVING_MOTOR_PINION_TEETH * 15);
        public static final double DRIVE_WHEEL_FREE_SPEED_RPS = (DRIVING_MOTOR_FREE_SPEED_RPS * WHEEL_CIRCUMFERENCE_METERS) / DRIVING_MOTOR_REDUCTION;
    }

    /**
     * These are constants that are only relevant during Autonomous Mode.
     * Any duplicate settings that exist elsewhere are overriden by the ones here
     * during auto.
     */
    public static final class AutoConstants {
        public static final double MAX_SPEED_METERS_PER_SECOND = 3;
        public static final double MAX_ACCELERATON_METERS_PER_SECOND_SQUARED = 3;
        public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND = Math.PI;
        public static final double MAX_ANGULAR_SPEED_RADIANS_PER_SECOND_SQUARED = Math.PI;

        // ?? what are these -Reece
        public static final double PX_CONTROLLER = 1;
        public static final double PY_CONTROLLER = 1;
        public static final double P_THETA_CONTROLLER = 1;

        // Constraint for the motion profiled robot angle controller
        public static final TrapezoidProfile.Constraints THETA_CONTROLLER_CONSTRAINTS = new TrapezoidProfile.Constraints(MAX_ANGULAR_SPEED_RADIANS_PER_SECOND, MAX_ANGULAR_SPEED_RADIANS_PER_SECOND_SQUARED);
    }

    /**
     * So, I think this is the max speed of the big ol' Neo with no load, but I
     * frankly have not a single clue.
     * I'm just trusting this number because I don't know what this is.
     */
    public static final class NeoMotorConstants {
        public static final double FREE_SPEED_RPM = 5676;
    }
}
