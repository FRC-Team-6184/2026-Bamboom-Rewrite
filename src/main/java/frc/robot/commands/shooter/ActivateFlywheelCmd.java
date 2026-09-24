package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
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
        SHOOTER.startFlywheel(rps);
    }

    @Override
    public void end(boolean interrupted) {
        SHOOTER.stopFlywheel();
    }
}
