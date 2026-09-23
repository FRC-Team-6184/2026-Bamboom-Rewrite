package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

import edu.wpi.first.wpilibj.Timer;

// Implementation of the intake pivotation, operating on a timer
public class PivotIntakeDownTimedCmd extends Command {
    private final IntakeSubsys intake;
    private final Timer timer = new Timer();

    public PivotIntakeDownTimedCmd(IntakeSubsys intake) {
        super();
        this.intake = intake;
        this.addRequirements(intake); 
    }

    @Override
    public void initialize() {
        timer.start();
        intake.pivotDown();
    }

    @Override
    public void execute() {
        if (timer.get() >= 0.6) {
            System.out.println("TIMER OVER");
            super.cancel();
        }
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        timer.reset();
        intake.pivotStop(); // super.cancel() Id imagine already does this, but just making sure.
    }
}
