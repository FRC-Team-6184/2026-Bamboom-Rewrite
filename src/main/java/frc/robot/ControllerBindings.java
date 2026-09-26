package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.CommandEnums;
import frc.robot.commands.CommandFactory;

/* TODO:
 * Implement the bindings for the main controller and co-controller
 * Put their implementations inside separate methods
 * 
 * You can copy and paste the related stuff from RobotContainer.java in the old repo
 */

 // Subsystems -> Commands -> ControllerBinding
public class ControllerBindings {
    private final CommandXboxController DRIVE_CONTROLLER;
    private final CommandPS5Controller CO_DRIVE_CONTROLLER;

    /** Initialize Controllers/Bindings */
    public ControllerBindings() {
        DRIVE_CONTROLLER = Constants.Controller.XBOX;
        CO_DRIVE_CONTROLLER = Constants.Controller.PS5;

        configureBindings();
    }

    private void configureBindings() {
        // DRIVE_CONTROLLER.x().toggleOnTrue(
        //     CommandFactory.getCommand(CommandEnums.XFORMATION_CMD));

        // DRIVE_CONTROLLER.rightBumper().and(DRIVE_CONTROLLER.leftBumper()).whileTrue(
        //     CommandFactory.getCommand(CommandEnums.RESET_GYRO_CMD));

        // DRIVE_CONTROLLER.b().whileTrue(
        //     CommandFactory.getCommand(CommandEnums.LOCK_ON_CMD));
        
        // ACTIVATE INTAKE
        CO_DRIVE_CONTROLLER.R1().whileTrue(
            CommandFactory.getCommand(CommandEnums.ACTIVATE_INTAKE_CMD)
        );

        // PURGE INTAKE
        CO_DRIVE_CONTROLLER.axisGreaterThan(4, 0.8).whileTrue(
            CommandFactory.getCommand(CommandEnums.PURGE_INTAKE_CMD)
        );

        CO_DRIVE_CONTROLLER.circle().whileTrue(CommandFactory.getCommand(CommandEnums.SHOOTER_CMD));
        // CO_DRIVE_CONTROLLER.povUp().onTrue(cmdIncreaseRPM);
        // CO_DRIVE_CONTROLLER.povDown().onTrue(cmdDecreaseRPM);

        // cmdIncreaseRPM = new ShooterRPMControlCommand(kShooterSubsystem, 100.0 / 60.0);
        // cmdDecreaseRPM = new ShooterRPMControlCommand(kShooterSubsystem, -100.0 / 60.0);

        // CO_DRIVE_CONTROLLER.axisGreaterThan(3, 0.8).whileTrue(cmdBlender);

        // CO_DRIVE_CONTROLLER.L1().whileTrue(cmdFlywheelHigh);


        // CO_DRIVE_CONTROLLER.axisGreaterThan(5, 0.12).or(CO_DRIVE_CONTROLLER.axisLessThan(5, -0.12)).whileTrue(cmdIntakePivot); //TODO: make this go to a proportional intake pivot command
        CO_DRIVE_CONTROLLER.povUp().onTrue(CommandFactory.getCommand(CommandEnums.SHOOTER_INCREASE_SPEED_CMD));
        CO_DRIVE_CONTROLLER.povDown().onTrue(CommandFactory.getCommand(CommandEnums.SHOOTER_DECREASE_SPEED_CMD));


        CO_DRIVE_CONTROLLER.axisLessThan(5, -0.6).whileFalse(CommandFactory.getCommand(CommandEnums.PIVOT_INTAKE_DOWN_LS_CMD)).whileTrue(CommandFactory.getCommand(CommandEnums.INTAKE_PIVOT_UP_CMD));

        // CO_DRIVE_CONTROLLER.povUp().onTrue(cmdIncreaseRPM);
        // CO_DRIVE_CONTROLLER.povDown().onTrue(cmdDecreaseRPM);

        // CO_DRIVE_CONTROLLER.povUp().whileTrue(cmdFlywheelUp);
        // CO_DRIVE_CONTROLLER.povDown().whileTrue(cmdFlywheelDown);
        // CO_DRIVE_CONTROLLER.povLeft().whileTrue(cmdFlywheelLeft);
        // CO_DRIVE_CONTROLLER.povRight().whileTrue(cmdFlywheelRight);


        // CO_DRIVE_CONTROLLER.pov

        // CO_DRIVE_CONTROLLER.povCenter().whileFalse(cmdLowSpeed);
    }

}
