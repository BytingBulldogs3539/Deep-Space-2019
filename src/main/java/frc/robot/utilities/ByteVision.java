/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * Add your docs here.
 */
public class ByteVision
{
    /* # sp = serial.Serial(port, baudrate=115200, bytesize=serial.EIGHTBITS,
     * parity=serial.PARITY_NONE, # xonxoff=False, rtscts=False,
     * stopbits=serial.STOPBITS_ONE, timeout=None, dsrdtr=True) */
    // TODO: make a watchdog for this so if we loose the camera we don't fail our
    // auton.

    // TODO: make this in try catch so if vision is not their we don't kill our
    // robot.
    SerialPort visionPortIntake;
    SerialPort visionPortTurret;

    public ByteVision()
    {
        try
        {
            visionPortIntake = new SerialPort(115200, SerialPort.Port.kUSB);

        }
        catch (Exception e)
        {
            DriverStation.reportError("ERROR: Vision tracking intake camera", e.getStackTrace());
        }

        try
        {
            visionPortTurret = new SerialPort(115200, SerialPort.Port.kUSB1);
        }
        catch (Exception e)
        {
            DriverStation.reportError("ERROR: Vision tracking intake camera", e.getStackTrace());
        }
    }

    public String getDataIntake()
    {
        return visionPortIntake.readString();
    }

    public String getDataTurret()
    {
        return visionPortTurret.readString();
    }
}
