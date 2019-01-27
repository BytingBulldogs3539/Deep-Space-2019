/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.*;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class RioDuino extends Subsystem
{
  private static SerialPort rioDuinoPort;

  public DigitalInput cargoLimitSwitch = new DigitalInput(RobotMap.cargoLimitSwitchPort);
  public DigitalInput panelLimitSwitch = new DigitalInput(RobotMap.panelLimitSwitchPort);

  public RioDuino()
  {
    try
    {
      rioDuinoPort = new SerialPort(9600, SerialPort.Port.kMXP);
    }
    catch (Exception e)
    {
      DriverStation.reportError("Error with LEDS/RIODUINO", e.getStackTrace());
    }
  }

  public static void updateMode(Mode mode)
  {
    if (rioDuinoPort != null)
    {
      rioDuinoPort.writeString("" + (char) mode.ordinal());
    }
  }

  public enum Mode
  {
    ORANGE, YELLOW, RED, PURPLE
  }

  public void initDefaultCommand()
  {
    setDefaultCommand(new LimitSwitchCommand());
  }
}
