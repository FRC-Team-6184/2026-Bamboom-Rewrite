package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsys;

public class DecreaseFlywheelRPSCmd extends Command {
    private ShooterSubsys SHOOTER;

    public DecreaseFlywheelRPSCmd(ShooterSubsys shooter) {
        super();
        SHOOTER = shooter;
        //not added as a requirement on purpose
    }

    @Override
    public void initialize() {
        SHOOTER.setFlywheelTargetSpeed(SHOOTER.getFlywheelTargetSpeed() + (50.0 / 60.0));

    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        
    }
}
