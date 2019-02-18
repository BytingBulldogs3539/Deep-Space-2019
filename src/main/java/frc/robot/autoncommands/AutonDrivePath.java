/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autoncommands;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AutonDrivePath extends Command
{
  String fileName;
  String fullURL;
  boolean useIsFinished;
  BufferedTrajectoryPointStream buffer;

  /**
   * Allows us to pass a file name and the motors will drive it. useIsFinished
   * must be true inorder for events to work!
   */
  public AutonDrivePath(String fileName, boolean useIsFinished)
  {
    requires(Robot.drivetrain);
    this.fileName = fileName;
    this.useIsFinished = useIsFinished;
    buffer = Robot.MotionBuffers.get(fileName);
  }

  public AutonDrivePath(BufferedTrajectoryPointStream buffer, boolean useIsFinished)
  {
    requires(Robot.drivetrain);
    this.useIsFinished = useIsFinished;
    this.buffer = buffer;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
    Robot.drivetrain.zeroEncoders();
   // Robot.drivetrain.plotThread.startThreading();
    Robot.drivetrain.startMotionProfile(buffer);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {
    Robot.drivetrain.putEncoder();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished()
  {
    if (useIsFinished)
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
