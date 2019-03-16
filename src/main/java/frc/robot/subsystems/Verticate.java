package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.RemoteLimitSwitchSource;

/**
 * Comprised of two 90 degree elbows and two curved legs
 */
public class Verticate extends Subsystem
{
  // Declare Talons
  TalonSRX master, slave;

  public Verticate()
  {
    // Initiation of Verticate Talons
    master = new TalonSRX(RobotMap.verticateMaster);
    slave = new TalonSRX(RobotMap.verticateSlave);

    /* Slaves will imitate all commands sent to masters e.g set() but not
     * configurations */

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
    slave.follow(master);
     master.setNeutralMode(NeutralMode.Brake);
     slave.setNeutralMode(NeutralMode.Brake);
     

    
    
    master.configForwardLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX,LimitSwitchNormal.NormallyOpen,RobotMap.verticateSlave,10);
     master.configReverseLimitSwitchSource(RemoteLimitSwitchSource.RemoteTalonSRX,LimitSwitchNormal.NormallyOpen,RobotMap.verticateSlave,10);

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
  
  public void climb(double speed)
  {
    master.set(ControlMode.PercentOutput,speed);
    //slave.set(ControlMode.PercentOutput,speed);
  }
  public void neutralOutput()
  {
    master.neutralOutput();
  }
 
}

