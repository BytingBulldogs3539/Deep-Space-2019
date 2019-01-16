/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.DriveCommand;

import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.PigeonState;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motion.*;
import frc.robot.utilities.*;

import frc.robot.RobotMap;

public class DriveTrain extends Subsystem
{
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  TalonSRX fr, fl, mr, ml, br, bl;
  PigeonIMU pigeon;

  Drive drive;

  public DriveTrain()
  {
    // Initiation of drive Talons
    fr = new TalonSRX(RobotMap.FRTalon);
    fl = new TalonSRX(RobotMap.FLTalon);
    mr = new TalonSRX(RobotMap.MRTalon);
    ml = new TalonSRX(RobotMap.MLTalon);
    br = new TalonSRX(RobotMap.BRTalon);
    bl = new TalonSRX(RobotMap.BLTalon);
    pigeon = new PigeonIMU(0);

    drive = new Drive(fr, fl);

    if (pigeon.getState() != PigeonState.Ready || pigeon.getState() != PigeonState.Initializing)
    {
      System.out.println("PIGEON READY!");
    }

    // Stores basic Talon config
    TalonSRXConfiguration basicTalonConfig = new TalonSRXConfiguration();
    // Basic config for Talons; more config is done in the initMotionProfile method
    basicTalonConfig.nominalOutputForward = 0.0;
    basicTalonConfig.nominalOutputReverse = 0.0;
    basicTalonConfig.peakOutputForward = 1.0;
    basicTalonConfig.peakOutputReverse = -1.0;
    basicTalonConfig.continuousCurrentLimit = 37;
    basicTalonConfig.peakCurrentLimit = 50;
    basicTalonConfig.peakCurrentDuration = 100;
    basicTalonConfig.voltageCompSaturation = 12.2;

    // Applies config to all Talons; more config is done in the initMotionProfile
    // method
    fr.configAllSettings(basicTalonConfig);
    fl.configAllSettings(basicTalonConfig);
    mr.configAllSettings(basicTalonConfig);
    ml.configAllSettings(basicTalonConfig);
    br.configAllSettings(basicTalonConfig);
    bl.configAllSettings(basicTalonConfig);

    fl.setInverted(true);
    ml.setInverted(true);

    // Back motors must be reversed because of the gear box
    fr.setInverted(true);
    bl.setInverted(false);

    initMotionProfile();

  }

  public void initMotionProfile()
  {
    TalonSRXConfiguration motionProfileConfig = new TalonSRXConfiguration();
    // Include the basic config aswell because we are going to writing over the
    // already completed talon config.
    motionProfileConfig.nominalOutputForward = 0.0;
    motionProfileConfig.nominalOutputReverse = 0.0;
    motionProfileConfig.peakOutputForward = 1.0;
    motionProfileConfig.peakOutputReverse = -1.0;
    motionProfileConfig.continuousCurrentLimit = 37;
    motionProfileConfig.peakCurrentLimit = 50;
    motionProfileConfig.peakCurrentDuration = 100;
    motionProfileConfig.voltageCompSaturation = 12.2;
    /* -------------- config the master specific settings ----------------- */
    /* remote 0 will capture Pigeon IMU */
    motionProfileConfig.remoteFilter0.remoteSensorDeviceID = pigeon.getDeviceID();
    motionProfileConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw;
    /* remote 1 will capture the quad encoder on left talon */
    motionProfileConfig.remoteFilter1.remoteSensorDeviceID = fl.getDeviceID();
    motionProfileConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.TalonSRX_SelectedSensor;
    /*
     * drive-position is our local quad minus left-talon's selected sens. depending
     * on sensor orientation, it could be the sum instead
     */
    motionProfileConfig.diff0Term = FeedbackDevice.QuadEncoder;
    motionProfileConfig.diff1Term = FeedbackDevice.RemoteSensor1;
    motionProfileConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorDifference;
    motionProfileConfig.primaryPID.selectedFeedbackCoefficient = 0.5; /*
                                                                       * divide by 2 so we servo sensor-average, intead
                                                                       * of sum
                                                                       */
    /* turn position will come from the pigeon */
    motionProfileConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor0;
    /* rest of the configs */
    motionProfileConfig.neutralDeadband = RobotMap.kNeutralDeadband; /* 0.1 % super small for best low-speed control */
    motionProfileConfig.slot0.kF = RobotMap.kGains_MotProf.kF;
    motionProfileConfig.slot0.kP = RobotMap.kGains_MotProf.kP;
    motionProfileConfig.slot0.kI = RobotMap.kGains_MotProf.kI;
    motionProfileConfig.slot0.kD = RobotMap.kGains_MotProf.kD;
    motionProfileConfig.slot0.integralZone = (int) RobotMap.kGains_MotProf.kIzone;
    motionProfileConfig.slot0.closedLoopPeakOutput = RobotMap.kGains_MotProf.kPeakOutput;
    // motionProfileConfig.slot0.allowableClosedloopError // leave default
    // motionProfileConfig.slot0.maxIntegralAccumulator; // leave default
    // motionProfileConfig.slot0.closedLoopPeriod; // leave default
    motionProfileConfig.slot1.kF = RobotMap.kGains_MotProf.kF;
    motionProfileConfig.slot1.kP = RobotMap.kGains_MotProf.kP;
    motionProfileConfig.slot1.kI = RobotMap.kGains_MotProf.kI;
    motionProfileConfig.slot1.kD = RobotMap.kGains_MotProf.kD;
    motionProfileConfig.slot1.integralZone = (int) RobotMap.kGains_MotProf.kIzone;
    motionProfileConfig.slot1.closedLoopPeakOutput = RobotMap.kGains_MotProf.kPeakOutput;
    // motionProfileConfig.slot1.allowableClosedloopError // leave default
    // motionProfileConfig.slot1.maxIntegralAccumulator; // leave default
    // motionProfileConfig.slot1.closedLoopPeriod; // leave default
    fr.configAllSettings(motionProfileConfig);

    // ToDo: Configure motor sensor phase.

    /* pick the sensor phase and desired direction */
    fr.setSensorPhase(true);

    /* speed up the target polling for PID[0] and PID-aux[1] */
    fr.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 10); /* plotthread is polling aux-pid-sensor-pos */
    fr.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10);
    fr.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 10);
    fr.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 10);
    fr.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 10);

    fr.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10, 10);
    fl.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10, 10);
  }

  public void startMotionProfile(BufferedTrajectoryPointStream buffer)
  {
    fr.startMotionProfile(buffer, 10, ControlMode.MotionProfileArc);
  }

  public boolean isMotionProfileFinished()
  {
    return fr.isMotionProfileFinished();
  }

  public void driveArcade(double speed, double turn)
  {
    drive.driveArcade(speed, turn);
  }

  @Override
  public void initDefaultCommand()
  {
    setDefaultCommand(new DriveCommand());
  }

}
