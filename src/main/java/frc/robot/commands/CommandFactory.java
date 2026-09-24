package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.intake.ActivateIntakeCmd;
import frc.robot.commands.intake.IntakeDownLSCmd;
import frc.robot.commands.intake.IntakeDownCmd;
import frc.robot.commands.intake.PurgeIntakeCmd;
import frc.robot.commands.shooter.ActivateShooterCmd;
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
        switch (cmdEnum) {
                // Intake
                case AUTONOMOUS_INTAKE_DOWN_CMD: return null;
                case AUTONOMOUS_START_INTAKE_CMD: return null;
                case ACTIVATE_INTAKE_CMD: return new ActivateIntakeCmd(Subsystems.INTAKE_SUBSYS);
                case INTAKE_MANAGER_CMD: return null;
                case INTAKE_PIVOT_CMD: return null;
                case PIVOT_INTAKE_DOWN_CMD: return new IntakeDownCmd(Subsystems.INTAKE_SUBSYS);
                case PIVOT_INTAKE_DOWN_LS_CMD: return new IntakeDownLSCmd(Subsystems.INTAKE_SUBSYS);
                case INTAKE_PIVOT_UP_CMD: return null;
                case PURGE_INTAKE_CMD: return new PurgeIntakeCmd(Subsystems.INTAKE_SUBSYS);

                // Shooter
                case ACTIVATE_SHOOTER_CMD: return new ActivateShooterCmd(Subsystems.SHOOTER_SUBSYS);

                // Swerve
                case TELEOP_DRIVE_CMD: return new TeleopDriveCmd(Subsystems.SWERVE_SUBSYS);
                case XFORMATION_CMD: return null;

                // Other
                case LOCK_ON_CMD: return null;
                case RESET_GYRO_CMD: return null;

                // If invalid cmdEnum
                default: throw new IllegalArgumentException("Unknown command, check Constants.java");
        }        
    }

    private CommandFactory() {} // Prevent instantiation. This is a factory class.
}
