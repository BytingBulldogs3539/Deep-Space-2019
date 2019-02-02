package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.utilities.ByteDoubleSolenoid;

/**
 * Intake/Extake for Cargo comprised of two compliant wheel rollers
 */
public class Manipulator extends Subsystem
{
  ByteDoubleSolenoid manipulatorSolenoid;
  TalonSRX master;

  public Manipulator()
  {
    master = new TalonSRX(RobotMap.manipulatorMaster);
    manipulatorSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.manipulatorOn, RobotMap.manipulatorOff, false);
  }

  @Override
  public void initDefaultCommand()
  {
  }

  public void trigger(boolean shouldActive)// True for down; False for up
  {
    if (shouldActive)
      manipulatorSolenoid.forward();
    else
      manipulatorSolenoid.reverse();

  }

}
