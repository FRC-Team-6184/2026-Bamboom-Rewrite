package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveSubsys;

public class ResetGyroCmd extends Command {
    SwerveSubsys SWERVE;


    public ResetGyroCmd(SwerveSubsys swerve) {
        super();
        SWERVE = swerve;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        SWERVE.resetGyro();
    }

    @Override
    public void end(boolean interrupted) {

    }
    
}
