/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.GamePieceType;

/**
 * Add your docs here.
 */
public class LimitSwitchCommand extends Command
{
  /**
   * Run this command to update the status of all of the lights
   */

  boolean lastPanelState;
  boolean lastCargoState;

  public LimitSwitchCommand()
  {
    requires(Robot.rioDuino);
  }

  @Override
  protected void initialize()
  {

  }

  @Override
  protected void execute()
  {
    if (Robot.oi.operator.buttonX.get())
    {
      if (Robot.elevator.gamePieceType == GamePieceType.NONE)
      {
        Robot.elevator.gamePieceType = GamePieceType.HATCH;
      }
      else if (Robot.elevator.gamePieceType == GamePieceType.CARGO)
      {
        Robot.elevator.gamePieceType = GamePieceType.HATCH;
      }
      else if (Robot.elevator.gamePieceType == GamePieceType.HATCH)
      {
        Robot.elevator.gamePieceType = GamePieceType.CARGO;
      }
    }
    else if (Robot.rioDuino.cargoLimitSwitch.get() != lastCargoState || Robot.rioDuino.panelLimitSwitch.get() != lastPanelState)
    {
      if (Robot.rioDuino.cargoLimitSwitch.get() && Robot.rioDuino.panelLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.BOTH;
      }
      else if (!Robot.rioDuino.cargoLimitSwitch.get() && !Robot.rioDuino.panelLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.NONE;
      }
      else if (Robot.rioDuino.cargoLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.CARGO;
      }
      else if (Robot.rioDuino.panelLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.HATCH;
      }
    }
  }

  @Override
  protected boolean isFinished()
  {
    return false;
  }

  @Override
  protected void end()
  {

  }

  @Override
  protected void interrupted()
  {
    end();
  }
}
