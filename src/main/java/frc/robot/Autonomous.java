package frc.robot;

import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.Constants.CommandEnums;
import frc.robot.commands.CommandFactory;

/* TODO:
 * Implement all features relating to autonomous mode
 */
public class Autonomous {

    public Autonomous() {
        // -William TODO: I think these were glitching things out, and these need to be done in a more robust and sensible way anyways
        NamedCommands.registerCommand(
            "AutoIntakeDown", 
            CommandFactory.getCommand(CommandEnums.AUTONOMOUS_INTAKE_DOWN_CMD)
        );
        NamedCommands.registerCommand(
            "AutoIntakeStart", 
            CommandFactory.getCommand(CommandEnums.AUTONOMOUS_START_INTAKE_CMD) 
        );
        // -Reece TODO: Verify that the new BLENDER_CMD is the same as the old cmdAutoBlender
        // cuz it might not be working otherwise
        NamedCommands.registerCommand(
            "BlenderCommand", 
            CommandFactory.getCommand(CommandEnums.BLENDER_CMD).withTimeout(Seconds.of(4.0))
        );
        // NamedCommands.registerCommand("IntakePivotUpCommand", cmdPivotUp.withTimeout(Seconds.of(0.5)));
    }

}
