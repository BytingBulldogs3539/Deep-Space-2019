/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.motionprofiling;

import java.io.File;

import com.ctre.phoenix.motion.*;

import frc.robot.utilities.*;

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

        /* clear the buffer, in case it was used elsewhere */
        _bufferedStream.Clear();

        /* Insert every point into buffer, no limit on size */
        for (int i = 0; i < profile.length; ++i)
        {

            double direction = forward ? +1 : -1;
            /* use the generated profile to figure out the forward arc path (translation) */
            double positionRot = profile[i][0];
            double velocityRPM = profile[i][1];
            int durationMilliseconds = (int) profile[i][2];

            /* to get the turn target, lets just scale from 0 deg to caller's final deg
             * linearizly */
            double targetTurnDeg = profile[i][3];

            /* for each point, fill our structure and pass it to API */
            point.timeDur = durationMilliseconds;

            /* drive part */
            point.position = direction * positionRot * Constants.kSensorUnitsPerRot; // Rotations => sensor units
            point.velocity = direction * velocityRPM * Constants.kSensorUnitsPerRot / 600.0; // RPM => units per 100ms
            point.arbFeedFwd = 0; // good place for kS, kV, kA, etc...

            /* turn part */
            point.auxiliaryPos = targetTurnDeg * Constants.kTurnUnitsPerDeg; // Convert deg to remote sensor units
            point.auxiliaryVel = 0; // advanced teams can also provide the target velocity
            point.auxiliaryArbFeedFwd = 0; // good place for kS, kV, kA, etc...

            point.profileSlotSelect0 = Constants.kPrimaryPIDSlot; /* which set of gains would you like to use [0,3]? */
            point.profileSlotSelect1 = Constants.kAuxPIDSlot; /* auxiliary PID [0,1], leave zero */
            point.zeroPos = false; /* don't reset sensor, this is done elsewhere since we have multiple sensors */
            point.isLastPoint = ((i + 1) == profile.length); /* set this to true on the last point */
            point.useAuxPID = true; /* tell MPB that we are using both pids */

            _bufferedStream.Write(point);
        }
        return _bufferedStream;
    }
}
