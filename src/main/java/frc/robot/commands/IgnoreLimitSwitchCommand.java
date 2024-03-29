/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class IgnoreLimitSwitchCommand extends InstantCommand
{
  public IgnoreLimitSwitchCommand()
  {
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
    RobotMap.useLimitSwitches = !RobotMap.useLimitSwitches;
    SmartDashboard.putBoolean("Use Limit Switches?", RobotMap.useLimitSwitches);
  }
}
