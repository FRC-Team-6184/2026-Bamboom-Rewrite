package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.shooter.DecreaseFlywheelRPSCmd;
import frc.robot.commands.shooter.IncreaseFlywheelRPSCmd;
import frc.robot.subsystems.ShooterSubsys;

public class ActivateFlywheelCmd extends Command {
    private final ShooterSubsys SHOOTER;
    double rps;

    public ActivateFlywheelCmd(ShooterSubsys shooter, double rps) {
        this.SHOOTER = shooter;
        this.rps = rps;
    }

    @Override
    public void initialize() {
        SHOOTER.setFlywheelSpeed(rps);
        super.alongWith(new DecreaseFlywheelRPSCmd()); // Im not sure I should be instantiating this
        super.alongWith(new IncreaseFlywheelRPSCmd());
    }


    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        SHOOTER.stopFlywheel();
    }
}
