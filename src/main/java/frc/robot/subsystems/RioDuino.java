/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * Microcontroller attached directly to RoboRIO in order to control light strips
 */
public class RioDuino
{
  private static SerialPort rioDuinoPort;

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

  public void updateMode(Mode mode)
  {
    if (rioDuinoPort != null)
    {
      System.out.println("UPDATE");
      rioDuinoPort.writeString("" + (char) mode.ordinal());
    }
  }

  public enum Mode
  {
    RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, PINK, PULSERED, PULSEORANGE, PULSEYELLOW, PULSEGREEN, PULSEBLUE, PULSEPURPLE, PULSEPINK, OFF
  }
}
