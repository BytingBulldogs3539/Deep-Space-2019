package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.*;
import frc.robot.subsystems.Elevator.ElevatorHeight;
import frc.robot.subsystems.Elevator.GamePieceType;

public class ElevatorPositionCommand extends Command
{
  private ElevatorHeight height;
  private boolean useIsFinished;

  public ElevatorPositionCommand(ElevatorHeight height, boolean useIsFinished)
  {
    requires(Robot.elevator);
    this.height = height;
    this.useIsFinished = useIsFinished;
  }

  @Override
  protected void initialize()
  {
    System.out.println("UP");
    // TODO: TEST!
    if (Robot.elevator.gamePieceType == GamePieceType.CARGO)
    {
      Robot.elevator.setHeightCargo(height);
    }
    if (Robot.elevator.gamePieceType == GamePieceType.HATCH)
    {
      Robot.elevator.setHeightHatch(height);
    }
    if (this.height == ElevatorHeight.Home)
    {
      Robot.elevator.setHeightCargo(height);

    }

  }

  @Override
  protected void execute()
  {
  }

  @Override
  protected boolean isFinished()
  {
    if (useIsFinished)
      return Robot.elevator.finished();
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