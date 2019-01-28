package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.subsystems.Elevator.ElevatorHeight;
import frc.robot.subsystems.Elevator.GamePieceType;

public class ElevatorPositionCommand extends Command
{
  private ElevatorHeight height;

  public ElevatorPositionCommand(ElevatorHeight height)
  {
    requires(Robot.elevator);
    this.height = height;
  }

  @Override
  protected void initialize()
  {
    //TODO: TEST!
    if(Robot.elevator.gamePieceType == GamePieceType.CARGO)
    {
      Robot.elevator.setHeightCargo(height);
    }
    if(Robot.elevator.gamePieceType == GamePieceType.HATCH)
    {
      Robot.elevator.setHeightHatch(height);
    }
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