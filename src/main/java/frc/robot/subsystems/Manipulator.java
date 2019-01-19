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

  @Override
  public void initDefaultCommand()
  {
  }
}
