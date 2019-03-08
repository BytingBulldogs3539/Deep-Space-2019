/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TurretPositionCommandFin extends Command
{

  private double degrees = 0;
  private double leftOffset = 0;
  private double rightOffset = 0;

  public TurretPositionCommandFin(double degrees)
  {
    this.degrees = degrees;
    this.leftOffset = 0;
    this.rightOffset = 0;
    requires(Robot.turret);
  }

  public TurretPositionCommandFin(double degrees, double leftOffset, double rightOffset)
  {
    this.degrees = degrees;
    this.leftOffset = leftOffset;
    this.rightOffset = rightOffset;
    requires(Robot.turret);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
    if(Robot.turret.getAngle() < degrees)
    {
      Robot.turret.setPosition(degrees + rightOffset);
    }
    else
    {
      Robot.turret.setPosition(degrees + leftOffset);
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished()
  {
    return Robot.turret.finished();
  }

  // Called once after isFinished returns true
  @Override
  protected void end()
  {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted()
  {
  }
}
