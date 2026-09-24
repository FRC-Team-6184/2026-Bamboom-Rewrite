package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

import edu.wpi.first.wpilibj.Timer;

public class PivotIntakeUpTimedCmd extends Command {

    private final IntakeSubsys INTAKE;
    private final Timer TIMER = new Timer();

    public PivotIntakeUpTimedCmd(IntakeSubsys intake) {
        super();
        this.INTAKE = intake;
        this.addRequirements(intake);
    }

    @Override
    public void initialize() {
        TIMER.start();
        INTAKE.pivotUp();
    }

    @Override
    public void execute() {
        if (TIMER.get() >= 0.25) {
            cancel();
        }
    }

    @Override
    public void end(boolean interrupted) {
        INTAKE.pivotStop();
    }

}
