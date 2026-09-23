package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.intake.ActivateIntakeCmd;
import frc.robot.commands.intake.PivotIntakeDownLSCmd;
import frc.robot.commands.intake.PivotIntakeDownTimedCmd;
import frc.robot.commands.intake.PurgeIntakeCmd;
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
import frc.robot.subsystems.IntakeSubsys;
// import frc.robot.subsystems.AutonomousSubsys;
// import frc.robot.subsystems.IntakeSubsys;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;
// import frc.robot.subsystems.VisionSubsys;
// import frc.robot.subsystems.LEDSubsys;
import frc.robot.Constants.Subsystems;
import frc.robot.Constants.CommandEnums;

// TODO: Replace the hundred ifs with a switch statement
public class CommandFactory {

    /**
     * 
     * @return A fully initialized command
     * @see {@code Constants.java} for a list of command enumerators
     */
    public static Command getCommand(CommandEnums cmdEnum) {
        // Blender
        if (cmdEnum == CommandEnums.BLENDER_CMD) return null;
        
        // Flywheel
        if (cmdEnum == CommandEnums.FLYWHEEL_HIGH_SPEED_CMD) return null;
        if (cmdEnum == CommandEnums.FLYWHEEL_LOW_SPEED_CMD) return null; 
        
        // Intake
        if (cmdEnum == CommandEnums.AUTONOMOUS_INTAKE_DOWN_CMD) return null; 
        if (cmdEnum == CommandEnums.AUTONOMOUS_START_INTAKE_CMD) return null; 
        if (cmdEnum == CommandEnums.ACTIVATE_INTAKE_CMD) return new ActivateIntakeCmd(Subsystems.INTAKE_SUBSYS); 
        if (cmdEnum == CommandEnums.INTAKE_MANAGER_CMD) return null; 
        if (cmdEnum == CommandEnums.INTAKE_PIVOT_CMD) return null; 
        if (cmdEnum == CommandEnums.PIVOT_INTAKE_DOWN_TIMED) return new PivotIntakeDownTimedCmd(Subsystems.INTAKE_SUBSYS); 
        if (cmdEnum == CommandEnums.PIVOT_INTAKE_DOWN_LS_CMD) return new PivotIntakeDownLSCmd(Subsystems.INTAKE_SUBSYS); 
        if (cmdEnum == CommandEnums.INTAKE_PIVOT_UP_CMD) return null; 
        if (cmdEnum == CommandEnums.PURGE_INTAKE_CMD) return new PurgeIntakeCmd(Subsystems.INTAKE_SUBSYS); 
        
        // Other
        if (cmdEnum == CommandEnums.LOCK_ON_CMD) return null; 
        if (cmdEnum == CommandEnums.RESET_GYRO_CMD) return null; 
        
        // Shooter
        if (cmdEnum == CommandEnums.CHANGE_RPM_CMD) return null; 
        if (cmdEnum == CommandEnums.HIGH_SHOOTER_RPM_CMD) return null; 
        if (cmdEnum == CommandEnums.LOW_SHOOTER_RPM_CMD) return null; 
        if (cmdEnum == CommandEnums.PRESET_SHOOT_CMD) return null; 
        if (cmdEnum == CommandEnums.SHOOT_AT_SPEED_CMD) return null; 
        if (cmdEnum == CommandEnums.SHOOTER_CMD) return null; 
        if (cmdEnum == CommandEnums.SHOOTER_RPM_CONTROL_CMD) return new ShooterRPMControlCmd(Subsystems.SHOOTER_SUBSYS, 100.0 / 60.0);
        if (cmdEnum == CommandEnums.TEMP_SHOOTER_CMD) return null; 
        
        // Swerve
        if (cmdEnum == CommandEnums.TELEOP_DRIVE_CMD) return new TeleopDriveCmd(Subsystems.SWERVE_SUBSYS); 
        if (cmdEnum == CommandEnums.XFORMATION_CMD) return null; 

        // If invalid cmdEnum
        throw new IllegalArgumentException("Unknown command, check Constants.java");
    }

    private CommandFactory() {} // Prevent instantiation. This is a factory class.
}
