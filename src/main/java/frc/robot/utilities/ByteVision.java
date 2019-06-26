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

    SerialPort visionPortIntake;
    SerialPort visionPortTurret;
    double lastValue = 0.0;
    int counter=0;

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
            DriverStation.reportError("ERROR: Vision tracking turret camera", e.getStackTrace());
        }
    }

    public double getDataIntake()
    {
        if (visionPortIntake != null)
        {
            
            try
            {
                counter=0;
                lastValue = Double.parseDouble(visionPortIntake.readString());
                return lastValue;
            }
            catch(Exception e)
            {
                counter++;
                if(counter<25)
                    return lastValue;
                return 0.0;

            }
        }
        return 0.0;
    }

    // {
    //     if (visionPortIntake != null)
    //     {
    //         if(visionPortIntake.readString()!=null&&!visionPortIntake.readString().equals(" ")&&!visionPortIntake.readString().equals(""))
    //         {
    //             try
    //             {
    //                 lastvalue = Double.parseDouble(visionPortIntake.readString());
    //             }
    //             catch(Exception e)
    //             {
    //                 return 0.0;
    //             }
    //         }   
    //         return lastvalue;
    //     }
    //     return 0;
    // }

    public int getPixeloffset()
    {
        return 3539;// TODO: return Pixel offset
    }

    public String getDataTurret()
    {
        if (visionPortIntake != null)
        {
            return visionPortTurret.readString();
        }
        return "";

    }
}
