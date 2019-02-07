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

	public PlotThread(TalonSRX talon)
	{
		_talon = talon;

		_thread = new Thread(this);
		_thread.start();
	}

	public void run()
	{
		/**
		 * Speed up network tables, this is a test project so eat up all of the network
		 * possible for the purpose of this test.
		 */

		while (true)
		{
			/* Yield for a Ms or so - this is not meant to be accurate */
			try
			{
				Thread.sleep(5);
			}
			catch (Exception e)
			{
				/* Do Nothing */
			}

			/* Grab the latest signal update from our 1ms frame update */
			double sen_pos = _talon.getSelectedSensorPosition(0);
			double sen_vel = _talon.getSelectedSensorVelocity(0);
			double trgt_pos = _talon.getActiveTrajectoryPosition(0);
			double trgt_vel = _talon.getActiveTrajectoryVelocity(0);
			double trgt_arbF = _talon.getActiveTrajectoryArbFeedFwd(0);
			double heading = _talon.getSelectedSensorPosition(1);
			double trgt_heading = _talon.getActiveTrajectoryPosition(1);


			SmartDashboard.putNumber("sen_pos", sen_pos);
			SmartDashboard.setPersistent("sen_pos");
			SmartDashboard.putNumber("sen_vel", sen_vel);
			SmartDashboard.setPersistent("trgt_vel");
			SmartDashboard.putNumber("trgt_pos", trgt_pos);
			SmartDashboard.setPersistent("trgt_pos");
			SmartDashboard.putNumber("trgt_vel", trgt_vel);
			SmartDashboard.setPersistent("trgt_vel");
			SmartDashboard.putNumber("trgt_arbF", trgt_arbF);
			SmartDashboard.setPersistent("trgt_arbF");
			SmartDashboard.putNumber("trgt heading", trgt_heading);
			SmartDashboard.setPersistent("trgt heading");
			SmartDashboard.putNumber("heading", heading);
			SmartDashboard.setPersistent("heading");
			// SmartDashboard.putNumber("test", _talon.);

		}
	}
}