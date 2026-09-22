package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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
        System.out.println("STARTING PIVOT DOWN");
    }

    @Override
    public void execute() {
        System.out.println("Waiting for timer" + " | " + timer.get());

        if (timer.get() >= 0.6) {
            System.out.println("TIMER OVER");
            // TODO: Why are we cancelling the same instance twice, is it the same instance?
            super.cancel();
            CommandScheduler.getInstance().cancel(this);
        }
    }

    @Override
    public void end(boolean interrupted) {
        // intake.pivotStop();
        // intake.startIntake();
        timer.stop();
        timer.reset();
    }
}
