/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

/**
 * Organizes gains used when assigning values to slots
 */
public class Gains
{
    public double kP;
    public double kI;
    public double kD;
    public double kF;
    public double kIzone;
    public double kPeakOutput;

    public Gains(double kP, double kI, double kD, double kF, double kIzone, double kPeakOutput)
    {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        this.kIzone = kIzone;
        this.kPeakOutput = kPeakOutput;
    }
}