package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

/**
 * Active lazy susan
 */
public class Turret extends Subsystem
{
  // Declare talons
  TalonSRX master, slave;

  public Turret()
  {
    // Initiation of Turret Talons
    master = new TalonSRX(RobotMap.turretMaster);
    slave = new TalonSRX(RobotMap.turretSlave);

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

    /* Sets phase of sensor so forward/reverse on sensor is synced with
     * forward/reverse on talon */
    master.setSensorPhase(true);
    master.setInverted(false);

    master.setNeutralMode(NeutralMode.Brake);
    slave.setNeutralMode(NeutralMode.Brake);

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
    master.configMotionCruiseVelocity(15000, RobotMap.timeoutMs);
    master.configMotionAcceleration(6000, RobotMap.timeoutMs);

    /* Zero the sensor */
    master.setSelectedSensorPosition(0, 0, RobotMap.timeoutMs);
  }

  public void setRotation(double degrees)
  {
    double rotations = degrees / 360 * (RobotMap.largeGear / RobotMap.smallGear);
    double encoderTicks = rotations * RobotMap.encTicksPerRot;

    master.set(ControlMode.MotionMagic, encoderTicks);
  }

  public double encoderTicksToDegrees(double encoderTicks)
  {
    double irotations = encoderTicks * 360 * (RobotMap.smallGear / RobotMap.largeGear);
    double degrees = irotations / RobotMap.encTicksPerRot;

    return degrees;
  }

  public void setSpeed(double speed)
  {
    master.set(ControlMode.PercentOutput, speed);
  }

  public void setNeutralOutput()
  {
    master.neutralOutput();
  }

  @Override
  public void initDefaultCommand()
  {
  }
}