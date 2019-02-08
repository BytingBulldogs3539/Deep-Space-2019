package frc.robot.motionprofiling;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Quick and dirty threaded plotter class
 */
public class PlotThread implements Runnable
{
	private TalonSRX _talon;
	private Thread _thread;

	double sen_pos, sen_vel, trgt_pos, trgt_vel, trgt_arbF, heading, trgt_heading;

	double pos_error, pos_error_accum, vel_error, vel_error_accum;

	boolean isNetworking = false;

	public PlotThread(TalonSRX talon)
	{
		_talon = talon;

		_thread = new Thread(this);
		_thread.start();

		// Must be initialized for later
		pos_error_accum = 0;
		vel_error_accum = 0;

		SmartDashboard.setPersistent("sen_pos");
		SmartDashboard.setPersistent("trgt_pos");
		SmartDashboard.setPersistent("trgt_vel");
		SmartDashboard.setPersistent("trgt_vel");
		SmartDashboard.setPersistent("trgt_arbF");

		SmartDashboard.setPersistent("trgt heading");
		SmartDashboard.setPersistent("heading");

		SmartDashboard.setPersistent("pos_error");
		SmartDashboard.setPersistent("vel_error");
		SmartDashboard.setPersistent("pos_error_accum");
		SmartDashboard.setPersistent("vel_error_accum");
	}

	public void run()
	{
		/**
		 * Speed up network tables, this is a test project so eat up all of the network
		 * possible for the purpose of this test.
		 */

		while (true)
		{
			if (isNetworking)
			{
				/* Yield for 5 Ms or so - this is not meant to be accurate */
				try
				{
					Thread.sleep(5);
				}
				catch (Exception e)
				{
					/* Do Nothing */
				}

				/* Grab the latest signal update from our 1ms frame update */
				sen_pos = _talon.getSelectedSensorPosition(0);
				sen_vel = _talon.getSelectedSensorVelocity(0);
				trgt_pos = _talon.getActiveTrajectoryPosition(0);
				trgt_vel = _talon.getActiveTrajectoryVelocity(0);
				trgt_arbF = _talon.getActiveTrajectoryArbFeedFwd(0);
				heading = _talon.getSelectedSensorPosition(1);
				trgt_heading = _talon.getActiveTrajectoryPosition(1);

				/* Calculate error */

				pos_error = Math.abs(sen_pos - trgt_pos);
				pos_error_accum = pos_error_accum + pos_error;

				vel_error = Math.abs(sen_vel - trgt_vel);
				vel_error_accum = vel_error_accum + vel_error;

				SmartDashboard.putNumber("sen_pos", sen_pos);
				SmartDashboard.putNumber("sen_vel", sen_vel);
				SmartDashboard.putNumber("trgt_pos", trgt_pos);
				SmartDashboard.putNumber("trgt_vel", trgt_vel);
				SmartDashboard.putNumber("trgt_arbF", trgt_arbF);
				SmartDashboard.putNumber("trgt heading", trgt_heading);
				SmartDashboard.putNumber("heading", heading);

				SmartDashboard.putNumber("pos_error", pos_error);
				SmartDashboard.putNumber("vel_error", vel_error);
				SmartDashboard.putNumber("pos_error_accum", pos_error_accum);
				SmartDashboard.putNumber("vel_error_accum", vel_error_accum);

				// SmartDashboard.putNumber("test", _talon.);
			}
		}
	}

	/** Starts accumulating */
	public void startThreading()
	{
		isNetworking = true;
	}

	public void stopThreading()
	{
		isNetworking = false;
	}

	public void resetAccum()
	{
		pos_error_accum = 0;
		vel_error_accum = 0;
	}
}