package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

/**
 * Three stage cascading elevator
 */
public class Elevator extends Subsystem
{
  // Declare talons
  TalonSRX master, slave;
  // Amount of time to wait before reporting to Driver Station that action failed
  int timeoutMs = 20;

  public Elevator()
  {
    master = new TalonSRX(RobotMap.ElevatorMaster);
    slave = new TalonSRX(RobotMap.ElevatorSlave);

    // Factory default hardware to prevent unexpected behavior
    master.configFactoryDefault();
    slave.configFactoryDefault();

    /* Configure Sensor Source for Primary PID */
    // Constants.kPIDLoopIdx
    // timeoutMs
    master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

    master.setSensorPhase(true);
    master.setInverted(false);

    /* Set relevant frame periods to be at least as fast as periodic rate */
    master.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, timeoutMs);
    master.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, timeoutMs);

    /* Set the peak and nominal outputs */
    master.configNominalOutputForward(0, timeoutMs);
    master.configNominalOutputReverse(0, timeoutMs);
    master.configPeakOutputForward(1, timeoutMs);
    master.configPeakOutputReverse(-1, timeoutMs);

    /* Set Motion Magic gains in slot0 - see documentation */
    master.selectProfileSlot(0/* Constants.kSlotIdx */, 0 /* Constants.kPIDLoopIdx */);
    master.config_kF(0/* Constants.kSlotIdx */, 0, timeoutMs); // F
    master.config_kP(0/* Constants.kSlotIdx */, 0, timeoutMs); // P
    master.config_kI(0/* Constants.kSlotIdx */, 0, timeoutMs); // I
    master.config_kD(0/* Constants.kSlotIdx */, 0, timeoutMs); // D

    /* Set acceleration and vcruise velocity - see documentation */
    master.configMotionCruiseVelocity(15000, timeoutMs);
    master.configMotionAcceleration(6000, timeoutMs);

    /* Zero the sensor */
    master.setSelectedSensorPosition(0, 0 /* Constants.kPIDLoopIdx */, timeoutMs);
  }

  public void setHeightInches(double inches)
  {
    // "3539" should be changed to the circumference of the spool
    double encoderTicks = inches / 3539 * 4096;
    master.set(ControlMode.MotionMagic, encoderTicks);
  }

  @Override
  public void initDefaultCommand()
  {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}