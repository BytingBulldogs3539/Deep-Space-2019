package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

/**
 * Comprised of two 90 degree elbows and two curved legs
 */
public class Verticate extends Subsystem
{
  // Declare Talons
  TalonSRX topMaster, topSlave, bottomMaster, bottomSlave;

  public Verticate()
  {
    // Initiation of Verticate Talons
    topMaster = new TalonSRX(RobotMap.verticateTopMaster);
    topSlave = new TalonSRX(RobotMap.verticateTopSlave);
    bottomMaster = new TalonSRX(RobotMap.verticateBottomMaster);
    bottomSlave = new TalonSRX(RobotMap.verticateBottomSlave);

    /* Slaves will imitate all commands sent to masters e.g set() but not
     * configurations */
    topSlave.follow(topMaster);
    bottomSlave.follow(bottomMaster);

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

    topMaster.configAllSettings(basicTalonConfig);
    bottomMaster.configAllSettings(basicTalonConfig);
    topSlave.configAllSettings(basicTalonConfig);
    bottomSlave.configAllSettings(basicTalonConfig);

    /* Configure Sensor Source for Primary PID */
    // Constants.kPIDLoopIdx
    // timeoutMs
    topMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.timeoutMs);
    bottomMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, RobotMap.timeoutMs);

    /* Sets phase of sensor so forward/reverse on sensor is synced with
     * forward/reverse on talon */
    topMaster.setSensorPhase(true);
    topMaster.setInverted(false);
    bottomMaster.setSensorPhase(true);
    bottomMaster.setInverted(false);

    topMaster.setNeutralMode(NeutralMode.Brake);
    bottomMaster.setNeutralMode(NeutralMode.Brake);
    topSlave.setNeutralMode(NeutralMode.Brake);
    bottomSlave.setNeutralMode(NeutralMode.Brake);

    /* Set relevant frame periods to be at least as fast as periodic rate */
    topMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.timeoutMs);
    topMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.timeoutMs);
    bottomMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, RobotMap.timeoutMs);
    bottomMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, RobotMap.timeoutMs);

    /* Set Motion Magic gains in slot 0 - see documentation */
    topMaster.selectProfileSlot(0, 0);
    topMaster.config_kF(0, RobotMap.turretGains.f, RobotMap.timeoutMs); // F
    topMaster.config_kP(0, RobotMap.turretGains.p, RobotMap.timeoutMs); // P
    topMaster.config_kI(0, RobotMap.turretGains.i, RobotMap.timeoutMs); // I
    topMaster.config_kD(0, RobotMap.turretGains.d, RobotMap.timeoutMs); // D
    bottomMaster.selectProfileSlot(0, 0);
    bottomMaster.config_kF(0, RobotMap.turretGains.f, RobotMap.timeoutMs); // F
    bottomMaster.config_kP(0, RobotMap.turretGains.p, RobotMap.timeoutMs); // P
    bottomMaster.config_kI(0, RobotMap.turretGains.i, RobotMap.timeoutMs); // I
    bottomMaster.config_kD(0, RobotMap.turretGains.d, RobotMap.timeoutMs); // D

    /* Set acceleration and vcruise velocity - see documentation */
    topMaster.configMotionCruiseVelocity(15000, RobotMap.timeoutMs);
    topMaster.configMotionAcceleration(6000, RobotMap.timeoutMs);
    bottomMaster.configMotionCruiseVelocity(15000, RobotMap.timeoutMs);
    bottomMaster.configMotionAcceleration(6000, RobotMap.timeoutMs);

    /* Zero the sensor */
    topMaster.setSelectedSensorPosition(0, 0, RobotMap.timeoutMs);
    bottomMaster.setSelectedSensorPosition(0, 0, RobotMap.timeoutMs);
  }

  // TODO: Create buttons in OI to control manually
  // TODO: Create feedback from pigeon to keep robot level when climbing
  // TODO: Create feedback from encoders to keep robot level when climbing (back
  // up method encase pigeon fails)
  // TODO: Create solenoids (possibly?) to keep mechanisms in place during teleop

  @Override
  public void initDefaultCommand()
  {
  }
}
