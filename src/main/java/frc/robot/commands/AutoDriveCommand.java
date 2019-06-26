/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;

public class AutoDriveCommand extends PIDCommand
{
  private double target = 0;
  private double vision = 0;

  public AutoDriveCommand()
  {
    super(.7, 0.0, 0.0);
    requires(Robot.turret);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
    this.getPIDController().reset();

    this.getPIDController().setOutputRange(-.5, .5);
    this.getPIDController().setSetpoint(target);
    this.getPIDController().enable();
    System.out.println("init run");

  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {
    
    System.out.println("Running"+ vision);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished()
  {
    return false;//!Robot.oi.driver.buttonBL.get();// getPIDController().onTarget() || isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end()
  {
    this.getPIDController().reset();
    this.getPIDController().disable();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted()
  {
    end();
  }

  protected double returnPIDInput()
  {
    vision = Robot.byteVision.getDataIntake();
    return vision;
  }

  @Override
  protected void usePIDOutput(double output)
  {
    double speed = (Math.atan(Robot.oi.driver.getLeftStickY()) / Math.atan(1));

   // Robot.drivetrain.driveArcade(speed*.2, -output);
    Robot.turret.setSpeed(-output);

    System.out.println("output" + output);
  }
}