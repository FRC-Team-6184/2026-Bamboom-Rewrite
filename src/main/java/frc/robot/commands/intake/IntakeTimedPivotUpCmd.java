package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsys;

public class IntakeTimedPivotUpCmd extends Command {
    
    // CommandPS5Controller myController = Controller.PS5;
    IntakeSubsys intake;

    public IntakeTimedPivotUpCmd(IntakeSubsys intake) {
        super();
        this.intake = intake;
        addRequirements(intake);
    }


    @Override
    public void execute() {
        // intake.getPivotMotor().set(myController.getRightY() * 0.1);
        // System.out.println(myController.getRightY() * 0.1);
        intake.getPivotMotor().set(-0.2);
    }

    @Override
    public void end(boolean interrupted) {
        intake.getPivotMotor().set(0.0);
    }
}
