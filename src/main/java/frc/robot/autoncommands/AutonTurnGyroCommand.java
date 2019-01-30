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
		Robot.drivetrain.pigeon.setFusedHeading(0, 10);
		Robot.drivetrain.pigeon.setFusedHeading(0, 10);


	}

	protected void initialize()
	{
	//	Robot.driveTrain._imu.setFusedHeading(0, 10);

		// I don't think this line is needed because the constructor already does this
		// getPIDController().setPID(RobotMap.turnPeaGyro, RobotMap.turnEyeGyro,
		// RobotMap.turnDeeGyro);
		this.getPIDController().setOutputRange(-1, 1);
	//	this.getPIDController().setOutputRange(1, -1);

		this.getPIDController().setSetpoint(targetAngle);

		this.getPIDController().setAbsoluteTolerance(1);


		this.getPIDController().enable();
		this.getPIDController().setToleranceBuffer(5);
		//this.getPIDController().

	}

	protected void execute()
	{
		//System.out.println(Robot.drivetrain.getPigeonHeading());
	}

	protected boolean isFinished()
	{
		return getPIDController().onTarget() || isTimedOut();
	}

	protected void end()
	{
		this.getPIDController().reset();

		this.getPIDController().disable();
		Robot.drivetrain.pigeon.setFusedHeading(0, 50);
		Robot.drivetrain.driveArcade(0, 0);
		Robot.drivetrain.pigeon.setFusedHeading(0, 50);

	}

	protected void interrupted()
	{
		end();
	}

	@Override
	protected double returnPIDInput()
	{
	//return Robot.driveTrain.getHeading();
		return Robot.drivetrain.getPigeonHeading(); //try this 
	}

	@Override
	protected void usePIDOutput(double output)
	{
		Robot.drivetrain.driveArcade(0, -output);
	}
}
