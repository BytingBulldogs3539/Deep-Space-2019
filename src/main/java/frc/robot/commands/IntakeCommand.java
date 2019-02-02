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
  public IntakeCommand()
  {
    requires(Robot.manipulator);
  }

  @Override
  protected void initialize()
  {
    Robot.manipulator.intake(Robot.oi.operator.getRawAxis(Robot.oi.operator.LEFT_TRIGGER) + Robot.oi.operator.getRawAxis(Robot.oi.operator.RIGHT_TRIGGER));
  }

  @Override
  protected void execute()
  {
  }

  @Override
  protected boolean isFinished()
  {
    // False because we rely on other methods to interupt
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
