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
  private ByteDoubleSolenoid  cargoIntakeSolenoid, placementSolenoid,extensionSolenoid,hatchSolenoid;
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

    
  //  floorIntakeSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.floorIntakeOn, RobotMap.floorIntakeOff, true);
    hatchSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.hatchInOn, RobotMap.hatchInOff, true);

    cargoIntakeSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.cargoIntakeOn, RobotMap.cargoIntakeOff, false);
    placementSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.placementOn, RobotMap.placementOff, true);
    extensionSolenoid = new ByteDoubleSolenoid(RobotMap.pcm, RobotMap.hatchExtendOn, RobotMap.hatchExtendOff, true);
  }

   public void HatchSetPosition(boolean shouldActive)
   {
     hatchSolenoid.setPosition(shouldActive); // exends hatch mechanism 
  
    }
  
   public void ExtensionSetPosition(boolean shouldActive)
   {
     extensionSolenoid.setPosition(shouldActive);//extends distance of hatch mechanism
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
