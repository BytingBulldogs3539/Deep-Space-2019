package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import frc.robot.motionprofiling.PlotThread;
import frc.robot.utilities.*;

/**
 * Three stage cascading elevator
 */
public class Elevator extends Subsystem
{
  // Declare talons
  TalonSRX master, slave;

  public GamePieceType gamePieceType = GamePieceType.HATCH;

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
    Low,

    Home
  }

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
    basicTalonConfig.voltageCompSaturation = RobotMap.voltageCompSaturation;

    master.configAllSettings(basicTalonConfig);
    slave.configAllSettings(basicTalonConfig);

    /* Configure Sensor Source for Primary PID */
    // Constants.kPIDLoopIdx
    // timeoutMs
    master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.timeoutMs);

    /* Sets phase of sensor so forward/reverse on sensor is synced with
     * forward/reverse on talon */
    master.setSensorPhase(false);
    master.setInverted(true);
    slave.setInverted(true);

    /* Automatically tries to stop motors whe\n not being powered */
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

    /* Set acceleration and vcruise velocity - see documentation */
    master.configMotionCruiseVelocity(7000, RobotMap.timeoutMs);
    master.configMotionAcceleration(2000, RobotMap.timeoutMs);

    // TODO: config the scurve strength
    master.configMotionSCurveStrength(0);//was 2

    /* Zero the sensor */
    master.setSelectedSensorPosition(0, 0, RobotMap.timeoutMs);
    master.configClearPositionOnLimitR(true, 10);
    master.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
    master.configForwardLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX, LimitSwitchNormal.NormallyOpen, RobotMap.elevatorSlave, 10);
    master.configForwardSoftLimitEnable(false);// removed

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
    double encoderTicks = (inches - (9)) * RobotMap.InchesToElevatorEncMultiplier;
    master.set(ControlMode.MotionMagic, encoderTicks);
    //System.out.println("set" + encoderTicks);

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
      setHeightInches(RobotMap.cargoHigh + RobotMap.cargoHighOffset);
      break;
    case Middle:
      setHeightInches(RobotMap.cargoMiddle + RobotMap.cargoMiddleOffset);
      break;
    case Low:
      setHeightInches(RobotMap.cargoLow + RobotMap.cargoLowOffset);
      break;
    case Home:
      setHeightInches(RobotMap.home);
      break;
    }
  }

  public void setHeightHatch(ElevatorHeight height)
  {
    switch (height)
    {
    case High:
      setHeightInches(RobotMap.hatchHigh + RobotMap.hatchHighOffset);
      break;
    case Middle:
      setHeightInches(RobotMap.hatchMiddle + RobotMap.hatchMiddleOffset);
      break;
    case Low:
      setHeightInches(RobotMap.hatchLow + RobotMap.hatchLowOffset);
      break;
    case Home:
      setHeightInches(RobotMap.home);
      break;
    }
  }

  public void setPower(double power)
  {
    master.set(ControlMode.PercentOutput, power);
  }

  public void neutralOutput()
  {
    master.neutralOutput();
  }

  public boolean finished()
  {
    return Tolerant.withinTolerance(master.getSelectedSensorPosition(0), master.getActiveTrajectoryPosition(), 100);

  }
  

  // TODO create a method to return the limit switch states.

  @Override
  public void initDefaultCommand()
  {

  }
}