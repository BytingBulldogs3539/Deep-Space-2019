package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

/**
 * Add your docs here.
 */
public class ElevatorSetpointCommand extends InstantCommand
{
  double inches = 0;

  public ElevatorSetpointCommand()
  {
    super();
    requires(Robot.elevator);
    this.inches = 0;
  }

  public ElevatorSetpointCommand(double inches)
  {
    super();
    requires(Robot.elevator);
    this.inches = inches;
    System.out.println("set inches to " + inches);
  }

  // Called once when the command executes
  @Override
  protected void initialize()
  {
    Robot.elevator.setHeightInches(inches);
  }

}
