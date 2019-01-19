package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.utilities.bbDoubleSolenoid;

/*
 * Add your docs
 * here is an autistically long comment that i cannot spell correctly but this is a testkh
 */
<<<<<<< HEAD
public class Solenoids extends Subsystem
{
  public bbDoubleSolenoid panelSolenoid;
  public bbDoubleSolenoid intakeSolenoid;
=======
public class Solenoids extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
>>>>>>> 274ace53dc84b935daaafb0ab2374289ba0c4ae8

  public Solenoids()
  {
    // TODO: add solenoid ports to RobotMap
    panelSolenoid = new bbDoubleSolenoid(0, 0, 0, false);
    intakeSolenoid = new bbDoubleSolenoid(0, 0, 0, false);
  }

  @Override

  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
 
}
}