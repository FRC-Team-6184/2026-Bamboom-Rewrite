package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.subsystems.SwerveSubsys;

public class TestShooterCmd extends Command {
    private ShooterSubsys SHOOTER;
    
    public TestShooterCmd(ShooterSubsys shooter) {
        super();
        SHOOTER = shooter;
        addRequirements(SHOOTER);
    }

    @Override
    public void initialize() {
        SHOOTER.setBlenderSpeed(-750.0 / 60.0);
        SHOOTER.setKickerSpeed(2000.0 / 60.0);
        SHOOTER.setFlywheelSpeed(-500.0 / 60.0);
    }

    @Override
    public void execute() {
        SHOOTER.setBlenderSpeed(-750.0 / 60.0);
        SHOOTER.setKickerSpeed(2000.0 / 60.0);
        SHOOTER.setFlywheelSpeed(-500.0 / 60.0);
    }

    @Override
    public void end(boolean interrupted) {
        SHOOTER.setBlenderSpeed(0.0);
        SHOOTER.setKickerSpeed(0.0);
        SHOOTER.setFlywheelSpeed(0.0);
    }
}
