/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveCommand extends Command
{
  public DriveCommand()
  {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
    //We don't need an initialize because we did all the initializing on robot start.
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {
    Robot.drivetrain.driveArcade(Robot.oi.driver.getLeftStickY(), Robot.oi.driver.getRightStickX());
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
    //If our drivetrain gets canceled or ended even though it should not we need to stop our drivetrain so we don't keep driving.
    Robot.drivetrain.neutralOutput();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted()
  {
    end();
  }
}
