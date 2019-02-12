/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.motionprofiling;

import java.io.File;
import java.util.Iterator;

import com.ctre.phoenix.motion.TrajectoryPoint;

import frc.robot.utilities.*;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionProfiling
{
    public static ByteTrajectoryPointStream initBuffer(String fileName)
    {
        String fullURL = "/home/lvuser/Motion_Profiles/" + fileName;
        Iterator<BytePoint> profile = JsonParser.RetrieveProfileData(new File(fullURL)).iterator();
        ByteTrajectoryPointStream _bufferedStream = new ByteTrajectoryPointStream();

        boolean forward = true; // set to false to drive in opposite direction of profile (not really needed
                                // since you can use negative numbers in profile).

        ByteTrajectoryPoint point = new ByteTrajectoryPoint(); // temp for for loop, since unused params are initialized
        // automatically, you can alloc just one


        /* Insert every point into buffer, no limit on size */

        while (profile.hasNext())
        {
            BytePoint bytePoint = profile.next();

            double direction = forward ? +1 : -1;
            /* use the generated profile to figure out the forward arc path (translation) */
            double positionRot = bytePoint.rotation;
            double velocityRPM = bytePoint.velocity;
            int durationMilliseconds = (int) bytePoint.time;

            double targetTurnDeg;
            /* to get the turn target; */

            boolean turnTune = true;
            if (turnTune)
            {
                targetTurnDeg = bytePoint.angle;
            }
            else
            {
                // @SuppressWarnings
                targetTurnDeg = 360 * (positionRot / 9.45958545092102);
            }

            /* for each point, fill our structure and pass it to API */
            point.timeDur = (int) durationMilliseconds;
            /* drive part */
            point.position = direction * positionRot * RobotMap.sensorUnitsPerRotDriveTrain; // Rotations => sensor units
            point.velocity = direction * velocityRPM * RobotMap.sensorUnitsPerRotDriveTrain / 600.0 *.7; // RPM => units per 100ms
            point.arbFeedFwd = 0; // good place for kS, kV, kA, etc...

            /* turn part */
            point.auxiliaryPos = (int) targetTurnDeg * RobotMap.turnUnitsPerDeg; // Convert deg to remote sensor units

            point.auxiliaryVel = 0; // advanced teams can also provide the target velocity
            point.auxiliaryArbFeedFwd = 0; // good place for kS, kV, kA, etc...

            point.profileSlotSelect0 = RobotMap.primaryPIDSlot; /* which set of gains would you like to use [0,3]? */
            point.profileSlotSelect1 = RobotMap.auxPIDSlot; /* auxiliary PID [0,1], leave zero */
            point.zeroPos = false; /* don't reset sensor, this is done elsewhere since we have multiple sensors */
            point.isLastPoint = !profile.hasNext(); /* set this to true on the last point */

            point.useAuxPID = true; /* tell MPB that we are using both pids */

            point.state = bytePoint.state;

            _bufferedStream.Write(point);
            _bufferedStream.AddState(point);
        }
        return _bufferedStream;
    }

    public static ByteTrajectoryPointStream initBuffer(double[][] profile, int totalCnt, double finalTurnDeg)
    {
        ByteTrajectoryPointStream _bufferedStream = new ByteTrajectoryPointStream();

        boolean forward = true; // set to false to drive in opposite direction of profile (not really needed
                                // since you can use negative numbers in profile).

        TrajectoryPoint point = new TrajectoryPoint(); // temp for for loop, since unused params are initialized
                                                       // automatically, you can alloc just one

        /* Insert every point into buffer, no limit on size */
        for (int i = 0; i < totalCnt; ++i)
        {

            double direction = forward ? +1 : -1;
            /* use the generated profile to figure out the forward arc path (translation) */
            double positionRot = profile[i][0];
            double velocityRPM = profile[i][1];
            int durationMilliseconds = (int) profile[i][2];

            /* to get the turn target, lets just scale from 0 deg to caller's final deg
             * linearizly */
            double targetTurnDeg = finalTurnDeg * (i + 1) / totalCnt;

            /* for each point, fill our structure and pass it to API */
            point.timeDur = durationMilliseconds;

            /* drive part */
            point.position = direction * positionRot * RobotMap.sensorUnitsPerRotDriveTrain; // Rotations => sensor units
            point.velocity = direction * velocityRPM * RobotMap.sensorUnitsPerRotDriveTrain / 600.0*.666; // RPM => units per 100ms
            point.arbFeedFwd = 0; // good place for kS, kV, kA, etc...

            /* turn part */
            point.auxiliaryPos = targetTurnDeg * RobotMap.turnUnitsPerDeg; // Convert deg to remote sensor units
            point.auxiliaryVel = 0; // advanced teams can also provide the target velocity
            point.auxiliaryArbFeedFwd = 0; // good place for kS, kV, kA, etc...

            point.profileSlotSelect0 = RobotMap.primaryPIDSlot; /* which set of gains would you like to use [0,3]? */
            point.profileSlotSelect1 = RobotMap.auxPIDSlot; /* auxiliary PID [0,1], leave zero */
            point.zeroPos = false; /* don't reset sensor, this is done elsewhere since we have multiple sensors */
            point.isLastPoint = ((i + 1) == totalCnt); /* set this to true on the last point */
            point.useAuxPID = true; /* tell MPB that we are using both pids */

            _bufferedStream.Write(point);
        }
        return _bufferedStream;
    }
}
