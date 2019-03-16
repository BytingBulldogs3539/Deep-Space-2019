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
    // We don't need an initialize because we did all the initializing on robot
    // start.
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {
    //double triggers = Robot.oi.driver.getRightTrigger()- Robot.oi.driver.getLeftTrigger();
    double speed = (Math.atan(Robot.oi.driver.getLeftStickY()) / Math.atan(1));
    //double speed = Robot.oi.driver.getRightTrigger()- Robot.oi.driver.getLeftTrigger();;

    double turn = (Math.tan(Robot.oi.driver.getRightStickX()) / Math.tan(1));
   // double turn = (Math.tan(Robot.oi.driver.getLeftStickX()) / Math.tan(1));

    
    if(Robot.oi.driver.buttonBL.get())
    {
      double vision = Robot.byteVision.getDataIntake()*1.5;
      double turnspeed = turn;
      double modifiedTurnSpeed=0;
      double danModifieder = .6;
      double visionModifieder = .4;

      if (vision>1)
        vision=1;

      if (vision<-1)
        vision=-1;

      modifiedTurnSpeed =  (turnspeed * danModifieder) + (vision * visionModifieder);
      System.out.println(vision);
      
      Robot.drivetrain.driveArcade(speed*.5,modifiedTurnSpeed);
    }
    else if (!Robot.oi.driver.buttonBR.get())//buttonBR
    {
      //Robot.drivetrain.driveArcade(( (Robot.oi.driver.getRightTrigger()- Robot.oi.driver.getLeftTrigger())*.5),  turn* .6);
      Robot.drivetrain.driveArcade(speed*.55,turn*.4);
      Robot.drivetrain.turboCurrent(20, 30);

    }
    else
    {
    //Robot.drivetrain.driveArcade((Robot.oi.driver.getRightTrigger()- Robot.oi.driver.getLeftTrigger()), turn*.6);
      Robot.drivetrain.driveArcade(speed, turn*.75);
      Robot.drivetrain.turboCurrent(50, 39);
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
    // If our drivetrain gets canceled or ended even though it should not we need to
    // stop our drivetrain so we don't keep driving.
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
