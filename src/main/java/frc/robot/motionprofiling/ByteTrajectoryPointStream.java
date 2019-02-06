/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.motionprofiling;

import java.util.HashMap;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;

public class ByteTrajectoryPointStream extends BufferedTrajectoryPointStream
{
    public HashMap<Integer, EventPoint> state = new HashMap<Integer, EventPoint>();

    public ErrorCode Write(ByteTrajectoryPoint point)
    {
        return super.Write(point);
    }

    public void AddState(ByteTrajectoryPoint point)
    {
        if (!point.state.equals(""))
        {
            EventPoint point2 = new EventPoint();
            point2.velocity = (int) point.velocity;
            point2.position = (int) point.position;
            point2.headingDeg = (int) point.auxiliaryPos;
            point2.state = point.state;

            state.put((int) point.position, point2);
        }
    }
}
