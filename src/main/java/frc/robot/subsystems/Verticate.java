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

    /* Slave will imitate all commands sent to master e.g set() but not
     * configurations */
    topSlave.follow(topMaster);
    bottomSlave.follow(bottomMaster);

    // TODO: Configure Talons similar to Elevator/Turret
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
