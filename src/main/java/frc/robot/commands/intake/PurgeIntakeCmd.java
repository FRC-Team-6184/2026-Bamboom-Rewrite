package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class PurgeIntakeCmd extends Command {
    private final IntakeSubsys intake;

    public PurgeIntakeCmd(IntakeSubsys intake) {
        super();
        this.intake = intake;
        this.addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.purgeIntake();
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }
}
