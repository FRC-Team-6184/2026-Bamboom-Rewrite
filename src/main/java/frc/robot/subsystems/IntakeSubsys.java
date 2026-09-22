package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DigitalInputOutput;
import frc.robot.Constants.DigitalValues;
import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.SoftwareObjects;

public class IntakeSubsys extends SubsystemBase {

    private DigitalInput kLimitSwitch;
    private TalonFX kPivotMotor;
    private TalonFX kIntakeMotor;

    Slot0Configs intakeMotorPIDConfigs;
    VelocityVoltage intakeMotorSpeedRequest = new VelocityVoltage(0.0);
    private double intakeSpeed = -4000 / 60.0;

    DoubleEntry intakeSpeedEntry = SoftwareObjects.networkTableInstance.getDoubleTopic("/Intake/Intake Speed").getEntry(0.0);

    /** Intake constructor. Perform all initializing regarding related motors here */
    public IntakeSubsys() {
        super();

        kLimitSwitch = DigitalInputOutput.INTAKE_LIMIT_SWITCH;
        kPivotMotor = MotorControllers.PIVOT_INTAKE_MOTOR;
        kIntakeMotor = MotorControllers.ACTIVE_INTAKE_MOTORS;

        //This isn't entirely correct, kS was simply really hard to measure properly. Assuming this value.
        //DO NOT TOUCH THIS, THESE ARE CONSTANTS I GOT FROM DATA, PLEASE NO TOUCHY
        Slot0Configs intakeMotorPIDConfigs = new Slot0Configs();
        intakeMotorPIDConfigs.kS = 0.0065; //assuming this value is close enough
        intakeMotorPIDConfigs.kA = 0.022339;
        intakeMotorPIDConfigs.kP = 0.14536;
        intakeMotorPIDConfigs.kV = 0.13043;
        intakeMotorPIDConfigs.kD = 0.0; //Just in case the default is not 0
        kIntakeMotor.getConfigurator().apply(intakeMotorPIDConfigs);

    }

    @Override
    public void periodic() {
        intakeSpeedEntry.set(kIntakeMotor.getVelocity().getValueAsDouble() * 60);
    }

    // if (kXboxController.getLeftTriggerAxis() > (RobotMap.DigitalValues.CONTROLLER_DEADZONE * 2)) {
    // kIntakeMotor.set(-0.3);
    // System.out.println("REEEE");
    // } else {
    // kIntakeMotor.set(0.0);
    // }
    // });
    // }

    public void pivotDown() {
        kPivotMotor.set(DigitalValues.INTAKE_PIVOT);
    }

    public void pivotStop() {
        kPivotMotor.set(0);
    }

    public void pivotUp() {
        kPivotMotor.set(-0.75 * DigitalValues.INTAKE_PIVOT);
    }

    public void startIntake() {
        kIntakeMotor.setControl(intakeMotorSpeedRequest.withVelocity(intakeSpeed));
    }

    public void stopIntake() {
        kIntakeMotor.set(0.0);
    }

    public void purgeIntake() {
        kIntakeMotor.set(0.45);
    }

    /**
     * 
     * @param rps Speed for intake motor to do in Rotations Per Second (<b><i> NOT ROTATIONS PER MINUTE </i></b>)
     */
    public void setIntakeSpeed(double rps) {
        // kIntakeMotor.setControl(intakeMotorSpeedRequest.withVelocity(rps));
        intakeSpeed = rps;

    }

    public TalonFX getPivotMotor() {
        return kPivotMotor;
    }

    public double getVelocity() {
        return kIntakeMotor.getVelocity().getValueAsDouble();
    }

    public void outtake() {
        kIntakeMotor.set(.75);
    }

    /**
     * 
     * @return True when switch is hit, false otherwise
     */
    public boolean isSwitchHit() {
        return !kLimitSwitch.get(); //True when not hit, false when hit, so not to make it function more as expected
    }


}
