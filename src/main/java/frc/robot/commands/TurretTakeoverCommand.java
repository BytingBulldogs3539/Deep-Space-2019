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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TurretTakeoverCommand extends Command
{
  public TurretTakeoverCommand()
  {
    requires(Robot.turret);
  }

  // Called just before this Command runs th
  @Override
  protected void initialize()
  {
    SmartDashboard.putBoolean("Is TurretTakeover", true);

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {

    // get the left stick x value and set to the motor speed maybe with a
    // multiplier.

    //if (Robot.oi.operator.)

    Robot.turret.setSpeed(Math.tan(Robot.oi.operator.getRightStickX()) / Math.tan(1) * RobotMap.turretSpeedMultipier);
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
    Robot.turret.neutralOutput();
    SmartDashboard.putBoolean("Is TurretTakeover", false);

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted()
  {
    end();
  }
}
