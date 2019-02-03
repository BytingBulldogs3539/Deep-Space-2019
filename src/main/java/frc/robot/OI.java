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
    driver.buttonY.whenPressed(new ClimbCommand(0));

    /* Operator */

    operator.buttonBL.whenPressed(new PanelArmActuateCommand(true));
    operator.buttonBL.whenReleased(new PanelArmActuateCommand(false));

    operator.buttonBR.whenPressed(new CargoArmActuateCommand(true));
    operator.buttonBR.whenReleased(new CargoArmActuateCommand(false));

    operator.buttonX.whenPressed(new PanelPlacementCommand(true));
    operator.buttonX.whenReleased(new PanelPlacementCommand(false));

    // TODO: This NEEDS to be TESTED.
    operator.buttonA.whenPressed(new ElevatorPositionCommand(ElevatorHeight.High));
    operator.buttonB.whenPressed(new ElevatorPositionCommand(ElevatorHeight.Middle));
    operator.buttonY.whenPressed(new ElevatorPositionCommand(ElevatorHeight.Low));

    operator.buttonX.whenPressed(new ManualLimitSwitch());

    operator.buttonSTART.whenPressed(new IgnoreLimitSwitchCommand());

    operator.buttonLS.toggleWhenActive(new ElevatorTakeoverCommand());
    operator.buttonRS.toggleWhenActive(new TurretTakeoverCommand());

    // Turret Controls
    operator.buttonPadDownLeft.whenPressed(new TurretPositionCommand(-135));
    operator.buttonPadLeft.whenPressed(new TurretPositionCommand(-90));
    operator.buttonPadUpLeft.whenPressed(new TurretPositionCommand(-45));
    operator.buttonPadUp.whenPressed(new TurretPositionCommand(0));
    operator.buttonPadUpRight.whenPressed(new TurretPositionCommand(45));
    operator.buttonPadRight.whenPressed(new TurretPositionCommand(90));
    operator.buttonPadDownRight.whenPressed(new TurretPositionCommand(135));
    // This will be changed to -180 if the angle was previously negative:
    operator.buttonPadDown.whenPressed(new TurretPositionCommand(180));

    /* Limit Switches */
    // cargoLimitSwitch.whileHeld(new LimitSwitchCommand());
    // cargoLimitSwitch.whenReleased(new LimitSwitchCommand());

    // panelLimitSwitch.whenPressed(new LimitSwitchCommand());
    // panelLimitSwitch.whenReleased(new LimitSwitchCommand());
  }
}
