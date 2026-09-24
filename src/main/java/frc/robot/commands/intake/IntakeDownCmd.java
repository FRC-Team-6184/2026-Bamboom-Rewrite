package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

// Implementation of the intake pivotation, operating on a timer
public class IntakeDownCmd extends Command {
    private final IntakeSubsys INTAKE;

    public IntakeDownCmd(IntakeSubsys intake) {
        super();
        this.INTAKE = intake;
        this.addRequirements(intake); 
    }

    @Override
    public void initialize() {
        super.withTimeout(0.6); // I believe this goes here? If it doesn't work, move it to the constructor and try that
        INTAKE.pivotDown();
    }

    @Override
    public void execute() {
        cancel();
    }

    @Override
    public void end(boolean interrupted) {
        INTAKE.pivotStop(); // super.cancel() Id imagine already does this, but just making sure.
    }
}
