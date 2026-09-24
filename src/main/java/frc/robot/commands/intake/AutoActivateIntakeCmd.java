package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class AutoActivateIntakeCmd extends Command {
    private final IntakeSubsys INTAKE;
    boolean finished = false;

    public AutoActivateIntakeCmd(IntakeSubsys intake) {
        super();
        this.INTAKE = intake;
        this.addRequirements(intake); 
    }

    @Override
    public void initialize() {
        INTAKE.startIntake();
        finished = true;
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
