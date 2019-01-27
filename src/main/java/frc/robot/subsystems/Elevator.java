package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

/**
 * Three stage cascading elevator
 */
public class Elevator extends Subsystem
{
  // Declare talons
  TalonSRX master, slave;

  public GamePieceType gamePieceType;

  // TODO: add limit switches to tell if we are holding a cargo or hatch, add way
  // of controling the motor

  /**
   * Defines the set of levels that the elevator will lift to.
   * <li>{@link #CARGO}</li>
   * <li>{@link #HATCH}</li>
   * <li>{@link #NONE}</li>
   */
  public enum GamePieceType
  {
    /** The intake will be set to go to Cargo levels */
    CARGO,
    /** The intake will be set to go to Hatch levels */
    HATCH,
    /** the intake contains both a Hatch or a Cargo */
    BOTH,
    /** the intake contains neither a Hatch or a Cargo */
    NONE
  }

  /**
   * Defines the level that the elevator will lift to.
   * <li>{@link #High}</li>
   * <li>{@link #Middle}</li>
   * <li>{@link #Low}</li>
   */
  public enum ElevatorHeight
  {
    /** The intake will be set to go to Cargo levels */
    High,
    /** The intake will be set to go to Hatch levels */
    Middle,
    /** The intake contains neither a Hatch or a Cargo */
    Low
  }

  // TODO: we need to add a feature for a limit switch for two things to stop the
  // elevator at the bottom and to zero the encoder at the bottom.
  public Elevator()
  {
    // Initiation of Elevator Talons
    master = new TalonSRX(RobotMap.elevatorMaster);
    slave = new TalonSRX(RobotMap.elevatorSlave);

    // Factory default hardware to prevent unexpected behavior
    master.configFactoryDefault();
    slave.configFactoryDefault();

    /* Slave will imitate all commands sent to master e.g set() but not
     * configurations */
    slave.follow(master);

    /* Basic config for Talons */
    TalonSRXConfiguration basicTalonConfig = new TalonSRXConfiguration();

    /* Set the peak and nominal outputs */
    basicTalonConfig.nominalOutputForward = 0.0;
    basicTalonConfig.nominalOutputReverse = 0.0;
    basicTalonConfig.peakOutputForward = 1.0;
    basicTalonConfig.peakOutputReverse = -1.0;

    /* Set to 27 because Talons are on 30 amp breaker */
    basicTalonConfig.continuousCurrentLimit = 27;
    basicTalonConfig.peakCurrentLimit = 40;
    basicTalonConfig.peakCurrentDuration = 100;

    /* Compensates for overcharging batteries. PID acts differently with different
     * voltage. Sets Max Voltage */
    basicTalonConfig.voltageCompSaturation = 12.2;

    master.configAllSettings(basicTalonConfig);
    slave.configAllSettings(basicTalonConfig);

    /* Configure Sensor Source for Primary PID */
    // Constants.kPIDLoopIdx
    // timeoutMs
    master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.timeoutMs);

    // TODO: This may need to be changed.

    /* Sets phase of sensor so forward/reverse on sensor is synced with
     * forward/reverse on talon */
    master.setSensorPhase(true);
    master.setInverted(false);

    /* Automatically tries to stop motors when not being powered */
    master.setNeutralMode(NeutralMode.Brake);
    slave.setNeutralMode(NeutralMode.Brake);

    /* Set relevant frame periods to be at least as fast as periodic rate */
    master.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.timeoutMs);
    master.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.timeoutMs);

    /* Set Motion Magic gains in slot 0 - see documentation */
    master.selectProfileSlot(0, 0);
    master.config_kF(0, RobotMap.elevatorGains.f, RobotMap.timeoutMs); // F
    master.config_kP(0, RobotMap.elevatorGains.p, RobotMap.timeoutMs); // P
    master.config_kI(0, RobotMap.elevatorGains.i, RobotMap.timeoutMs); // I
    master.config_kD(0, RobotMap.elevatorGains.d, RobotMap.timeoutMs); // D

    // TODO: This may need to be changed.
    /* Set acceleration and vcruise velocity - see documentation */
    master.configMotionCruiseVelocity(15000, RobotMap.timeoutMs);
    master.configMotionAcceleration(6000, RobotMap.timeoutMs);

    /* Zero the sensor */
    master.setSelectedSensorPosition(0, 0, RobotMap.timeoutMs);
  }

  /**
   * Allows us to move our elevator using motion magic to the specified height in
   * inches.
   * 
   * @param inches
   *                 The height (in inches) that the elevator will move to.
   */
  public void setHeightInches(double inches)
  {
    // TODO: This may need to be changed.
    double encoderTicks = inches / 3.63 * 4096;
    master.set(ControlMode.MotionMagic, encoderTicks);
    System.out.println("set" + encoderTicks);
  }

  /**
   * Allows us to move our elevator using motion magic to the specified height in
   * inches.
   * 
   * @param inches
   *                 The height (in inches) that the elevator will move to.
   */
  public void setHeightCargo(ElevatorHeight height)
  {
    switch (height)
    {
    case High:
      setHeightInches(RobotMap.cargoHigh);
      break;
    case Middle:
      setHeightInches(RobotMap.cargoMiddle);
      break;
    case Low:
      setHeightInches(RobotMap.cargoLow);
      break;
    }
  }

  public void setHeightHatch(ElevatorHeight height)
  {
    switch (height)
    {
    case High:
      setHeightInches(RobotMap.hatchHigh);
      break;
    case Middle:
      setHeightInches(RobotMap.hatchMiddle);
      break;
    case Low:
      setHeightInches(RobotMap.hatchLow);
      break;
    }
  }

  // TODO create a method to return the limit switch states.

  // TODO: Add a feature for override control.
  @Override
  public void initDefaultCommand()
  {

  }
}