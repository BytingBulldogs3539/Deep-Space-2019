/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 Byting Bulldogs. All Rights Reserved.                   */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import frc.robot.RobotMap;

/**
 * Singleton wrapper for power distribution panel to allow access from anywhere
 * in the robot
 */
public class PowerDistribution
{
    private static PowerDistributionPanel pdp;
    private static PowerDistribution powerDistribution;

    private PowerDistribution()
    {
        pdp = new PowerDistributionPanel(RobotMap.pdp);
    }

    public static double getCurrent(int pdpSlotNumber)
    {
        if (powerDistribution == null)
        {
            powerDistribution = new PowerDistribution();
        }

        return pdp.getCurrent(pdpSlotNumber);
    }
}