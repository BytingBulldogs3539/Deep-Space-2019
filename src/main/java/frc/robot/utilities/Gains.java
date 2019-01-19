/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

/**
 * Encapsulates PIDF and related variables
 */
public class Gains
{
    public double p;
    public double i;
    public double d;
    public double f;
    public double iZone;
    public double peakOutput;

    public Gains(double p, double i, double d, double f, double iZone, double peakOutput)
    {
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;
        this.iZone = iZone;
        this.peakOutput = peakOutput;
    }
}