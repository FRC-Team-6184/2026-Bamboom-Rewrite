package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkFlex;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DIO;
import frc.robot.Constants.DigitalValues.Speeds;
import frc.robot.Constants.MotorControllers;
import frc.robot.Constants.SoftwareObjects;


// Subsystems should only contain:
// 1. All involved components
// 2. All actions the subsystem might do
// 3. Other dependencies for the above 2

// TODO: Clean up generally
public class IntakeSubsys extends SubsystemBase {
    private final DigitalInput INTAKE_LIMIT_SWITCH;
    private final TalonFX INTAKE_PIVOT_MOTOR;
    private final SparkFlex FL_INTAKE_MOTOR;
    private final SparkFlex FR_INTAKE_MOTOR;

    VelocityVoltage intakeMotorSpeedRequest = new VelocityVoltage(0.0);

    private double pivotSpeed = Speeds.INTAKE_PIVOT; 

    // TODO: Move this to a dedicated network table file
    DoubleEntry intakeSpeedEntry = SoftwareObjects.NETWORK_TABLE_INSTANCE.getDoubleTopic("/Intake/Intake Speed").getEntry(0.0);

    public IntakeSubsys() {
        super();

        INTAKE_LIMIT_SWITCH = DIO.INTAKE_LIMIT_SWITCH;
        INTAKE_PIVOT_MOTOR = MotorControllers.INTAKE_PIVOT_MOTOR;
        FL_INTAKE_MOTOR = MotorControllers.FL_ACTIVE_INTAKE_MOTOR;
        FR_INTAKE_MOTOR = MotorControllers.FR_ACTIVE_INTAKE_MOTOR;
    }

    @Override
    public void periodic() {
        
    }

    // Pivoting Motor
    public void pivotUp() {
        INTAKE_PIVOT_MOTOR.set(-pivotSpeed * 0.6); // Replace -0.75 with one of the speed constants
    }

    public void pivotDown() {
        INTAKE_PIVOT_MOTOR.set(pivotSpeed);
    }

    public void pivotStop() {
        INTAKE_PIVOT_MOTOR.set(0);
    }

    // Intake Motors
    public void startIntake() {
        FL_INTAKE_MOTOR.set(1.0);
        FR_INTAKE_MOTOR.set(1.0); // Currently reversed in software, fix after space-coast
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
     * 
     * @return Returns true when the limit switch is active
     */
    public boolean isSwitchHit() {
        return !INTAKE_LIMIT_SWITCH.get(); //True when not hit, false when hit, so not to make it function more as expected
    }

    /**
     * 
     * @param rps Speed for intake motor to do in Rotations Per Second (<b><i> NOT ROTATIONS PER MINUTE </i></b>)
     * @see c Currently no implementation
     */
    public void setIntakeSpeed(double rps) {
        // kIntakeMotor.setControl(intakeMotorSpeedRequest.withVelocity(rps));
    }

    /**
     * 
     * @param speed as a value from 0 to 1
     */
    public void setPivotSpeed(double speed) {
        pivotSpeed = speed;
    }
    
    /**
     * 
     * @return An array of the velocities of both the FR (index 0) and FL (index 1) Motors
     */
    // public double[] getVelocity() {
        
    // }


}
