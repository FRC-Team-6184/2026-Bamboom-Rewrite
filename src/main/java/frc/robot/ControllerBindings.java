package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/* TODO:
 * Implement the bindings for the main controller and co-controller
 * Put their implementations inside separate methods
 * 
 * You can copy and paste the related stuff from RobotContainer.java in the old repo
 */

 // Subsystems -> Commands -> ControllerBinding
public class ControllerBindings {
    private final CommandXboxController kDriveController;
    private final CommandPS5Controller kCoDriveController;

    /** Initialize Controllers/Bindings */
    public ControllerBindings() {
        kDriveController = Constants.Controller.XBOX;
        kCoDriveController = Constants.Controller.PS5;

        configureBindings();
    }

    private void configureBindings() {
        // TODO: Swerve drive stuffs idk

        // mainController.x().toggleOnTrue(cmdXFormation);
        // mainController.rightBumper().and(mainController.leftBumper()).whileTrue(cmdResetGyro);
        // mainController.b().whileTrue(cmdLockon);

        // codriveController.povUp().onTrue(cmdIncreaseRPM);
        // codriveController.povDown().onTrue(cmdDecreaseRPM);

        // codriveController.axisGreaterThan(3, 0.8).whileTrue(cmdBlender);

        // codriveController.L1().whileTrue(cmdFlywheelHigh);
        // codriveController.R1().toggleOnTrue(cmdIntake);
        // codriveController.axisGreaterThan(4, 0.8).whileTrue(cmdIntakePurge);


        // codriveController.axisGreaterThan(5, 0.12).or(codriveController.axisLessThan(5, -0.12)).whileTrue(cmdIntakePivot); //TODO: make this go to a proportional intake pivot command

        // codriveController.axisLessThan(5, -0.6).whileFalse(cmdLimitSwitchPivot).whileTrue(cmdIntakePivot);

        // codriveController.povUp().onTrue(cmdIncreaseRPM);
        // codriveController.povDown().onTrue(cmdDecreaseRPM);

        // codriveController.povUp().whileTrue(cmdFlywheelUp);
        // codriveController.povDown().whileTrue(cmdFlywheelDown);
        // codriveController.povLeft().whileTrue(cmdFlywheelLeft);
        // codriveController.povRight().whileTrue(cmdFlywheelRight);


        // codriveController.pov

        // codriveController.povCenter().whileFalse(cmdLowSpeed);
    }

}
