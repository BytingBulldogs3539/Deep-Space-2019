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
  private ByteDoubleSolenoid panelIntakeSolenoid, cargoIntakeSolenoid, placementLeftSolenoid, placementRightSolenoid;
  private TalonSRX master;

  public Manipulator()
  {
    master = new TalonSRX(RobotMap.manipulatorMaster);
    panelIntakeSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.manipulatorOn, RobotMap.manipulatorOff, true);
    cargoIntakeSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.manipulatorOn, RobotMap.manipulatorOff, true);
    placementLeftSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.manipulatorOn, RobotMap.manipulatorOff, true);
    placementRightSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.manipulatorOn, RobotMap.manipulatorOff, true);
  }

  @Override
  public void initDefaultCommand()
  {
  }

  public void triggerPanelIntake(boolean shouldActive)// True for down; False for up
  {
    if (shouldActive)
      panelIntakeSolenoid.forward();
    else
      panelIntakeSolenoid.reverse();
  }

}
