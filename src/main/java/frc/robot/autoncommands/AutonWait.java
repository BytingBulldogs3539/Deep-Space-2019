/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autoncommands;

import edu.wpi.first.wpilibj.command.Command;


import edu.wpi.first.wpilibj.command.Command;

public class AutonWait extends Command
{
	double seconds;

	public AutonWait(double seconds)
	{
		this.seconds = seconds;
	}

	protected void initialize()
	{
		this.setTimeout(seconds);
	}

	protected void execute()
	{
	}

	protected boolean isFinished()
	{
		return isTimedOut();
	}

	protected void end()
	{
	}

	protected void interrupted()
	{
		end();
	}
}
