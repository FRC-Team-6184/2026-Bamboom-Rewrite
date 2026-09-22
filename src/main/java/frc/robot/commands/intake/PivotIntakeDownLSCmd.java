package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

import frc.robot.Constants.DigitalValues;

// Implementation of the intake pivotation, using a limit switch.
/**
 * Limit switch is not wired as of tuesday, don't use this command before wiring the switch
 */
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
            intake.setPivotSpeed(0.1); // Currently does not actually do anything
            intake.pivotDown(); // Currently set to 0.2 speed according to the constants
        } else {
            intake.pivotStop();
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.setPivotSpeed(DigitalValues.INTAKE_PIVOT); // Set back to default
        intake.pivotStop();
    }
}
