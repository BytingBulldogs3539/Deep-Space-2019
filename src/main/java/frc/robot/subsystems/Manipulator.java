package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.utilities.ByteDoubleSolenoid;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

/**
 * Intake/Extake for Cargo comprised of two compliant wheel rollers
 */
public class Manipulator extends Subsystem
{
  private ByteDoubleSolenoid panelIntakeSolenoid, cargoIntakeSolenoid, placementSolenoid,floorIntakeSolenoid;
  private TalonSRX master;

  public Manipulator()
  {
    // Initiation of Intake Talon
    master = new TalonSRX(RobotMap.manipulatorMaster);

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

    master.setNeutralMode(NeutralMode.Brake);

    
    floorIntakeSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.floorIntakeOn, RobotMap.floorIntakeOff, true);

    panelIntakeSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.panelIntakeOn, RobotMap.panelIntakeOff, true);
    cargoIntakeSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.cargoIntakeOn, RobotMap.cargoIntakeOff, false);
    placementSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.placementOn, RobotMap.placementOff, true);
  }

  // Grabs hatch panel from floor; not necessary for feeder station
  public void panelIntakeSetPosition(boolean shouldActive)
  {
    panelIntakeSolenoid.setPosition(shouldActive);
  }
  public void floorIntakeSetPosition(boolean shouldActive)
  {
    floorIntakeSolenoid.setPosition(shouldActive);
  }

  // Actuates intake arm over bumper to grab cargo
  public void cargoIntakeSetPosition(boolean shouldActive)
  {
    cargoIntakeSolenoid.setPosition(shouldActive);
  }

  // Places hatch panel onto hook tape
  public void placementSolenoidSetPosition(boolean shouldActive)
  {
    placementSolenoid.setPosition(shouldActive);
  }

  public void intake(double speed)
  {
    master.set(ControlMode.PercentOutput, speed);
  }

  @Override
  public void initDefaultCommand()
  {
    setDefaultCommand(new IntakeCommand());
  }
}
