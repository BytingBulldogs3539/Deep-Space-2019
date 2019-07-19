package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import frc.robot.motionprofiling.PlotThread;
import frc.robot.utilities.*;

/**
 * Active lazy susan
 */
public class Turret extends Subsystem
{
  // Declare talons
  TalonSRX master;

  public Turret()
  {
    // Initiation of Turret Talons
    master = new TalonSRX(RobotMap.turretMaster);

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

    /* Configure Sensor Source for Primary PID */
    // Constants.kPIDLoopIdx
    // timeoutMs
    master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.timeoutMs);
    //master.configRemoteFeedbackFilter(RobotMap.MRTalon, RemoteSensorSource.GadgeteerPigeon_Yaw, 0);
    //master.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor0);

    /* Sets phase of sensor so forward/reverse on sensor is synced with
     * forward/reverse on talon */
    master.setSensorPhase(true);
    master.setInverted(true);

    /* Make our motor not want to turn. */
    master.setNeutralMode(NeutralMode.Brake);

    /* Set relevant frame periods to be at least as fast as periodic rate */
    master.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.timeoutMs);
    master.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.timeoutMs);

    /* Set Motion Magic gains in slot 0 - see documentation */
    master.selectProfileSlot(0, 0);

    master.config_kF(0, RobotMap.turretGains.f, RobotMap.timeoutMs); // F
    master.config_kP(0, RobotMap.turretGains.p, RobotMap.timeoutMs); // P
    master.config_kI(0, RobotMap.turretGains.i, RobotMap.timeoutMs); // I
    master.config_kD(0, RobotMap.turretGains.d, RobotMap.timeoutMs); // D

    // TODO: Change possible
    /* Set acceleration and vcruise velocity - see documentation */
    master.configMotionCruiseVelocity(12000, RobotMap.timeoutMs);
    master.configMotionAcceleration(1200, RobotMap.timeoutMs);

    // TODO: config the scurve strength
    master.configMotionSCurveStrength(0);//was 2 

    // PlotThread test = new PlotThread(master);
    /* Zero the sensor */
    master.setSelectedSensorPosition(0, 0, RobotMap.timeoutMs);
  }

  /**
   * Rotates the turret to an angle respects soft limits, counter clockwise is
   * negative
   * 
   * 
   * 
   */
  public void zero(){
    master.setSelectedSensorPosition(0, 0, RobotMap.timeoutMs);

  }
  public void setPosition(double degrees)
  {
    double currentPosition = getAngle();
    if (degrees == 180 && currentPosition < 0)
    {
      degrees = -180;
    }
    setRotation(degrees);
  }

  /* Rotates turret to an angle; soft limits will interfere */
  public void setRotation(double degrees)
  {
    double rotations = degrees / 360 * RobotMap.turretGearRatio;
    double encoderTicks = rotations * RobotMap.encTicksPerRot;

    master.set(ControlMode.MotionMagic, encoderTicks);
  }

  /* Gets the current angle of the turret */
  public double getAngle()
  {
    return encoderTicksToDegrees(master.getSelectedSensorPosition());
  }

  /* Converts encoder ticks into turret angle */
  public double encoderTicksToDegrees(double encoderTicks)
  {
    double irotations = encoderTicks / RobotMap.encTicksPerRot;
    double degrees = irotations / RobotMap.turretGearRatio * 360.0;

    return degrees;
  }

  public boolean finished()
  {

    return Tolerant.withinTolerance(master.getSelectedSensorPosition(0), master.getActiveTrajectoryPosition(), 100);
  }

  /* Sets the speed of the turret motor */
  public void setSpeed(double speed)
  {
    master.set(ControlMode.PercentOutput, speed);
  }

  public void neutralOutput()
  {
    master.neutralOutput();
  }

  @Override
  public void initDefaultCommand()
  {
  }
}