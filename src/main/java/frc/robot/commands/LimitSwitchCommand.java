/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Elevator.GamePieceType;
import frc.robot.subsystems.RioDuino.Mode;

/**
 * Add your docs here.
 */
public class LimitSwitchCommand extends InstantCommand
{
  /**
   * Run this command to update the status of all of the lights
   */
  public LimitSwitchCommand()
  {
    super();
  }

  // Called once when the command executes
  @Override
  protected void initialize()
  {
    if (RobotMap.useLimitSwitches)
    {
      if (Robot.oi.cargoLimitSwitch.get() && Robot.oi.panelLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.BOTH;
        Robot.rioDuino.updateMode(Mode.RED);
        // TODO: add button override
      }
      else if (Robot.oi.cargoLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.CARGO;
        Robot.rioDuino.updateMode(Mode.ORANGE);
      }
      else if (Robot.oi.panelLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.HATCH;
        Robot.rioDuino.updateMode(Mode.YELLOW);
      }
      else
      {
        Robot.elevator.gamePieceType = GamePieceType.NONE;
        Robot.rioDuino.updateMode(Mode.GREEN);
      }
    }
  }

}
