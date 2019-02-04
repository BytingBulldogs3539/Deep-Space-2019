package frc.robot.motionprofiling;

import frc.robot.utilities.Gains;;

public class Constants
{
	/**
	 * Motor neutral dead-band, set to the minimum 0.1%.
	 */
	public final static double neutralDeadband = 0.001;

	/**
	 * Pigeon will reports 8192 units per 360 deg (1 rotation) If using
	 * encoder-derived (left plus/minus right) heading, find this emperically.
	 */
	public final static double turnUnitsPerDeg = 8192.0 / 360.0;

	/**
	 * PID Gains may have to be adjusted based on the responsiveness of control loop
	 * kP kI kD kF Iz PeakOut
	 */
	// TODO: Tune for robot.
	public final static Gains gains_MotProf = new Gains(.2, 0.0, 0.0, 1023.0 / 7000.0, 400, 1.00);
	public final static Gains gains_MotProfAngle = new Gains(.2, 0.0, 0.0, 1023.0 / 7000.0, 400, 1.00);

	public final static int primaryPIDSlot = 0; // any slot [0,3]
	public final static int auxPIDSlot = 1; // any slot [0,3]
}