/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

/**
 * Add your docs here.
 */
public class Tolerant {

    public static boolean withinTolerance(int number,int target, int tolerance){
        if(number <= (target+tolerance)&& number >= target-tolerance){
          return true;
    
        }else{
          return false;
        }
}
}
