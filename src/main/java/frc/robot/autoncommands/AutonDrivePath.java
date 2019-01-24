/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autoncommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.motionprofiling.*;

public class AutonDrivePath extends Command
{
  String fileName;
  String fullURL;
  boolean useIsFinished;

  public AutonDrivePath(String fileName, boolean useIsFinished)
  {
    requires(Robot.drivetrain);
    this.fileName = fileName;
    this.useIsFinished = useIsFinished;

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
    Robot.drivetrain.zeroEncoders();
    //TODO: We need to change this so that we can load the file and its points before we try to start motion profiling for a faster start.
    Robot.drivetrain.startMotionProfile(MotionProfiling.initBuffer(fileName));
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
    if(useIsFinished)
      return Robot.drivetrain.isMotionProfileFinished();
    else
      return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end()
  {
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
