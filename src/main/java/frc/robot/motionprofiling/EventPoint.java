/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.motionprofiling;

/**
 * Add your docs here.
 */
public class EventPoint
{
    public int position = 0; // !< The position to servo to.
    public int velocity = 0; // !< The velocity to feed-forward.
    public int headingDeg = 0; // !< Not used. Use auxiliaryPos instead. @see auxiliaryPos
    public String state = "";
    public Boolean ran = false;

}
