package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.DigitalValues.Speeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.StatusSignal;

import edu.wpi.first.units.AngularVelocityUnit;
import edu.wpi.first.units.measure.AngularVelocity;



public class ShooterSubsys extends SubsystemBase {
    /**
     * One of the top shooter motors that actually launches the balls. It's on the <b>left</b> side when you're facing the intake.
     */
    private final TalonFX FLYWHEEL_LEFT_MOTOR = MotorControllers.FLYWHEEL_LEFT_MOTOR;
    /**
     * One of the top shooter motors that actually launches the balls. It's on the <b>right</b> side when you're facing the intake..
     */
    private final TalonFX FLYWHEEL_RIGHT_MOTOR = MotorControllers.FLYWHEEL_RIGHT_MOTOR;
    /**
     * Motor on the bottom of the shooter tower that "kicks" them towards the top of the shooter.
     */
    private final TalonFX KICKER_MOTOR = MotorControllers.SHOOTER_KICKER_MOTOR;
    /**
     * Motor that sends the balls towards the kicker motor. Called the "blender" as it moves them cyclically towards the kicker.
     */
    private final TalonFX BLENDER_MOTOR = MotorControllers.BLENDER_MOTOR; //NOTE: usually runs at -0.5

    //Speed requests for the individual motors. Separated to try to mitigate mistakes and ensure motors don't accidentally set to the wrong speed.
    private VelocityVoltage topMotorSpeedRequest = new VelocityVoltage(0);
    private VelocityVoltage kickerMotorSpeedRequest =new VelocityVoltage(0);
    private VelocityVoltage blenderMotorSpeedRequest = new VelocityVoltage(0);

    private AngularVelocity shooterVelocity = MotorControllers.FLYWHEEL_LEFT_MOTOR.getVelocity().getValue();

    private double flywheelTargetSpeed = -750 / 60.0; //Speed of the shooter's flywheels, set here so it can be used by multiple different commands.

    public ShooterSubsys() {
        super();

        // Set feedforward configs for the motors so they can be RPM controlled :D

        // TODO: Set one as a follower of the other
        // While these motors are linked together and it would be preferable just to have one as a follower of the other
        // I don't know if I can trust CTRE follower stuff as of right now, so I'm not gonna use it.
        // We'll have to test and find out after Spacecoast, I'm just being overly careful and anxious about something breaking
        ///- William H.
        FLYWHEEL_LEFT_MOTOR.getConfigurator().apply(MotorConstants.TOP_SHOOTER_CONFIG);
        FLYWHEEL_RIGHT_MOTOR.getConfigurator().apply(MotorConstants.TOP_SHOOTER_CONFIG);
        
        KICKER_MOTOR.getConfigurator().apply(MotorConstants.KICKER_SHOOTER_CONFIG);

        BLENDER_MOTOR.getConfigurator().apply(MotorConstants.BLENDER_SHOOTER_CONFIG);

    }

    @Override
    public void periodic() {
    }

    public void stopShooter() {
        setFlywheelTargetSpeed(0);
        setKickerSpeed(0);
        setBlenderSpeed(0);
    }

    /**
     * 
     * @return double RPS
     */
    public double getShooterRPS() {
        return RotationsPerSecond.convertFrom(shooterVelocity.magnitude(), shooterVelocity.unit());
    }

    /**
     * Sets the speed of the top shooter motors. Be careful that the speed is in Rotations per <b>Second</b> (RPS) and not Rotations per <b>Minute</b> (RPM)
     * @param rps Desired speed of the shooter in rotations per second
     */
    // TODO: Make this negative to reverse direction
    public void setFlywheelSpeed(double rps) {
        topMotorSpeedRequest.withFeedForward(rps);
        FLYWHEEL_LEFT_MOTOR.setControl(topMotorSpeedRequest);
        FLYWHEEL_RIGHT_MOTOR.setControl(topMotorSpeedRequest);
    }

    public void stopFlywheel() {
        setFlywheelTargetSpeed(0);
    }

    public void setKickerSpeed(double rps) {
        kickerMotorSpeedRequest.withFeedForward(rps);
        KICKER_MOTOR.setControl(kickerMotorSpeedRequest);
    }

    public void stopKicker() {
        setKickerSpeed(0);
    }

    // TODO: Make this negative to reverse direction
    public void setBlenderSpeed(double rps) {
        blenderMotorSpeedRequest.withFeedForward(rps);
        BLENDER_MOTOR.setControl(blenderMotorSpeedRequest);
    }

    public void stopBlender() {
        setBlenderSpeed(0);
    }

    public void setFlywheelTargetSpeed(double speed) {
        flywheelTargetSpeed = speed;
    }

    public double getFlywheelTargetSpeed() {
        return flywheelTargetSpeed;
    }

    public double getFlywheelVelocity() {
        return FLYWHEEL_LEFT_MOTOR.getVelocity().getValueAsDouble();
    }
}