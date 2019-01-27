/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.ArrayList;


/**
 * A Command group required for using motion profiles in auton.
 */
public abstract class MotionCommandGroup extends CommandGroup 
{
    public ArrayList<String> motionProfileList = new ArrayList<String>();

    public void addMotionProfile(String fileName)
    {
        motionProfileList.add(fileName);
    }
}
