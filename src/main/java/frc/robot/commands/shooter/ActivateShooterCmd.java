package frc.robot.commands.shooter;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.DigitalValues.Speeds;
import frc.robot.commands.shooter.DecreaseFlywheelRPSCmd;
import frc.robot.commands.shooter.IncreaseFlywheelRPSCmd;
import frc.robot.subsystems.ShooterSubsys;
import frc.robot.Constants.MotorControllers;
import edu.wpi.first.wpilibj.Timer;

// TODO: Make decrease and increase speed commands work alongside this one to change rpm as it moves.
public class ActivateShooterCmd extends Command {
    private final ShooterSubsys SHOOTER;
    private AngularVelocity shooterVelocity;
    private double TARGET_RPS = -600.0 / 60.0; // Eventually wanna move this into constants.java
    private Timer timer = new Timer();

    private boolean flag = false; // rename this sometime

    public ActivateShooterCmd(ShooterSubsys shooter) {
        super();
        this.SHOOTER = shooter;
        this.addRequirements(shooter); 
    }

    @Override
    public void initialize() {
        timer.start();
        SHOOTER.setFlywheelSpeed(TARGET_RPS);
    }

    @Override
    public void execute() {
        TARGET_RPS = SHOOTER.getFlywheelTargetSpeed();

        if (SHOOTER.getShooterRPS() >= TARGET_RPS || timer.get() >= 0.25) {
            flag = true;
        }

        if(flag) {
            shooterVelocity = MotorControllers.FLYWHEEL_LEFT_MOTOR.getVelocity().getValue();
            SHOOTER.setKickerSpeed(1500.0 / 60.0);
            SHOOTER.setBlenderSpeed(TARGET_RPS);
        }
    }

    @Override
    public void end(boolean interrupted) {
        SHOOTER.stopShooter();
        timer.stop();
        timer.reset();
        flag = false;
    }
}
