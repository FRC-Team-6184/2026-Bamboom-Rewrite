package frc.robot.commands.swerve;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveSubsys;

public class TeleopDriveCmd extends Command {
    private SwerveSubsys swerve;
    private CommandXboxController controller = Constants.Controller.XBOX;
    private Pigeon2 gyro = Constants.Gyro.GYRO;

    public TeleopDriveCmd(SwerveSubsys swerve) {
        super();
        this.addRequirements(swerve);
        this.swerve = swerve;
    }

    @Override
    public void initialize() {
        gyro.reset();
    }

    @Override
    public void execute() {
        // System.out.println("This command is in fact actually running");

        double x = controller.getLeftX();
        x = Math.abs(x) > Constants.DigitalValues.CONTROLLER_DEADZONE ? x : 0.0;

        double y = controller.getLeftY();
        y = Math.abs(y) > Constants.DigitalValues.CONTROLLER_DEADZONE ? y : 0.0;

        double rot = controller.getRightX();
        rot = Math.abs(rot) > Constants.DigitalValues.CONTROLLER_DEADZONE ? rot : 0.0;

        // If controller is approximately in a straight direction, set it straight so
        // it's more intuitive
        if (Math.abs(x) >= 0.95 && Math.abs(y) <= 0.2) {
            x = 1 * Math.signum(x);
            y = 0;
        } else if (Math.abs(y) >= 0.95 && Math.abs(x) <= 0.2) {
            x = 0;
            y = 1 * Math.signum(y);
        }

        // IF controller is set approximately to full rotate speed, just set it to full
        // rotate speed.
        if (Math.abs(rot) >= 0.95) {
            rot = 1 * Math.signum(rot);
        }

        // TODO: Set this back to true when robot is in better shape, false to be easier
        // to work with for now.
        // Realistically, it needs to be possible to make it not field relative, maybe a
        // hold or something.
        swerve.drive(x, y, rot, true);
    }

    @Override
    public void end(boolean interrupted) { // This command shouldn't end unless disabled or something once it's started
        // System.out.println("*-.[THE TELEOP DRIVE COMMAND ENDED SOMETHING WENT WRONG] | " + interrupted + ".-*");
    }

}