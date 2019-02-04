package frc.robot.utilities;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Button Wrapper to allow limit switches to mock a Button
 * 
 * @author FRC-3539
 *
 * @since 1/26/18
 */

public class LimitButton extends Button
{
	/* the port that the limit switch is plugged into. */
	DigitalInput port;

	/**
	 * The constructor of a limitbutton which makes a limit switch mock a button.
	 * 
	 * @param portnum
	 *                    the port that the limit switch is plugged into.
	 */
	public LimitButton(int portnum)
	{
		port = new DigitalInput(portnum);
	}

	/**
	 * @return returns weather the limit switch is being pressed.
	 */
	@Override
	public boolean get()
	{
		return port.get();
	}
}
