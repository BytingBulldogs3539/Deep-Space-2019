/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.utilities.LimitButton;
import frc.robot.utilities.LogitechF310;
import frc.robot.commands.*;
import frc.robot.subsystems.Elevator.ElevatorHeight;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
  // driver controller should be plugged in first.
  // operator controller should be plugged in second.

  public LogitechF310 driver = new LogitechF310(0);
  public LogitechF310 operator = new LogitechF310(1);

  public LimitButton cargoLimitSwitch = new LimitButton(RobotMap.cargoLimitSwitchPort);
  public LimitButton panelLimitSwitch = new LimitButton(RobotMap.panelLimitSwitchPort);

  public OI()
  {
    /* Driver */

    /* Operator */

    // TODO: This NEEDS to be TESTED.
    operator.buttonA.whenPressed(new ElevatorPositionCommand(ElevatorHeight.High));
    operator.buttonB.whenPressed(new ElevatorPositionCommand(ElevatorHeight.Middle));
    operator.buttonY.whenPressed(new ElevatorPositionCommand(ElevatorHeight.Low));

    operator.buttonSTART.whenPressed(new IgnoreLimitSwitchCommand());

    operator.buttonLS.toggleWhenActive(new ElevatorTakeoverCommand());
    operator.buttonRS.toggleWhenActive(new TurretTakeoverCommand());

    /* Limit Switches */
    cargoLimitSwitch.whenPressed(new LimitSwitchCommand());
    cargoLimitSwitch.whenReleased(new LimitSwitchCommand());

    panelLimitSwitch.whenPressed(new LimitSwitchCommand());
    panelLimitSwitch.whenReleased(new LimitSwitchCommand());
  }
}
