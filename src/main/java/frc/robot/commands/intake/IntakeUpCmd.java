package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

// TODO: Does this class actually just nudge the intake to get balls unstuck? If so I wanna rename it 
public class IntakeUpCmd extends Command {

    private final IntakeSubsys INTAKE;

    public IntakeUpCmd(IntakeSubsys intake) {
        super();
        this.INTAKE = intake;
        this.addRequirements(intake);
    }

    @Override
    public void initialize() {
        super.withTimeout(0.25);
        INTAKE.pivotUp();
    }

    @Override
    public void execute() {
        cancel();
    }

    @Override
    public void end(boolean interrupted) {
        INTAKE.pivotStop();
    }

}
