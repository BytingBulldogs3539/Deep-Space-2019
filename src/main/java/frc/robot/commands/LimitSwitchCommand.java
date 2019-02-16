/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    System.out.println("limit pressed");
    if (RobotMap.useLimitSwitches)
    {
      if (Robot.oi.cargoLimitSwitch.get() && Robot.oi.panelLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.BOTH;
        SmartDashboard.putBoolean("Is Cargo Mode", false);
        Robot.rioDuino.updateMode(Mode.RED);
        System.out.println("RED");
      }
      else if (Robot.oi.cargoLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.CARGO;
        SmartDashboard.putBoolean("Is Cargo Mode", true);

        Robot.rioDuino.updateMode(Mode.ORANGE);
        System.out.println("ORANGE");

      }
      else if (Robot.oi.panelLimitSwitch.get())
      {
        Robot.elevator.gamePieceType = GamePieceType.HATCH;
        SmartDashboard.putBoolean("Is Cargo Mode", false);

        Robot.rioDuino.updateMode(Mode.YELLOW);
        System.out.println("YELLOW");

      }
      else
      {
        Robot.elevator.gamePieceType = GamePieceType.NONE;
        SmartDashboard.putBoolean("Is Cargo Mode", false);
        Robot.rioDuino.updateMode(Mode.GREEN);
        System.out.println("Green");
      }
    }

  }

}
