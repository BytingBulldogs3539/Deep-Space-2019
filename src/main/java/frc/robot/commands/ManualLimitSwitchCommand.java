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
import frc.robot.subsystems.Elevator.GamePieceType;
import frc.robot.subsystems.RioDuino.Mode;

public class ManualLimitSwitchCommand extends InstantCommand
{
  public ManualLimitSwitchCommand()
  {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
    if (Robot.elevator.gamePieceType == GamePieceType.NONE)
    {
      Robot.elevator.gamePieceType = GamePieceType.HATCH;
      SmartDashboard.putBoolean("Is Cargo Mode", false);
      //Robot.rioDuino.updateMode(Mode.YELLOW);
      System.out.println("YELLOW");

    }
    else if (Robot.elevator.gamePieceType == GamePieceType.BOTH)
    {
      Robot.elevator.gamePieceType = GamePieceType.HATCH;
      SmartDashboard.putBoolean("Is Cargo Mode", false);

      //Robot.rioDuino.updateMode(Mode.YELLOW);
      System.out.println("YELLOW");

    }
    else if (Robot.elevator.gamePieceType == GamePieceType.HATCH)
    {
      Robot.elevator.gamePieceType = GamePieceType.CARGO;
      SmartDashboard.putBoolean("Is Cargo Mode", true);

      //Robot.rioDuino.updateMode(Mode.ORANGE);
      System.out.println("ORANGE");
    }
    else if (Robot.elevator.gamePieceType == GamePieceType.CARGO)
    {
      Robot.elevator.gamePieceType = GamePieceType.HATCH;
      SmartDashboard.putBoolean("Is Cargo Mode", false);
      //Robot.rioDuino.updateMode(Mode.YELLOW);
      System.out.println("YELLOW");

    }
  }
}
