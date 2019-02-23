/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;

public class AutoTurretCommand extends PIDCommand
{
  private double target = 0;

  public AutoTurretCommand()
  {
    super(0.0025, 0.0, 0.0);

    requires(Robot.turret);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
    this.getPIDController().setOutputRange(-.3, .3);
    this.getPIDController().setSetpoint(target);
    this.getPIDController().setAbsoluteTolerance(1);
    this.getPIDController().enable();
    this.getPIDController().setToleranceBuffer(5);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {
    System.out.println("Running");
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished()
  {
    return false;//getPIDController().onTarget() || isTimedOut();
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
  }

  protected double returnPIDInput()
  {
    return Robot.byteVision.getDataIntake();
  }

  @Override
  protected void usePIDOutput(double output)
  {
    Robot.turret.setSpeed(-output);
  }
}
