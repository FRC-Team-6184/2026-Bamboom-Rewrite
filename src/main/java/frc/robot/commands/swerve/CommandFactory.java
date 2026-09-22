package frc.robot.commands.swerve;

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
    public static Command getCommand(int cmdEnum) {
        // Blender
        if (cmdEnum == CommandEnums.BlenderCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Flywheel
        if (cmdEnum == CommandEnums.FlywheelHighSpeedCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.FlywheelLowSpeedCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Intake
        if (cmdEnum == CommandEnums.AutonomousIntakeDownCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.AutonomousStartIntakeCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.IntakeCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.IntakeManagerCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.IntakePivotCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.IntakePivotDownCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.IntakePivotLimitSwitchCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.IntakePivotUpCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.IntakePurgeCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Other
        if (cmdEnum == CommandEnums.LockOnCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.ResetGyroCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Shooter
        if (cmdEnum == CommandEnums.ChangeRPMCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.HighShooterRPMCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.LowShooterRPMCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.PresetShootCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.ShootAtSpeedCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.ShooterCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.ShooterRPMControlCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        if (cmdEnum == CommandEnums.TempShooterCmd) return new TeleopDriveCmd(); // TODO: Pass Args
        
        // Swerve
        if (cmdEnum == CommandEnums.TeleopDriveCmd) return new TeleopDriveCmd(swerveSubsys); // TODO: Pass Args
        if (cmdEnum == CommandEnums.XFormationCmd) return new TeleopDriveCmd(); // TODO: Pass Args

        // If invalid cmdEnum
        throw new IllegalArgumentException("Unknown command, check Constants.java");
    }

}
