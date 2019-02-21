/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ClimbCommandOld extends Command
{
  private double speed;

  public ClimbCommandOld(double speed)
  {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.verticate);
    this.speed = speed;
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
    if(!Robot.oi.driver.buttonX.get()){
    System.out.println("CLimb--------------------");
    if(Robot.oi.driver.buttonA.get()){
    Robot.verticate.climbArm(-speed);
    }else{
      Robot.verticate.climbArm(0);
    }
    if(Robot.oi.driver.buttonB.get()){
      Robot.verticate.climb(speed);
    }
    else{
      Robot.verticate.climb(0);
    }
    if(Robot.oi.driver.buttonBL.get()){
      Robot.verticate.wheel(speed);
    }else{
      Robot.verticate.wheel(0);
    }
  }else{
    System.out.println("CLimb--------------------");
    if(Robot.oi.driver.buttonA.get()){
    Robot.verticate.climbArm(speed);
    }else{
      Robot.verticate.climbArm(0);
    }
    if(Robot.oi.driver.buttonB.get()){
      Robot.verticate.climb(-speed);
    }
    else{
      Robot.verticate.climb(0);
    }
    if(Robot.oi.driver.buttonBL.get()){
      Robot.verticate.wheel(-speed);
    }else{
      Robot.verticate.wheel(0);
    }

  }
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
    Robot.verticate.climb(0);
    Robot.verticate.climbArm(0);

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted()
  {
  }
}
