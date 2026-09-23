package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DigitalInputOutput;
import frc.robot.Constants.DigitalValues.Speeds;
import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.SoftwareObjects;


// Subsystems should only contain:
// 1. All involved components
// 2. All actions the subsystem might do
// 3. Other dependencies for the above 2

// TODO: Clean up generally
public class IntakeSubsys extends SubsystemBase {
    private final DigitalInput kLimitSwitch;
    private final TalonFX kPivotMotor;
    private final TalonFX FL_INTAKE_MOTOR;
    private final TalonFX FR_INTAKE_MOTOR;

    Slot0Configs intakeMotorPIDConfigs; // Stuff regarding Feed-Forward
    VelocityVoltage intakeMotorSpeedRequest = new VelocityVoltage(0.0);

    private double intakeSpeed = -4000 / 60.0; // 4000 RPM / 60 Seconds to get RPS
    private double pivotSpeed = Speeds.INTAKE_PIVOT; // Make this into an RPS thing for consistency

    // TODO: Move this to a dedicated network table file
    DoubleEntry intakeSpeedEntry = SoftwareObjects.NETWORK_TABLE_INSTANCE.getDoubleTopic("/Intake/Intake Speed").getEntry(0.0);

    public IntakeSubsys() {
        super();

        kLimitSwitch = DigitalInputOutput.INTAKE_LIMIT_SWITCH;
        kPivotMotor = MotorControllers.PIVOT_INTAKE_MOTOR;
        FL_INTAKE_MOTOR = MotorControllers.FL_ACTIVE_INTAKE_MOTOR;
        FR_INTAKE_MOTOR = MotorControllers.FR_ACTIVE_INTAKE_MOTOR;
        

        // DO NOT TOUCH
        Slot0Configs intakeMotorPIDConfigs = new Slot0Configs();
        intakeMotorPIDConfigs.kS = 0.0065; // Assuming this value is close enough, hard to measure
        intakeMotorPIDConfigs.kA = 0.022339;
        intakeMotorPIDConfigs.kP = 0.14536;
        intakeMotorPIDConfigs.kV = 0.13043;
        intakeMotorPIDConfigs.kD = 0.0; // Just in case the default is not 0
        FL_INTAKE_MOTOR.getConfigurator().apply(intakeMotorPIDConfigs);
        FR_INTAKE_MOTOR.getConfigurator().apply(intakeMotorPIDConfigs);
    }

    @Override
    public void periodic() {
        intakeSpeedEntry.set(FL_INTAKE_MOTOR.getVelocity().getValueAsDouble() * 60);
        intakeSpeedEntry.set(FR_INTAKE_MOTOR.getVelocity().getValueAsDouble() * 60);
    }

    // Pivoting Motor
    public void pivotUp() {
        FL_INTAKE_MOTOR.set(-0.75 * pivotSpeed);
        FR_INTAKE_MOTOR.set(-0.75 * pivotSpeed);
    }

    public void pivotDown() {
        FL_INTAKE_MOTOR.set(pivotSpeed);
        FR_INTAKE_MOTOR.set(pivotSpeed);
    }

    public void pivotStop() {
        FL_INTAKE_MOTOR.set(0);
        FR_INTAKE_MOTOR.set(0);
    }

    // Intake Motors
    public void startIntake() {
        FL_INTAKE_MOTOR.setControl(intakeMotorSpeedRequest.withVelocity(intakeSpeed));
        FR_INTAKE_MOTOR.setControl(intakeMotorSpeedRequest.withVelocity(intakeSpeed));
    }

    public void stopIntake() {
        FL_INTAKE_MOTOR.set(0.0);
        FR_INTAKE_MOTOR.set(0.0);
    }

    public void purgeIntake() {
        FL_INTAKE_MOTOR.set(0.45);
        FR_INTAKE_MOTOR.set(0.45);
    }

    /**
     * @param rps Speed for intake motor to do in Rotations Per Second (<b><i> NOT ROTATIONS PER MINUTE </i></b>)
     */
    public void setIntakeSpeed(double rps) {
        // kIntakeMotor.setControl(intakeMotorSpeedRequest.withVelocity(rps));
        intakeSpeed = rps;
    }

    /**
     * 
     * @param rps
     * @see c Currently does not change anything, need to know what the {@code intakeSpeed} magic
     * numbers are so I know what magical numbers to put as pivotSpeed.
     */
    public void setPivotSpeed(double rps) {
        pivotSpeed = rps;
    }
    
    // public TalonFX getPivotMotor() {
    //     return kPivotMotor;
    // }

    /**
     * 
     * @return An array of the velocities of both the FR (index 0) and FL (index 1) Motors
     */
    public double[] getVelocity() {
        return new double[]{FL_INTAKE_MOTOR.getVelocity().getValueAsDouble(), FL_INTAKE_MOTOR.getVelocity().getValueAsDouble()};
    }

    /**
     * @return Returns true when the limit switch is active
     */
    public boolean isSwitchHit() {
        return !kLimitSwitch.get(); //True when not hit, false when hit, so not to make it function more as expected
    }


}
