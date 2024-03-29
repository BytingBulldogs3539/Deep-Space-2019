/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ClimbMan extends Command
{
  public ClimbMan()
  {
    requires(Robot.verticate);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {
    // get the right stick y value and set to the motor speed maybe with a
    // multiplier.
  //  Robot.verticate.climb(Robot.oi.driver.getRightTrigger()- Robot.oi.driver.getLeftTrigger());
    Robot.verticate.climb(Robot.oi.driver.getLeftTrigger()-Robot.oi.driver.getRightTrigger());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished()
  {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end()
  {
    Robot.verticate.neutralOutput();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted()
  {
    end();
  }
}
