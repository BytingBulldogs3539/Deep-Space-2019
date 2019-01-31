package frc.robot.autoncommands;

import frc.robot.Robot;
import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class AutonTurnGyroCommand extends PIDCommand
{
	private double targetAngle;

	public AutonTurnGyroCommand(double setpointAngle, double seconds)
	{
		super(RobotMap.TurnPid.p, RobotMap.TurnPid.i, RobotMap.TurnPid.d);
		requires(Robot.drivetrain);
		this.targetAngle = setpointAngle;
		setTimeout(seconds);
	}

	protected void initialize()
	{

		this.getPIDController().setOutputRange(-1, 1);

		this.getPIDController().setSetpoint(targetAngle);

		this.getPIDController().setAbsoluteTolerance(1);

		this.getPIDController().enable();
		this.getPIDController().setToleranceBuffer(5);

	}

	protected void execute()
	{
	}

	protected boolean isFinished()
	{
		return getPIDController().onTarget() || isTimedOut();
	}

	protected void end()
	{
		this.getPIDController().reset();

		this.getPIDController().disable();
		Robot.drivetrain.neutralOutput();

	}

	protected void interrupted()
	{
		end();
	}

	@Override
	protected double returnPIDInput()
	{
		// return Robot.driveTrain.getHeading();
		return Robot.drivetrain.getPigeonHeading(); // try this
	}

	@Override
	protected void usePIDOutput(double output)
	{
		Robot.drivetrain.driveArcade(0, -output);
	}
}
