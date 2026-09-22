package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.NetworkTable;

import frc.robot.Constants.Controller;
import frc.robot.Constants.DigitalValues;
import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.SoftwareObjects;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/* TODO:
 * Code in this file is still spaghetti, need to go thru and refactor
 * 
 */

public class ShooterSubsys extends SubsystemBase {
    private final TalonFX bottomMotor = MotorControllers.BOTTOM_SHOOTER_WHEEL;
    private final TalonFX topMotor = MotorControllers.TOP_SHOOTER_WHEEL;
    private final TalonFX blenderMotor = MotorControllers.BLENDER_MOTOR; //NOTE: usually runs at -0.5
    private final CommandXboxController controller = Controller.XBOX;
    private NetworkTable network = SoftwareObjects.networkTableInstance.getTable("Shooter");
    private DoubleEntry shooterRPMEntry = network.getDoubleTopic("ShooterRPM Actual").getEntry(0);
    private DoubleEntry shooterRPMTargetEntry = network.getDoubleTopic("ShooterRPM Target").getEntry(0);
    private DoubleEntry bottomRPMEntry = network.getDoubleTopic("BottomRPM Actual").getEntry(0);

    private NetworkTable pidTable = network.getSubTable("TopPID");
    private DoubleEntry ntKP = pidTable.getDoubleTopic("kP").getEntry(0.1733);
    private DoubleEntry ntKD = pidTable.getDoubleTopic("kD").getEntry(0.0);
    private DoubleEntry ntKV = pidTable.getDoubleTopic("kV").getEntry(0.11622);
    private DoubleEntry ntKS = pidTable.getDoubleTopic("kS").getEntry(0.12582);
    private DoubleEntry ntKA = pidTable.getDoubleTopic("kA").getEntry(0.0097241);

    private double m_kP = 0.1733, m_kD = 0.0, m_kV = 0.11622, m_kS = 0.12582, m_kA = 0.0097241;

    private double shooterRPMDest = DigitalValues.SHOOTER_HIGH_SPEED;
    private double m_targetRPM = 3250 / 60.0;
    private double kickerRPMDest = -4500 / 60.0; //placeholder values
    private double blenderRPMDest = 1.25 * shooterRPMDest;

    /**
     * Units are in RPS, Rotations Per Second, rather than RPM due to how I recorded the data used in FeedForward
     * <p>Since you're probably used to RPM, Rotations Per Minute, divide the value by 60 before putting it in.
     */
    private VelocityVoltage topMotorSpeedRequest = new VelocityVoltage(0);
    private VelocityVoltage bottomMotorSpeedRequest = new VelocityVoltage(0);
    private VelocityVoltage blenderMotorSpeedRequest = new VelocityVoltage(0);


    public ShooterSubsys() {
        super();

        shooterRPMEntry.set(0.0);
        shooterRPMTargetEntry.set(0.0);
        bottomRPMEntry.set(0.0);
        ntKP.set(m_kP);
        ntKD.set(m_kD);
        ntKV.set(m_kV);
        ntKS.set(m_kS);
        ntKA.set(m_kA);

        //Data collected from System Identification (whole complicated thing don't worry about it)
        //These are constants 
        //DO NOT TOUCH PLEASE PLEASE PLEASE
        Slot0Configs topShooterPIDConfig = new Slot0Configs();
        topShooterPIDConfig.kP = 0.1733;
        topShooterPIDConfig.kA = 0.0097241;
        topShooterPIDConfig.kV = 0.11622;
        topShooterPIDConfig.kS = 0.12582;
        topShooterPIDConfig.kD = 0.0; // What SysID gave me
        topMotor.getConfigurator().apply(topShooterPIDConfig);

        Slot0Configs bottomShooterPIDConfig = new Slot0Configs();
        bottomShooterPIDConfig.kP = 0.13694;
        bottomShooterPIDConfig.kA = 0.0019461;
        bottomShooterPIDConfig.kV = 0.11021;
        bottomShooterPIDConfig.kS = 0.027235;
        bottomShooterPIDConfig.kD = 0.0; //What SysID gave me
        bottomMotor.getConfigurator().apply(bottomShooterPIDConfig);
        bottomMotor.getConfigurator().apply(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive));

        Slot0Configs blenderPIDConfig = new Slot0Configs();
        blenderPIDConfig.kP = 0.14905;
        blenderPIDConfig.kA = 0.0029793;
        blenderPIDConfig.kV = 0.11111;
        blenderPIDConfig.kS = 0.049802;
        blenderPIDConfig.kD = 0.0; //Still what SysID gave me. This value probably defaults to 0.0, but I don't trust it.
        blenderMotor.getConfigurator().apply(blenderPIDConfig);

    }

    @Override
    public void periodic() {
        //TODO: fill this in and make it do things quite possibly :)
    }
}