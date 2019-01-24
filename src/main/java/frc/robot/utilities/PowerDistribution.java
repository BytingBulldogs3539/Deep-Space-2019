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
        //TODO: We should pass the pdp CAN id in so that we don't have to change this if we use this in another robot.
        pdp = new PowerDistributionPanel(RobotMap.pdp);
    }
    /**
	 * Allows us to get the current of the pdp slot specified.
	 * 
	 * @param pdpSlotNumber the slot that you would like to get the current from.
	 */
    public static double getCurrent(int pdpSlotNumber)
    {
        if (powerDistribution == null)
        {
            powerDistribution = new PowerDistribution();
        }

        return pdp.getCurrent(pdpSlotNumber);
    }
}