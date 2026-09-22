package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

// import frc.robot.commands.blender.BlenderCommand;
// import frc.robot.commands.flywheel.FlywheelHighSpeedCommand;
// import frc.robot.commands.flywheel.FlywheelLowSpeedCommand;
// import frc.robot.commands.shooter.HighShooterRPMCommand;
// import frc.robot.commands.intake.AutonomousIntakeDownCommand;
// import frc.robot.commands.intake.AutonomousStartIntakeCommand;
// import frc.robot.commands.intake.IntakeCommand;
// import frc.robot.commands.intake.IntakePivotCommand;
// import frc.robot.commands.intake.IntakePivotUpCommand;
// import frc.robot.commands.intake.IntakePurgeCommand;
// import frc.robot.commands.other.LockOnCommand;
// import frc.robot.commands.shooter.LowShooterRPMCommand;
// import frc.robot.commands.shooter.ShootAtSpeedCommand;
// import frc.robot.commands.other.ResetGyroCommand;
// import frc.robot.commands.shooter.ShooterCommand;
// import frc.robot.commands.shooter.ShooterRPMControlCommand;
// import frc.robot.commands.shooter.TempShooterCommand;
import frc.robot.commands.swerve.TeleopDriveCmd;
// import frc.robot.commands.swerve.XFormationCommand;
// import frc.robot.commands.intake.IntakePivotDownCommand;
// import frc.robot.commands.intake.IntakePivotLimitSwitchCommand;
// import frc.robot.commands.intake.IntakeManagerCommand;

// import frc.robot.subsystems.AutonomousSubsys;
// import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
// import frc.robot.subsystems.VisionSubsys;
// import frc.robot.subsystems.LEDSubsys;

import frc.robot.Constants.CommandEnums;

/* TODO:
 * Subsystem Factory class?
 */
public class CommandFactory {

    // Subsystems (Maybe make a SubsysFactory class?)
    private static SwerveSubsys swerveSubsys = new SwerveSubsys();

    // FlywheelHighSpeedCmd cmdFlywheelHigh;
    // FlywheelLowSpeedCmd cmdFlywheelLow;
    // IntakePivotDownCmd cmdPivotDown;
    // BlenderCmd cmdBlender;
    // IntakePivotUpCmd cmdPivotUp;
    // HighShooterRPMCmd cmdHighSpeed;
    // LowShooterRPMCmd cmdLowSpeed;
    // IntakeCmd cmdIntake;
    // ShooterCmd cmdShooter;
    // TempShooterCmd cmdTempShooter;
    // IntakePurgeCmd cmdIntakePurge;
    // IntakePivotCmd cmdIntakePivot;
    // XFormationCmd cmdXFormation;
    // ShooterRPMControlCmd cmdFlywheelRPM;
    // ResetGyroCmd cmdResetGyro;
    TeleopDriveCmd cmdTeleopDrive;
    // IntakePivotLimitSwitchCmd cmdLimitSwitchPivot;
    // ShooterRPMControlCmd cmdIncreaseRPM;
    // ShooterRPMControlCmd cmdDecreaseRPM;
    // LockOnCmd cmdLockon;

    // ShootAtSpeedCmd cmdFlywheelUp;
    // ShootAtSpeedCmd cmdFlywheelLeft;
    // ShootAtSpeedCmd cmdFlywheelRight;
    // ShootAtSpeedCmd cmdFlywheelDown;

    // AutonomousIntakeDownCmd cmdAutoIntakePivot;
    // AutonomousStartIntakeCmd cmdAutoStartIntake;
    // BlenderCmd cmdAutoBlender;

    private CommandFactory() {}

    // TODO: Make enum return respective initialized command
    public static Command getCommand(CommandEnums e) {
        // Blender
        if (e == CommandEnums.BLENDER_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Flywheel
        if (e == CommandEnums.FLYWHEEL_HIGH_SPEED_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.FLYWHEEL_LOW_SPEED_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Intake
        if (e == CommandEnums.AUTONOMOUS_INTAKE_DOWN_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.AUTONOMOUS_START_INTAKE_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.INTAKE_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.INTAKE_MANAGER_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.INTAKE_PIVOT_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.INTAKE_PIVOT_DOWN_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.INTAKE_PIVOT_LIMIT_SWITCH_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.INTAKE_PIVOT_UP_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.INTAKE_PURGE_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Other
        if (e == CommandEnums.LOCK_ON_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.RESET_GYRO_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Shooter
        if (e == CommandEnums.CHANGE_RPM_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.HIGH_SHOOTER_RPM_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.LOW_SHOOTER_RPM_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.PRESET_SHOOT_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.SHOOT_AT_SPEED_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.SHOOTER_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.SHOOTER_RPM_CONTROL_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        if (e == CommandEnums.TEMP_SHOOTER_CMD) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Swerve
        if (e == CommandEnums.TELEOP_DRIVE_CMD) return new TeleopDriveCmd(swerveSubsys); // TODO: Pass Args
        if (e == CommandEnums.XFORMATION_CMD) return new TeleopDriveCmd(); // TODO: Pass Args

        // If invalid e
        throw new IllegalArgumentException("Unknown command, check Constants.java");
    }

}
