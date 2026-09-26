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
        
        // INTAKE SPEED CHANGE
        CO_DRIVE_CONTROLLER.povUp().onTrue(
            CommandFactory.getCommand(CommandEnums.SHOOTER_INCREASE_SPEED_CMD)
        );

        CO_DRIVE_CONTROLLER.povDown().onTrue(
            CommandFactory.getCommand(CommandEnums.SHOOTER_DECREASE_SPEED_CMD)
        );

        // ACTIVATE INTAKE
        CO_DRIVE_CONTROLLER.L1().onTrue(
            CommandFactory.getCommand(CommandEnums.ACTIVATE_INTAKE_CMD)
        );

        // PURGE INTAKE
        CO_DRIVE_CONTROLLER.axisGreaterThan(4, 0.8).whileTrue(
            CommandFactory.getCommand(CommandEnums.PURGE_INTAKE_CMD)
        );

        // PIVOT INTAKE DOWN
        CO_DRIVE_CONTROLLER.axisLessThan(5, -0.6).whileFalse(
            CommandFactory.getCommand(CommandEnums.PIVOT_INTAKE_DOWN_LS_CMD)).whileTrue(
                CommandFactory.getCommand(CommandEnums.INTAKE_PIVOT_UP_CMD)
            );

        // ACTIVATE SHOOTER on R1 
        CO_DRIVE_CONTROLLER.R1().onTrue(
            CommandFactory.getCommand(CommandEnums.ACTIVATE_SHOOTER_CMD)
        );

        // // XFORMATION
        // DRIVE_CONTROLLER.x().onTrue(
        //     CommandFactory.getCommand(CommandEnums.XFORMATION_CMD)
        // );

        // // RESET THE GYRO
        // DRIVE_CONTROLLER.rightBumper().and(DRIVE_CONTROLLER.leftBumper()).whileTrue(
        //     CommandFactory.getCommand(CommandEnums.RESET_GYRO_CMD)
        // );

        // // LOCK ON
        // DRIVE_CONTROLLER.b().whileTrue(
        //     CommandFactory.getCommand(CommandEnums.LOCK_ON_CMD)
        // );
    }

}
