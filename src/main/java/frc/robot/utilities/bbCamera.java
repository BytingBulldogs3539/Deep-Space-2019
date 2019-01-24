package frc.robot.utilities;

import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class bbCamera 
{
    private UsbCamera camera;
    public bbCamera(String label,int port)
    {
        try
        {
            camera = CameraServer.getInstance().startAutomaticCapture(label, port);
            camera.setResolution(240, 240);
            camera.setFPS(20);
            camera.setExposureManual(50);
            camera.setBrightness(50);
            camera.getProperty("compression").set(50);
            camera.getProperty("default_compression").set(50);
            camera.getProperty("width").set(240);
            camera.getProperty("height").set(240);
            
        }
        catch(Exception e)
        {
            DriverStation.reportError("ERROR: CAMERA "+ port +" UNPLUGGED!", e.getStackTrace());
        }
    }
}
