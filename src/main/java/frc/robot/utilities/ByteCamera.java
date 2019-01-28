package frc.robot.utilities;

import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class ByteCamera
{
    private UsbCamera camera;

    /**
     * Allows us to create a camera object without causing any errors any long code
     * in Robot.
     * 
     * @param label
     *                  The name to give the camera.
     * @param port
     *                  The device number of the camera interface.
     */
    public ByteCamera(String label, int port)
    {
        // Try creating the camera however if this fails then we need to tell the driver
        // that they won't have a camera if they don't fix it.
        try
        {
            camera = CameraServer.getInstance().startAutomaticCapture(label, port);
            camera.setResolution(240, 240);
            camera.setFPS(20);
            camera.setExposureManual(3);
            camera.setBrightness(10);
            camera.getProperty("compression").set(80);
            camera.getProperty("default_compression").set(80);
            camera.getProperty("width").set(240);
            camera.getProperty("height").set(240);

        }
        catch (Exception e)
        {
            DriverStation.reportError("ERROR: CAMERA " + port + " UNPLUGGED!", e.getStackTrace());
        }
    }
}
