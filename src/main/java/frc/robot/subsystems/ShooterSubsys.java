package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.MotorControllers;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ShooterSubsys extends SubsystemBase {
    /**
     * One of the top shooter motors that actually launches the balls. It's on the <b>left</b> side when you're facing the intake.
     */
    private final TalonFX shooterLeftMotor = MotorControllers.SHOOTER_LEFT_WHEEL;
    /**
     * One of the top shooter motors that actually launches the balls. It's on the <b>right</b> side when you're facing the intake..
     */
    private final TalonFX shooterRightMotor = MotorControllers.SHOOTER_RIGHT_WHEEL;
    /**
     * Motor on the bottom of the shooter tower that "kicks" them towards the top of the shooter.
     */
    private final TalonFX kickerMotor = MotorControllers.BOTTOM_SHOOTER_WHEEL;
    /**
     * Motor that sends the balls towards the kicker motor. Called the "blender" as it moves them cyclically towards the kicker.
     */
    private final TalonFX blenderMotor = MotorControllers.BLENDER_MOTOR; //NOTE: usually runs at -0.5

    //Speed requests for the individual motors. Separated to try to mitigate mistakes and ensure motors don't accidentally set to the wrong speed.
    private VelocityVoltage topMotorSpeedRequest = new VelocityVoltage(0);
    private VelocityVoltage kickerMotorSpeedRequest = new VelocityVoltage(0);
    private VelocityVoltage blenderMotorSpeedRequest = new VelocityVoltage(0);


    public ShooterSubsys() {
        super();

        //Set feedforward configs for the motors so they can be RPM controlled :D

        //TODO: Set one as a follower of the other
        //While these motors are linked together and it would be preferable just to have one as a follower of the other
        //I don't know if I can trust CTRE follower stuff as of right now, so I'm not gonna use it.
        //We'll have to test and find out after Spacecoast, I'm just being overly careful and anxious about something breaking
        ///- William H.
        shooterLeftMotor.getConfigurator().apply(MotorConstants.TOP_SHOOTER_CONFIG);
        shooterRightMotor.getConfigurator().apply(MotorConstants.TOP_SHOOTER_CONFIG);
        
        kickerMotor.getConfigurator().apply(MotorConstants.KICKER_SHOOTER_CONFIG);

        blenderMotor.getConfigurator().apply(MotorConstants.BLENDER_SHOOTER_CONFIG);

    }

    @Override
    public void periodic() {
        //TODO: fill this in and make it do things quite possibly :)
    }

    /**
     * Sets the speed of the top shooter motors. Be careful that the speed is in Rotations per <b>Second</b> (RPS) and not Rotations per <b>Minute</b> (RPM)
     * @param rps Desired speed of the shooter in rotations per second
     */
    public void setTopShooterSpeed(double rps) {
        topMotorSpeedRequest.withFeedForward(rps);
        shooterLeftMotor.setControl(topMotorSpeedRequest);
        shooterRightMotor.setControl(topMotorSpeedRequest);
    }

    public void stopTopShooter() {
        setTopShooterSpeed(0);
    }

    public void setKickerSpeed(double rps) {
        kickerMotorSpeedRequest.withFeedForward(rps);
        kickerMotor.setControl(kickerMotorSpeedRequest);
    }

    public void stopKicker() {
        setKickerSpeed(0);
    }

    public void setBlenderSpeed(double rps) {
        blenderMotorSpeedRequest.withFeedForward(rps);
        blenderMotor.setControl(blenderMotorSpeedRequest);
    }

    public void stopBlender() {
        setBlenderSpeed(0);
    }
}