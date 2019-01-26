package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.utilities.bbDoubleSolenoid;

/**
 * Intake/Extake for Cargo comprised of two compliant wheel rollers
 */
public class Manipulator extends Subsystem
{
  bbDoubleSolenoid intakeSolenoid;
  TalonSRX master;


  public Manipulator()
  {
    master = new TalonSRX(RobotMap.manipulatorMaster);
    intakeSolenoid = new bbDoubleSolenoid(RobotMap.pcm, RobotMap.manipulatorOn, RobotMap.manipulatorOff, true);
  }
  // TODO: add limit switches to tell if we are holding a cargo or hatch, add way
  // of controlling the motor, and configure the motor with basic talon config.

  @Override
  public void initDefaultCommand()
  {
  }

  public void trigger(boolean shouldActive)//True for down; False for up
  {
    if(shouldActive)
      intakeSolenoid.forward();
    else
      intakeSolenoid.reverse();

  }

}
