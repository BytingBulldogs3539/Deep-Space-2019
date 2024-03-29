/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autongroups.CenterLeftHatch;
import frc.robot.autongroups.CenterRightHatch;
import frc.robot.autongroups.ConfigMotion;
import frc.robot.autongroups.Circle;

import frc.robot.autongroups.MotionTest;
import frc.robot.autongroups.RightHatchOne;
import frc.robot.autongroups.LeftRocketJump;
import frc.robot.autongroups.RightRocketRear;
import frc.robot.autongroups.RightRocketRear;
import frc.robot.autongroups.LeftHatchOne;
import frc.robot.autongroups.LeftRocketRear;

import frc.robot.motionprofiling.MotionProfiling;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.RioDuino;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Elevator.GamePieceType;
import frc.robot.subsystems.RioDuino.Mode;
import frc.robot.subsystems.Verticate;
import frc.robot.motionprofiling.*;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.*;

import frc.robot.utilities.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
  public static DriveTrain drivetrain;
  public static Elevator elevator;
  public static Manipulator manipulator;
  public static OI oi;
  public static RioDuino rioDuino;
  public static Turret turret;
  public static Verticate verticate;
  public static Compressor c;
  public static ByteVision byteVision;
  public static ByteCamera fCamera, bCamera;

  MotionCommandGroup autonomousCommand;
  SendableChooser<MotionCommandGroup> chooser = new SendableChooser<>();
  // Used to select what game piece we start with.
  SendableChooser<GamePieceType> gamePieceChooser = new SendableChooser<>();

  public static HashMap<String, ByteTrajectoryPointStream> MotionBuffers = new HashMap<String, ByteTrajectoryPointStream>();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit()
  {
    drivetrain = new DriveTrain();
    elevator = new Elevator();
    manipulator = new Manipulator();
    rioDuino = new RioDuino();
    turret = new Turret();
    verticate = new Verticate();
    c = new Compressor(RobotMap.pcm);

    byteVision = new ByteVision();

    oi = new OI();

    // Let's start the camera servers.
    // TODO: test to make sure that both the front and the back camera are always
    // the front and back camera.
    fCamera = new ByteCamera("Front", 0);
   // bCamera = new ByteCamera("Back", 1);

    gamePieceChooser.addOption("Hatch", GamePieceType.HATCH);
    gamePieceChooser.addOption("Cargo", GamePieceType.CARGO);

    chooser.setDefaultOption("Default Auto", new MotionTest());
    chooser.addOption("Motion Config Auto", new ConfigMotion());
    chooser.addOption("CenterRightHatch", new CenterRightHatch());
    chooser.addOption("CenterLeftHatch",new CenterLeftHatch());
    chooser.addOption("RightHatchOne", new RightHatchOne());
    chooser.addOption("RightRocketRear", new RightRocketRear());
    chooser.addOption("LeftRocketJump", new LeftRocketJump());

    chooser.addOption("LeftHatchOne", new LeftHatchOne());
    chooser.addOption("LeftRocketRear", new LeftRocketRear());

    chooser.addOption("Circle", new Circle());

    // chooser.addOption("My Auto", new MyAutoCommand());

    SmartDashboard.putData("Auto mode", chooser);
    SmartDashboard.putData("Game Piece", gamePieceChooser);
    Robot.elevator.gamePieceType = GamePieceType.HATCH;
    gamePieceChooser.setDefaultOption("Game Piece Auto", GamePieceType.HATCH);
    gamePieceChooser.setDefaultOption("Game Piece Auto", GamePieceType.HATCH);

    rioDuino.updateMode(Mode.PULSEGREEN);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic()
  {
    // rioDuino.updateMode(Mode.PULSERED);
     //System.out.println("INTAKE " + byteVision.getDataIntake() + " End Intake");
     //System.out.println("Turret" + byteVision.getDataTurret() + "End Turret");
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit()
  {
    c.start();
    // c.stop();
  }

  @Override
  public void disabledPeriodic()
  {
    Scheduler.getInstance().run();

    // if (autonomousCommand != chooser.getSelected() && chooser.getSelected() != null)
    // {
    //   autonomousCommand = chooser.getSelected();
    //   for (String fileName : autonomousCommand.motionProfileList)
    //   {
    //     System.out.println("Load File");
    //     Robot.MotionBuffers.put(fileName, MotionProfiling.initBuffer(fileName));

    //   }
    // }
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit()
  {
    // We need to get the feed back from the drivers and give it to our elevator.
    elevator.gamePieceType = gamePieceChooser.getSelected();

    /**
     * This is here to make sure that we are not left in motion magic mode.
     */
    elevator.neutralOutput();
    drivetrain.neutralOutput();

    // TODO: Think about changing this to not recreate the object on init and add a
    // button to refresh the command instead.
    // try
    // {
    //   autonomousCommand = autonomousCommand.getClass().newInstance();
    // }
    // catch (InstantiationException e)
    // {
    //   e.printStackTrace();
    // }
    // catch (IllegalAccessException e)
    // {
    //   e.printStackTrace();
    // }

    
    // if (autonomousCommand != null)
    // {
    //   autonomousCommand.start();
    // }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic()
  {
    Scheduler.getInstance().run();

    if (oi.driver.buttonSTART.get())
    {
      DriverStation.reportWarning("AUTO CANCELED BY USER", false);
      autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopInit()
  {
    c.start();
    // c.stop();//remove

    /**
     * This is here to make sure that we are not left in motion magic mode.
     */
    elevator.neutralOutput();
    drivetrain.neutralOutput();

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null)
    {
      autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic()
  {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {
  }
}