package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

import frc.robot.Constants.DigitalValues.Speeds;

// Implementation of the intake pivotation, using a limit switch.
/**
 * Limit switch is not wired as of tuesday, don't use this command before wiring the switch
 */
public class IntakeDownLSCmd extends Command {
    private final IntakeSubsys intake;

    public IntakeDownLSCmd(IntakeSubsys intake) {
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
            intake.setPivotSpeed(0.1); 
            intake.pivotDown(); 
        } else {
            intake.pivotStop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.setPivotSpeed(Speeds.INTAKE_PIVOT); // Set back to default
        intake.pivotStop();
    }
}
