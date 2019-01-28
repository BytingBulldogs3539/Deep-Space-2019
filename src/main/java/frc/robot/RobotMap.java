package frc.robot;

import frc.robot.utilities.*;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap
{
    /* Robot ------------------------------ */
    public static final int pcm = 0;
    public static final int pdp = 0;
    public static final int encTicksPerRot = 4096;
    public static boolean useLimitSwitches=true;

    /* DriveTrain ------------------------------ */

    /**
     * How many sensor units per rotation. Using CTRE Magnetic Encoder.
     * 
     * @link https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
     */
                                                                //Ticks per rev //Gear ratio
    public final static int sensorUnitsPerRotDriveTrain = (int) (4096 * 3.86);

    // List of TalonSRX CAN ID numbers
    public static final int FRTalon = 4;
    public static final int FLTalon = 9;
    public static final int MRTalon = 2;
    public static final int MLTalon = 10;
    public static final int BRTalon = 1;
    public static final int BLTalon = 18;

    public static final int PigeonID = 0;

    /* Evelator ------------------------------ */

    // DIO number of port that limit switch is plugged into
    public static final int cargoLimitSwitchPort = 0;
    public static final int panelLimitSwitchPort = 1;

    // List of heights that the elevator can go to (in inches) while holding a cargo
    //TODO: Change these when we get the robot.

    public static double cargoHigh = 0.0;
    public static double cargoMiddle = 0.0;
    public static double cargoLow = 0.0;

    // List of heights that the elevator can go to (in inches) while holding a cargo
    //TODO: Change these when we get the robot.
    public static double hatchHigh = 0.0;
    public static double hatchMiddle = 0.0;
    public static double hatchLow = 0.0;

    // List of TalonSRX CAN ID numbers
    public static final int elevatorMaster = 55;
    public static final int elevatorSlave = 56;

    /* Manipulator ------------------------------ */

    // List of TalonSRX CAN ID numbers
    public static final int manipulatorMaster = 54;

    /* Turret ------------------------------ */

    // List of TalonSRX CAN ID numbers
    public static final int turretMaster = 53;
    public static final int turretSlave = 52;

    // Gear ratios for lazy suzan
    public static final int largeGear = 60;
    public static final int smallGear = 16;

    /* Verticate ------------------------------ */
    public static final int verticateTopMaster = 53;
    public static final int verticateTopSlave = 52;
    public static final int verticateBottomMaster = 53;
    public static final int verticateBottomSlave = 52;

    // Amount of time to wait before reporting to Driver Station that action failed
    public static final int timeoutMs = 20;

    /* Gains / PIDs ------------------------------ */
    public static Gains elevatorGains = new Gains(.1, 0.0, 0.0, .054, 400, 1.00);
    public static Gains turretGains = new Gains(.1, 0.0, 0.0, .054, 400, 1.00);
    public static final int manipulatorOn = 1;
    public static final int manipulatorOff = 0;
}