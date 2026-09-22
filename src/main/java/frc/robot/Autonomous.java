package frc.robot;

import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import com.pathplanner.lib.auto.NamedCommands;

/* TODO:
 * Implement all features relating to autonomous mode
 * 
 * Copy and paste the related stuff from RobotContainer.java in the old repo
 */
public class Autonomous {

    AutonomousIntakeDownCommand cmdAutoIntakePivot;
    AutonomousStartIntakeCommand cmdAutoStartIntake;
    BlenderCommand cmdAutoBlender;

    public Autonomous() {
        // -William TODO: I think these were glitching things out, and these need to be done in a more robust and sensible way anyways
        // -Reece TODO: Need to make cmdAutoIntakeDown and related actual commands
        // -Reece I split up the methods the way I did because I don't want to store commands
        // into a variable of their type, because then we need to import the libraries just for that.
        NamedCommands.registerCommand(
            "AutoIntakeDown", 
            cmdAutoIntakeDown // TODO: new cmdAutoIntakeDown()
        );
        NamedCommands.registerCommand(
            "AutoIntakeStart", 
            cmdAutoStartIntake // TODO: new cmdAutoStartIntake()
        );
        NamedCommands.registerCommand(
            "BlenderCommand", 
            cmdAutoBlender.withTimeout(Seconds.of(4.0))
        );
        // NamedCommands.registerCommand("IntakePivotUpCommand", cmdPivotUp.withTimeout(Seconds.of(0.5)));
    }

}
