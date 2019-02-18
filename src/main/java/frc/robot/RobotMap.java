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
    /* Robot ------------------------------------------------------------ */
    public static final int pcm = 20; // CAN ID number
    public static final int pdp = 30; // CAN ID number
    public static final int encTicksPerRot = 4096;
    public static boolean useLimitSwitches = true;

    // Voltage "cap" to promote consistency as the voltage drops over time
    public static final double voltageCompSaturation = 12.2;

    // Amount of time to wait before reporting to Driver Station that action failed
    public static final int timeoutMs = 20;

    /* DriveTrain ------------------------------------------------------------ */

    public static boolean scaleDriveSticks = true;

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
    public static final int FLTalon = 10; // 4
    public static final int FRTalon = 3; // 9
    public static final int MLTalon = 11; // 2
    public static final int MRTalon = 2; // 10
    public static final int BLTalon = 12; // 1
    public static final int BRTalon = 1;// 18

    /* Evelator ------------------------------------------------------------ */

    // DIO number of port that limit switch is plugged into
    public static final int cargoLimitSwitchPort = 8;
    public static final int panelLimitSwitchPort = 9;

    // List of heights that the elevator can go to (in inches) while holding a cargo
    // TODO: Change these when we get the robot.

    public static double cargoHigh = 74.0;
    public static double cargoMiddle = 48.0;
    public static double cargoLow = 20.0;
    public static double home = 0.0;
    // List of heights that the elevator can go to (in inches) while holding a cargo
    // TODO: Change these when we get the robot.
    public static double hatchHigh = 0.0;
    public static double hatchMiddle = 60.0;
    public static double hatchLow = 35.0;

    public static double cargoHighOffset = 0.0;
    public static double cargoMiddleOffset = 0.0;
    public static double cargoLowOffset = 0.0;
    // List of heights that the elevator can go to (in inches) while holding a cargo
    // TODO: Change these when we get the robot.
    public static double hatchHighOffset = 0.0;
    public static double hatchMiddleOffset = 0.0;
    public static double hatchLowOffset= 0.0;

    // List of TalonSRX CAN ID numbers
    public static final int elevatorMaster = 6;
    public static final int elevatorSlave = 5;

    public static final double elevatorSpeedMultiplier = .5;

    public static final double  InchesToElevatorEncMultiplier = 320;

    /* Manipulator ------------------------------------------------------------ */

    public static final int panelIntakeOn = 6;
    public static final int panelIntakeOff = 1;
    public static final int cargoIntakeOn = 3;
    public static final int cargoIntakeOff = 4;
    public static final int placementOn = 7;
    public static final int placementOff = 0;
    public static final int floorIntakeOn = 5;
    public static final int floorIntakeOff = 2;

    // List of TalonSRX CAN ID numbers
    public static final int manipulatorMaster = 7;

    /* Turret ------------------------------------------------------------ */

    // Soft limits for angle
    public static final int turretNegativeLimit = -225;
    public static final int turretPositiveLimit = 225;

    // List of TalonSRX CAN ID numbers
    public static final int turretMaster = 9;

    // Gear ratios for lazy suzan
    public static final double turretGearRatio = 3.83;

    // A speed multiplier for manual take over of the turret.
    public static final double turretSpeedMultipier = .5;

    /* Verticate ------------------------------------------------------------ */
    public static final int verticateTopMaster = 4;
    public static final int verticateTopSlave = 8;
    public static final int verticateBottomMaster = 13;

    /* Motion Profile
     * ------------------------------------------------------------ */
    public final static int primaryPIDSlot = 0; // any slot [0,3]
    public final static int auxPIDSlot = 1; // any slot [0,3]

    /**
     * Motor neutral dead-band, set to the minimum 0.1%.
     */
    public final static double neutralDeadband = 0.01;

    /**
     * Pigeon will reports 8192 units per 360 deg (1 rotation) If using
     * encoder-derived (left plus/minus right) heading, find this emperically.
     */
    public final static double turnUnitsPerDeg = (8192.0 / 360.0);

    /* Gains / PIDs ------------------------------------------------------------ */

    /**
     * PID Gains may have to be adjusted based on the responsiveness of control loop
     * kP kI kD kF Iz PeakOut
     */
    // TODO: Tune for robot.
    // Tuned
    public static Gains elevatorGains = new Gains(.6, 0.00005, 0.0, .7, 400, 1.00);
    // Tuned
    public static Gains turretGains = new Gains(1.8, 0.001, 0.0, .4, 400, 1.00);
    // NOT Tuned
    public static Gains TurnPid = new Gains(.1, 0.0, 0.0, 0.0, 0.0, 0.0);
    // SEMI Tuned
    public final static Gains gains_MotProf = new Gains(0.01, 0.0, 0.0, .125, 400, 1.00);
    // SEMI Tuned
    public final static Gains gains_MotProfAngle = new Gains(.3, 0.0, 0, 1023.0 / 9500.0, 400, 1.00);
}