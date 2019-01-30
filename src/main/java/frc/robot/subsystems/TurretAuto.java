/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import frc.robot.Robot;
import frc.robot.commands.*;

/**
 * Add your docs here.
 */
public class TurretAuto extends PIDSubsystem {
  /**
   * Add your docs here.
   */
  public TurretAuto() {
    // Intert a subsystem name and PID values here
    super("SubsystemName", 1, 2, 3);
    // Use these to get going:
    
     setSetpoint(0); // Sets where the PID controller should move the system
    // to
    // enable() - Enables the PID controller.
  }

  @Override
  public void initDefaultCommand() {
   // setDefaultCommand(new AutoTurret());
  }

  @Override
  protected double returnPIDInput() {
   
    ;
    return Robot.byteVision.getPixeloffset(); // Return your input value for the PID loop
  }

  @Override
  protected void usePIDOutput(double output) {
    Robot.turret.setSpeed(output);
    // Use output to drive your system
   
  }
}
