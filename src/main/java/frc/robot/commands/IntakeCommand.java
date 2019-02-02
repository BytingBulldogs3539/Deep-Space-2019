/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class IntakeCommand extends Command
{
  private double speed;

  public IntakeCommand(double speed)
  {
    requires(Robot.manipulator);
    this.speed = speed;
  }

  @Override
  protected void initialize()
  {
    Robot.manipulator.intake(speed);
  }

  @Override
  protected void execute()
  {
  }

  @Override
  protected boolean isFinished()
  {
    // False because we use the "whileHeld" method in OI which interupts the command
    return false;
  }

  @Override
  protected void end()
  {
  }

  @Override
  protected void interrupted()
  {
  }
}
