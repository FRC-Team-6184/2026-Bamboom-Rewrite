package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class AutoIntakeDownCmd extends Command {
    private final IntakeSubsys INTAKE;
    boolean finished = false;


    public AutoIntakeDownCmd(IntakeSubsys intake) {
        super();
        this.INTAKE = intake;
        this.addRequirements(intake); 
    }

    @Override
    public void initialize() {
        INTAKE.pivotDown();
    }

    @Override
    public void execute() {
        if (INTAKE.isSwitchHit()) {
            INTAKE.pivotStop();
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
