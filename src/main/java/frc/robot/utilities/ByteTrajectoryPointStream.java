/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

import java.util.HashMap;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;

public class ByteTrajectoryPointStream extends BufferedTrajectoryPointStream
{
    public HashMap<Integer, ByteTrajectoryPoint> state = new HashMap<Integer, ByteTrajectoryPoint>();

    public ErrorCode Write(ByteTrajectoryPoint point)
    {
        System.out.println("WRITE");
        if (!point.state.equals(""))
        {
            state.put((int) point.position, point);
            System.out.println("POINT ADDED");
        }
        return super.Write(point);
    }
}
