/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

import java.io.File;

import com.ctre.phoenix.motion.*;

import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class MotionProfiling
{
    public static BufferedTrajectoryPointStream initBuffer(String fileName)
    {
        String fullURL = "/home/lvuser/Motion_Profiles/" + fileName;
        double[][] profile = JsonParser.RetrieveProfileData(new File(fullURL));
        BufferedTrajectoryPointStream _bufferedStream = new BufferedTrajectoryPointStream();

        boolean forward = true; // set to false to drive in opposite direction of profile (not really needed
                                // since you can use negative numbers in profile).

        TrajectoryPoint point = new TrajectoryPoint(); // temp for for loop, since unused params are initialized
                                                       // automatically, you can alloc just one

        /* Insert every point into buffer, no limit on size */
        for (int i = 0; i < profile.length; ++i)
        {
            // This might need to be changed
            double direction = 1;
            double positionRot = profile[i][0];
            double velocityRPM = profile[i][1];
            double heading = profile[i][3];

            /* for each point, fill our structure and pass it to API */
            point.position = direction * positionRot * RobotMap.kSensorUnitsPerRotation * 2; // Convert
                                                                                             // Revolutions
                                                                                             // to Units
            point.velocity = direction * velocityRPM * RobotMap.kSensorUnitsPerRotation / 600.0; // Convert
                                                                                                 // RPM
                                                                                                 // to
                                                                                                 // Units/100ms
            point.auxiliaryPos = heading * 10; /* scaled such that 3600 => 360 deg */
            point.profileSlotSelect0 = RobotMap.kSlot_MotProf; /*
                                                                * which set of gains would you like to use [0,3]?
                                                                */
            point.profileSlotSelect1 = RobotMap.kSlot_Turning; /* auxiliary PID [0,1], leave zero */
            point.timeDur = (int) profile[i][2];
            point.zeroPos = false;
            if (i == 0)
                point.zeroPos = true; /* set this to true on the first point */

            point.isLastPoint = false;
            if ((i + 1) == profile.length)
                point.isLastPoint = true; /* set this to true on the last point */

            _bufferedStream.Write(point);
        }
        return _bufferedStream;
    }
}
