package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.utilities.bbDoubleSolenoid;

/*
 * Add your docs
 * here is an autistically long comment that i cannot spell correctly but this is a testkh
 */
public class Solenoids extends Subsystem
{
  public bbDoubleSolenoid panelSolenoid;
  public bbDoubleSolenoid intakeSolenoid;

  public Solenoids()
  {
    // TODO: add solenoid ports to RobotMap
    panelSolenoid = new bbDoubleSolenoid(0, 0, 0, false);
    intakeSolenoid = new bbDoubleSolenoid(0, 0, 0, false);
  }

  @Override

  public void initDefaultCommand()
  {
  }
}