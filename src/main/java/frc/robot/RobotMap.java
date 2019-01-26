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
    /* DriveTrain ---------- */

    // List of TalonSRX CAN ID numbers
    public static final int FRTalon = 4;
    public static final int FLTalon = 9;
    public static final int MRTalon = 2;
    public static final int MLTalon = 10;
    public static final int BRTalon = 1;
    public static final int BLTalon = 18;

    /* Evelator ---------- */

    //List of heights that the elevator can go to in inches while holding a cargo
    public static double CargoHigh = 0.0;
    public static double CargoMiddle = 0.0;
    public static double CargoLow = 0.0;

    //List of heights that the elevator can go to in inches while holding a cargo
    public static double HatchHigh = 0.0;
    public static double HatchMiddle = 0.0;
    public static double HatchLow = 0.0;

    // List of TalonSRX CAN ID numbers
    public static final int ElevatorMaster = 55;
    public static final int ElevatorSlave = 56;

    /* Manipulator ---------- */
    public static final int manipulatorMaster = 54;

    /* Turret ---------- */
    public static final int turretMaster = 53;
    public static final int turretSlave = 52;

    /* Verticate ---------- */

    // Amount of time to wait before reporting to Driver Station that action failed
    public static final int timeoutMs = 20;

    // PDP
    public static final int pdp = 0;

    /* Gains / PIDs ---------- */
    public static Gains elevatorGains = new Gains(.1, 0.0, 0.0, .054, 400, 1.00);
    public static Gains turretGains = new Gains(.1, 0.0, 0.0, .054, 400, 1.00);

}