package frc.robot.subsystems;

import java.util.Optional;
import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AutonomousEnums;

public class AutonomousSubsys extends SubsystemBase {

    private boolean isRedAlliance = true;
    private boolean isBlueAlliance = false;

    private PathPlannerAuto blueDepotTrench = new PathPlannerAuto("Blue Depot Trench");
    private PathPlannerAuto blueHumanTrench = new PathPlannerAuto("Blue Human Station Trench");
    private PathPlannerAuto redDepotTrench = new PathPlannerAuto("Red Depot Trench");
    private PathPlannerAuto redHumanTrench = new PathPlannerAuto("Red Human Station Trench");
    private PathPlannerAuto practice = new PathPlannerAuto("TestingAndPractice");
    private PathPlannerAuto blueDepotTrenchDouble = new PathPlannerAuto("Blue Depot Trench Double");
    private PathPlannerAuto redDepotTrenchDouble = new PathPlannerAuto("Red Depot Trench Double");

    private SendableChooser<AutonomousEnums> autoChooser = new SendableChooser<AutonomousEnums>();

    public AutonomousSubsys() {
        super();

        autoChooser.addOption("Blue Depot Trench", AutonomousEnums.BlueDepotTrench);
        autoChooser.addOption("Blue Human Trench", AutonomousEnums.BlueHumanTrench);
        autoChooser.addOption("Red Depot Trench", AutonomousEnums.RedDepotTrench);
        autoChooser.addOption("Red Human Trench", AutonomousEnums.RedHumanTrench);
        autoChooser.addOption("TestingAndPractice", AutonomousEnums.Practice);
        autoChooser.addOption("Blue Depot Trench DOUBLE", AutonomousEnums.BlueDepotTrenchDouble);
        autoChooser.addOption("Red Depot Trench DOUBLE", AutonomousEnums.RedDepotTrenchDouble);

        autoChooser.setDefaultOption("Blue Human Trench", AutonomousEnums.BlueHumanTrench);
        SmartDashboard.putData("AutoChooser", autoChooser);
    }

    public Optional<Pose2d> getSelectedAutoStartingPose() {
        AutonomousEnums selected = autoChooser.getSelected();
        PathPlannerAuto auto;
        if (selected == null) {
            auto = blueHumanTrench;
        } else {
            switch (selected) {
                case BlueDepotTrench:
                    auto = blueDepotTrench;
                    break;
                case RedDepotTrench:
                    auto = redDepotTrench;
                    break;
                case RedHumanTrench:
                    auto = redHumanTrench;
                    break;
                case Practice:
                    auto = practice;
                    break;
                case BlueDepotTrenchDouble:
                    auto = blueDepotTrenchDouble;
                    break;
                case RedDepotTrenchDouble:
                    auto = redDepotTrenchDouble;
                    break;
                default:
                    auto = blueHumanTrench;
                    break;
            }
        }

        return Optional.of(auto.getStartingPose());
    }

    public PathPlannerAuto getSelectedAuto() {
        AutonomousEnums selected = autoChooser.getSelected();
        if (selected == null) {
            setAsBlueAlliance();
            return blueHumanTrench;
        } else {
            switch (selected) {
                case BlueDepotTrench:
                    setAsBlueAlliance();
                    return blueDepotTrench;

                case BlueHumanTrench:
                    setAsBlueAlliance();
                    return blueHumanTrench;

                case RedDepotTrench:
                    setAsRedAlliance();
                    return redDepotTrench;

                case RedHumanTrench:
                    setAsRedAlliance();
                    return redHumanTrench;

                case Practice:
                    setAsBlueAlliance();
                    return practice;
                case BlueDepotTrenchDouble:
                    setAsBlueAlliance();
                    System.out.println("blue depot double!");
                    return blueDepotTrenchDouble;
                case RedDepotTrenchDouble:
                    setAsRedAlliance();
                    return redDepotTrenchDouble;
                default: //Defaults to Blue Human Trench in a hail mary, something has gone terribly wrong
                    System.out.println("SOMETHING WITH THE AUTO CHOOSER WENT TERRIBLY WRONG | " + selected.toString());
                    setAsBlueAlliance();
                    return blueHumanTrench;
            }
        }
    }

    private void setAsBlueAlliance() {
        isBlueAlliance = true;
        isRedAlliance = false;
    }

    private void setAsRedAlliance() {
        isBlueAlliance = false;
        isRedAlliance = true;
    }


}
