/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator.GamePieceType;

/**
 * Add your docs here.
 */
public class LimitSwitchCommand extends InstantCommand {
  /**
   * Run this command to update the status of all of the lights
   */
  public LimitSwitchCommand() {
    super();
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    if (Robot.oi.cargoLimitSwitch.get() && Robot.oi.panelLimitSwitch.get()) {
      Robot.elevator.gamePieceType = GamePieceType.BOTH;
      // TODO: add update lights
      // TODO: add button override
    } else if (Robot.oi.cargoLimitSwitch.get()) {
      Robot.elevator.gamePieceType = GamePieceType.CARGO;
    } else if (Robot.oi.panelLimitSwitch.get()) {
      Robot.elevator.gamePieceType = GamePieceType.HATCH;
    } else {
      Robot.elevator.gamePieceType = GamePieceType.NONE;
    }
  }

}
