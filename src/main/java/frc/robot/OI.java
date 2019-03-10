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
    driver.buttonY.whenPressed(new ManualLimitSwitchCommand());

    driver.buttonA.whenPressed(new ClimbArmCommand(.95));

    driver.buttonA.whenReleased(new ClimbArmCommand(0));

    


    driver.buttonX.toggleWhenActive(new AutoTurretCommand());

    driver.buttonB.whenPressed(new ClimbArmCommand(-.95));
    driver.buttonB.whenReleased(new ClimbArmCommand(0));

    driver.buttonBL.whenPressed(new ClimbWheelCommand(.5));
    driver.buttonBL.whenReleased(new ClimbWheelCommand(0));



    
    //driver.buttonTR.toggleWhenPressed(new VisionControlCommand());

    /* Operator */

    // operator.buttonBL.whenPressed(new PanelArmActuateCommand(false));
    // operator.buttonBL.whenReleased(new PanelArmActuateCommand(true));

    operator.buttonBR.whenPressed(new CargoArmActuateCommand(true));
    operator.buttonBR.whenReleased(new CargoArmActuateCommand(false));
    operator.buttonSELECT.whenPressed(new ManualLimitSwitchCommand());


    operator.buttonBL.whenPressed(new PanelPlacementCommand(false));
    operator.buttonBL.whenReleased(new PanelPlacementCommand(true));
    
    operator.buttonX.whenPressed(new ElevatorPositionCommand(ElevatorHeight.Home,false));
    operator.buttonA.whenPressed(new ElevatorPositionCommand(ElevatorHeight.Low,false));
    //operator.buttonA.whenReleased(new ElevatorPositionCommand(ElevatorHeight.Home));

    operator.buttonB.whenPressed(new ElevatorPositionCommand(ElevatorHeight.Middle,false));
    //operator.buttonB.whenReleased(new ElevatorPositionCommand(ElevatorHeight.Home));

    operator.buttonY.whenPressed(new ElevatorPositionCommand(ElevatorHeight.High,false));
    //operator.buttonY.whenReleased(new ElevatorPositionCommand(ElevatorHeight.Home));



    operator.buttonLS.toggleWhenActive(new ElevatorTakeoverCommand());
    operator.buttonSTART.whenPressed(new TurretZeroCommand());
    operator.buttonRS.toggleWhenPressed(new TurretTakeoverCommand());

    // Turret Controls
    operator.buttonPadDownLeft.whenPressed(new TurretPositionCommand(-135,false));
    operator.buttonPadLeft.whenPressed(new TurretPositionCommand(-90,false));
    operator.buttonPadUpLeft.whenPressed(new TurretPositionCommand(-45,false));
    operator.buttonPadUp.whenPressed(new TurretPositionCommand(0,false));
    operator.buttonPadUpRight.whenPressed(new TurretPositionCommand(45,false));
    operator.buttonPadRight.whenPressed(new TurretPositionCommand(90,false));
    operator.buttonPadDownRight.whenPressed(new TurretPositionCommand(135,false));
    // This will be changed to -180 if the angle was previously negative:
    operator.buttonPadDown.whenPressed(new TurretPositionCommand(180,false));

    /* Limit Switches */
    //  cargoLimitSwitch.whenPressed(new LimitSwitchCommand());
    //  cargoLimitSwitch.whenReleased(new LimitSwitchCommand());
    cargoLimitSwitch.whenReleased(new CargoArmActuateCommand(false));

    //  panelLimitSwitch.whenPressed(new LimitSwitchCommand());
    //  panelLimitSwitch.whenReleased(new LimitSwitchCommand());
  }
}