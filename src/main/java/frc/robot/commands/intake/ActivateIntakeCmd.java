package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class ActivateIntakeCmd extends Command {
    private final IntakeSubsys intake;

    public ActivateIntakeCmd(IntakeSubsys intake) {
        super();
        this.intake = intake;
        addRequirements(intake); // Removed 'this' keyword.
    }

    @Override
    public void initialize() {
        intake.startIntake();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntake();
    }
}
