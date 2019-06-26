package frc.robot.utilities;

import edu.wpi.cscore.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class ByteCamera
{
    private UsbCamera camera;
  

    /**
     * Allows us to create a camera object witshout causing any errors any long code
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
            //camera = new UsbCamera(label, port);
            camera = CameraServer.getInstance().startAutomaticCapture(label,port);

            camera.setResolution(360, 360);
            camera.setFPS(20);
            camera.setExposureManual(3);
            camera.setBrightness(50);
            CameraServer.getInstance().addCamera(camera);
            

        }
        catch (Exception e)
        {
            DriverStation.reportError("ERROR: CAMERA " + port + " UNPLUGGED!", e.getStackTrace());
        }
    }
}
