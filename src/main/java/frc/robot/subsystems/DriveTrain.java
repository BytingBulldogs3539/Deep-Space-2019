/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.DriveCommand;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.PigeonState;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motion.*;

import frc.robot.RobotMap;
import frc.robot.motionprofiling.*;
import frc.robot.utilities.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Six mini cim, six wheel tank
 */
public class DriveTrain extends Subsystem
{
  public TalonSRX fr, fl, mr, ml, br, bl;
  PigeonIMU pigeon;
  Drive drive;

  public DriveTrain()
  {
    // Initiation of Drive Talons
    // Our right master talon.
    fr = new TalonSRX(RobotMap.FRTalon);
    // Our left master talon.
    fl = new TalonSRX(RobotMap.FLTalon);
    mr = new TalonSRX(RobotMap.MRTalon);
    ml = new TalonSRX(RobotMap.MLTalon);
    br = new TalonSRX(RobotMap.BRTalon);
    bl = new TalonSRX(RobotMap.BLTalon);
    pigeon = new PigeonIMU(mr);

    // We only use two motor drive train because the rest of the motors follow our
    // "master talons"
    drive = new Drive(fr, fl);

 

    // TODO: Think about adding a incase if pigeon is not plugged in or not
    // responding.
    if (pigeon.getState() != PigeonState.Ready && pigeon.getState() != PigeonState.Initializing)
    {
      DriverStation.reportError("ERROR: PIGEON NOT RESPONDING!", false);
    }

    // Stores basic Talon config
    TalonSRXConfiguration basicTalonConfig = new TalonSRXConfiguration();

    // Basic config for Talons
    basicTalonConfig.nominalOutputForward = 0.0;
    basicTalonConfig.nominalOutputReverse = 0.0;
    basicTalonConfig.peakOutputForward = 1.0;
    basicTalonConfig.peakOutputReverse = -1.0;
    basicTalonConfig.continuousCurrentLimit = 20;//37
    basicTalonConfig.peakCurrentLimit = 30;
    basicTalonConfig.peakCurrentDuration = 100;
    basicTalonConfig.voltageCompSaturation = RobotMap.voltageCompSaturation;

    // Applies config to all Talons

    // We don't need to configure our fr talon because we configure it later with
    // more motion profiling stuff.
    fl.configAllSettings(basicTalonConfig);
    mr.configAllSettings(basicTalonConfig);
    ml.configAllSettings(basicTalonConfig);
    br.configAllSettings(basicTalonConfig);
    bl.configAllSettings(basicTalonConfig);

    fl.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    fr.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

    fl.setSensorPhase(true);
    fr.setSensorPhase(false);



  
    // The left side must also be inverted so that we can drive forward with two
    fr.setInverted(true);
    mr.setInverted(true);
    br.setInverted(false);

    // Back motors must be reversed because of the gear box
    fl.setInverted(false);
    ml.setInverted(false);
    bl.setInverted(true);

    
    


    /* --- config the motion profiling specific settings --- */

    TalonSRXConfiguration MotionConfig = new TalonSRXConfiguration();

    MotionConfig.nominalOutputForward = 0.0;
    MotionConfig.nominalOutputReverse = 0.0;
    MotionConfig.peakOutputForward = 1.0;
    MotionConfig.peakOutputReverse = -1.0;
    MotionConfig.continuousCurrentLimit = 37;
    MotionConfig.peakCurrentLimit = 50;
    MotionConfig.peakCurrentDuration = 100;
    MotionConfig.voltageCompSaturation = RobotMap.voltageCompSaturation;

    /* remote 0 will capture Pigeon IMU */
    MotionConfig.remoteFilter0.remoteSensorDeviceID = pigeon.getDeviceID();
    MotionConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.GadgeteerPigeon_Yaw;
    /* remote 1 will capture the quad encoder on left talon */
    MotionConfig.remoteFilter1.remoteSensorDeviceID = fl.getDeviceID();
    MotionConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.TalonSRX_SelectedSensor;
    /* drive-position is our local quad minus left-talon's selected sens. depending
     * on sensor orientation, it could be the sum instead */

    MotionConfig.sum0Term = FeedbackDevice.QuadEncoder;
    MotionConfig.sum1Term = FeedbackDevice.RemoteSensor1;

    MotionConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorSum;
    MotionConfig.primaryPID.selectedFeedbackCoefficient = 0.5; /* divide by 2 so we servo sensor-average, intead of sum */
    /* turn position will come from the pigeon */
    MotionConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor0;
    /* rest of the configs */
    MotionConfig.neutralDeadband = RobotMap.neutralDeadband; /* 0.1 % super small for best low-speed control */
    MotionConfig.slot0.kF = RobotMap.gains_MotProf.f;
    MotionConfig.slot0.kP = RobotMap.gains_MotProf.p;
    MotionConfig.slot0.kI = RobotMap.gains_MotProf.i;
    MotionConfig.slot0.kD = RobotMap.gains_MotProf.d;
    MotionConfig.slot0.integralZone = (int) RobotMap.gains_MotProf.iZone;
    MotionConfig.slot0.closedLoopPeakOutput = RobotMap.gains_MotProf.peakOutput;

    MotionConfig.slot1.kF = RobotMap.gains_MotProfAngle.f;
    MotionConfig.slot1.kP = RobotMap.gains_MotProfAngle.p;
    MotionConfig.slot1.kI = RobotMap.gains_MotProfAngle.i;
    MotionConfig.slot1.kD = RobotMap.gains_MotProfAngle.d;
    MotionConfig.slot1.integralZone = (int) RobotMap.gains_MotProfAngle.iZone;
    MotionConfig.slot1.closedLoopPeakOutput = RobotMap.gains_MotProfAngle.peakOutput;


    // Apply the configuration to the right master talon
    fr.configAllSettings(MotionConfig);

    /* speed up the target polling for PID[0] and PID-aux[1] */
    fr.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 10); /* plotthread is polling aux-pid-sensor-pos */
    fr.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10);
    fr.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 10);

    zeroEncoders();

    // TODO: Check to see if we want to disable this in teleop
    //plotThread = new PlotThread(fr);

    fl.setNeutralMode(NeutralMode.Brake);
    fr.setNeutralMode(NeutralMode.Brake);

    ml.setNeutralMode(NeutralMode.Brake);
    mr.setNeutralMode(NeutralMode.Brake);

    bl.setNeutralMode(NeutralMode.Brake);
    br.setNeutralMode(NeutralMode.Brake);

    ml.follow(fl);
    bl.follow(fl);

    mr.follow(fr);

    br.follow(fr);

    PlotThread test = new PlotThread(fr);


    
    // Set our back and middle motors to follow our master front talons.


  }

  /**
   * Allows us to start the motion profile.
   * 
   * @param buffer
   *                 The BufferedTrjaectoryPointStream that contains all of our
   *                 points.
   */
  public void startMotionProfile(BufferedTrajectoryPointStream buffer)
  {
    fr.startMotionProfile(buffer, 10, ControlMode.MotionProfileArc);
    fl.follow(fr, FollowerType.AuxOutput1);
  }

  /**
   * Sets our master talons to a disabled state so they do nothing.
   */
  public void neutralOutput()
  {
    fr.neutralOutput();
    fl.neutralOutput();
  }

  /**
   * Allows us to tell if the motion profile is done running.
   */
  public boolean isMotionProfileFinished()
  {
    return fr.isMotionProfileFinished();
  }

  /**
   * Allows us to get the current of the pdp slot specified.
   * 
   * @param speed
   *                The speed the robot will travel forward/backward a value
   *                between 1 and -1 (normally the left stick y axis).
   * @param turn
   *                The speed the robot will travel right/left a value between 1
   *                and -1 (normally the right stick x axis).
   */
  public void driveArcade(double speed, double turn)
  {
    drive.driveArcade(speed, turn);
  }

  /**
   * Allows us to zero all of the encoders/sensors associated with the drivetrain.
   */
  public void zeroEncoders()
  {
    pigeon.setYaw(0);
    pigeon.setAccumZAngle(0, 10);
    fl.setSelectedSensorPosition(0);
    fr.setSelectedSensorPosition(0);

    fr.getSensorCollection().setPulseWidthPosition(0, 10);
    fr.getSensorCollection().setQuadraturePosition(0, 10);

    fl.getSensorCollection().setPulseWidthPosition(0, 10);
    fl.getSensorCollection().setQuadraturePosition(0, 10);
  }

  /* Get the current reference angle of the pigeon. */
  public double getPigeonHeading()
  {
    double[] feedback = new double[3];
    pigeon.getYawPitchRoll(feedback);
    return feedback[0];
  }

  public void putEncoder()
  {
    SmartDashboard.putNumber("actual_sen_pos", fr.getSelectedSensorPosition());
    SmartDashboard.putNumber("actual_sen_vel", fr.getSelectedSensorVelocity());
  }

  /* Zero the pigeons angle. */
  public void zeroPigeon()
  {
    pigeon.setYaw(0);
  }

  @Override
  public void initDefaultCommand()
  {
    // If nothing else is running go back to running the DriveCommand.
    setDefaultCommand(new DriveCommand());
  }
}
