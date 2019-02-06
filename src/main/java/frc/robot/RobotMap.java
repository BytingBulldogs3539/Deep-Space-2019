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
    /* Robot ----------------------------------- */
    public static final int pcm = 20; // CAN ID number
    public static final int pdp = 30; // CAN ID number
    public static final int encTicksPerRot = 4096;
    public static boolean useLimitSwitches = true;

    // Voltage "cap" to promote consistency as the voltage drops over time
    public static final double voltageCompSaturation = 12.2;

    // Amount of time to wait before reporting to Driver Station that action failed
    public static final int timeoutMs = 20;

    /* DriveTrain ------------------------------ */

    /**
     * How many sensor units per rotation. Using CTRE Magnetic Encoder.
     * 
     * @link https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
     */
    // Ticks per rev //Gear ratio
    public final static int sensorUnitsPerRotDriveTrain = (int) (4096 * 2.5);
    // public final static int sensorUnitsPerRotDriveTrain = (int) (4096 / 1.2);

    // The battery is the front of the robot and the left is the left and the right
    // is the right.
    // List of TalonSRX CAN ID numbers
    public static final int FRTalon = 12; // 4
    public static final int FLTalon = 1; // 9
    public static final int MRTalon = 11; // 2
    public static final int MLTalon = 2; // 10
    public static final int BRTalon = 10; // 1
    public static final int BLTalon = 3;// 18

    /* Evelator ------------------------------ */

    // DIO number of port that limit switch is plugged into
    public static final int cargoLimitSwitchPort = 2;
    public static final int panelLimitSwitchPort = 3;

    // List of heights that the elevator can go to (in inches) while holding a cargo
    // TODO: Change these when we get the robot.

    public static double cargoHigh = 0.0;
    public static double cargoMiddle = 0.0;
    public static double cargoLow = 0.0;

    // List of heights that the elevator can go to (in inches) while holding a cargo
    // TODO: Change these when we get the robot.
    public static double hatchHigh = 0.0;
    public static double hatchMiddle = 0.0;
    public static double hatchLow = 0.0;

    // List of TalonSRX CAN ID numbers
    public static final int elevatorMaster = 7;
    public static final int elevatorSlave = 6;

    public static final double elevatorSpeedMultiplier = 1;

    /* Manipulator ------------------------------ */

    // List of TalonSRX CAN ID numbers
    public static final int manipulatorMaster = 5;

    /* Turret ------------------------------ */

    // Soft limits for angle
    public static final int turretNegativeLimit = -225;
    public static final int turretPositiveLimit = 225;

    // List of TalonSRX CAN ID numbers
    public static final int turretMaster = 4;

    // Gear ratios for lazy suzan
    public static final int largeGear = 60;
    public static final int smallGear = 16;

    // A speed multiplier for manual take over of the turret.
    public static final double turretSpeedMultipier = .5;

    /* Verticate ------------------------------ */
    public static final int verticateTopMaster = 8;
    public static final int verticateTopSlave = 9;
    public static final int verticateBottomMaster = 13;

    /* Gains / PIDs ------------------------------ */
    public static Gains elevatorGains = new Gains(.1, 0.0, 0.0, .054, 400, 1.00);
    public static Gains turretGains = new Gains(.1, 0.0, 0.0, .054, 400, 1.00);
    public static Gains TurnPid = new Gains(.1, 0.0, 0.0, 0.0, 0.0, 0.0);
    public static final int panelIntakeOn = 1;
    public static final int panelIntakeOff = 0;
    public static final int cargoIntakeOn = 3;
    public static final int cargoIntakeOff = 2;
    public static final int placementLeftOn = 5;
    public static final int placementLeftOff = 4;
    public static final int placementRightOn = 7;
    public static final int placementRightOff = 6;
}