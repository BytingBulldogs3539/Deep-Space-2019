/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.ArrayList;
import java.util.HashMap;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * A Command group required for using motion profiles in auton.
 */
public abstract class MotionCommandGroup extends CommandGroup
{
    public ArrayList<String> motionProfileList = new ArrayList<String>();

    private Thread eventThread;

    public void addMotionProfile(String fileName)
    {
        motionProfileList.add(fileName);
    }

    public void setOnEvent(String fileName, String eventName, final Command command, TalonSRX _talon)
    {
        if (eventThread == null)
        {
            System.out.println("Add thread");
            eventThread = new Thread(() ->
            {
                while (!Thread.interrupted())
                {
                    try
                    {
                        Thread.sleep(5);
                    }
                    catch (Exception e)
                    {

                    }
                    if (Robot.MotionBuffers.get("fileName").state.containsKey(_talon.getActiveTrajectoryPosition()))
                    {
                        ByteTrajectoryPoint point = Robot.MotionBuffers.get("fileName").state.get(_talon.getActiveTrajectoryPosition());

                        if (_talon.getActiveTrajectoryVelocity() == point.velocity && _talon.getActiveTrajectoryPosition(1) == point.headingDeg)
                        {
                            if (eventName.equals(point.state))
                                command.start();
                        }
                    }

                }
            });
        }
    }
}
