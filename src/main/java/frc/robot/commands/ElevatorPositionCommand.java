package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;

public class ElevatorPositionCommand extends Command
{
  private double inches;

  public ElevatorPositionCommand(double inches)
  {
    requires(Robot.elevator);
    this.inches = inches;
  }

  @Override
  protected void initialize()
  {
    Robot.elevator.setHeightInches(inches);
  }

  @Override
  protected void execute()
  {
  }

  @Override
  protected boolean isFinished()
  {
    return true;
  }

  @Override
  protected void end()
  {
  }

  @Override
  protected void interrupted()
  {
  }
}