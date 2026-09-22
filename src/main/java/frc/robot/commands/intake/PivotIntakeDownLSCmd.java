package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

// Implementation of the intake pivotation, using a limit switch.
public class PivotIntakeDownLSCmd extends Command {
    private final IntakeSubsys intake;

    public PivotIntakeDownLSCmd(IntakeSubsys intake) {
        super();
        this.intake = intake;
        this.addRequirements(intake); 
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (!intake.isSwitchHit()) {
            intake.setPivotSpeed(0.1); // No implementation currently
            intake.pivotDown(); // HAS TO BE 0.1, DO NOT RUN UNTIL IMPLEMENTED
        } else {
            intake.pivotStop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.setPivotSpeed(); // Set it to whatever the default is
        intake.pivotStop();
    }
}
