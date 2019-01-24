package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Intake/Extake for Cargo comprised of two compliant wheel rollers
 */
public class Manipulator extends Subsystem
{
  TalonSRX master;

  public Manipulator()
  {
    master = new TalonSRX(RobotMap.manipulatorMaster);
  }
  //TODO: add limit switches to tell if we are holding a cargo or hatch, add way of controlling the motor, and configure the motor with basic talon config.

  @Override
  public void initDefaultCommand()
  {
  }
}
