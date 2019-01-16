/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.utilities.*;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
  // List of Motor Controller numbers
  public static int FRTalon = 4;
  public static int FLTalon = 9;
  public static int MRTalon = 2;
  public static int MLTalon = 10;
  public static int BRTalon = 1;
  public static int BLTalon = 18;

  // Amount of ticks in a mag encoder
  public final static int kSensorUnitsPerRot = 4096;

  /**
   * Motor neutral dead-band, set to the minimum 0.1%.
   */
  public final static double kNeutralDeadband = 0.001;

  /**
   * Pigeon will reports 8192 units per 360 deg (1 rotation) If using
   * encoder-derived (left plus/minus right) heading, find this emperically.
   */
  public final static double kTurnUnitsPerDeg = 8192.0 / 360.0;

  /**
   * PID Gains may have to be adjusted based on the responsiveness of control loop
   * kP kI kD kF Iz PeakOut
   */
  public final static Gains kGains_MotProf = new Gains(1.0, 0.0, 0.0, 1023.0 / 6800.0, 400,
      1.00); /* measured 6800 velocity units at full motor output */

  public final static int kPrimaryPIDSlot = 0; // any slot [0,3]
  public final static int kAuxPIDSlot = 1; // any slot [0,3]
  /*
   * Chassis Bot public static int fr = 4; public static int mr = 2; public static
   * int br = 1;
   * 
   * public static int fl = 9; public static int ml = 10; public static int bl =
   * 18;
   * 
   */

}
