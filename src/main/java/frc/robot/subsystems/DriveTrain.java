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

import frc.robot.RobotMap;
import frc.robot.motionprofiling.*;
import frc.robot.utilities.*;

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

    TalonSRXConfiguration MotionConfig = new TalonSRXConfiguration();

    MotionConfig.nominalOutputForward = 0.0;
    MotionConfig.nominalOutputReverse = 0.0;
    MotionConfig.peakOutputForward = 1.0;
    MotionConfig.peakOutputReverse = -1.0;
    MotionConfig.continuousCurrentLimit = 37;
    MotionConfig.peakCurrentLimit = 50;
    MotionConfig.peakCurrentDuration = 100;
    MotionConfig.voltageCompSaturation = 12.2;

    /* -------------- config the master specific settings ----------------- */
    /* remote 0 will capture Pigeon IMU */
    MotionConfig.remoteFilter0.remoteSensorDeviceID = pigeon.getDeviceID();
    MotionConfig.remoteFilter0.remoteSensorSource = RemoteSensorSource.Pigeon_Yaw;
    /* remote 1 will capture the quad encoder on left talon */
    MotionConfig.remoteFilter1.remoteSensorDeviceID = fl.getDeviceID();
    MotionConfig.remoteFilter1.remoteSensorSource = RemoteSensorSource.TalonSRX_SelectedSensor;
    /*
     * drive-position is our local quad minus left-talon's selected sens. depending
     * on sensor orientation, it could be the sum instead
     */
    MotionConfig.diff0Term = FeedbackDevice.QuadEncoder;
    MotionConfig.diff1Term = FeedbackDevice.RemoteSensor1;
    MotionConfig.primaryPID.selectedFeedbackSensor = FeedbackDevice.SensorDifference;
    MotionConfig.primaryPID.selectedFeedbackCoefficient = 0.5; /*
                                                                * divide by 2 so we servo sensor-average, intead of sum
                                                                */
    /* turn position will come from the pigeon */
    MotionConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor0;
    /* rest of the configs */
    MotionConfig.neutralDeadband = Constants.kNeutralDeadband; /* 0.1 % super small for best low-speed control */
    MotionConfig.slot0.kF = Constants.kGains_MotProf.kF;
    MotionConfig.slot0.kP = Constants.kGains_MotProf.kP;
    MotionConfig.slot0.kI = Constants.kGains_MotProf.kI;
    MotionConfig.slot0.kD = Constants.kGains_MotProf.kD;
    MotionConfig.slot0.integralZone = (int) Constants.kGains_MotProf.kIzone;
    MotionConfig.slot0.closedLoopPeakOutput = Constants.kGains_MotProf.kPeakOutput;

    MotionConfig.slot1.kF = Constants.kGains_MotProfAngle.kF;
    MotionConfig.slot1.kP = Constants.kGains_MotProfAngle.kP;
    MotionConfig.slot1.kI = Constants.kGains_MotProfAngle.kI;
    MotionConfig.slot1.kD = Constants.kGains_MotProfAngle.kD;
    MotionConfig.slot1.integralZone = (int) Constants.kGains_MotProfAngle.kIzone;
    MotionConfig.slot1.closedLoopPeakOutput = Constants.kGains_MotProfAngle.kPeakOutput;

    fr.configAllSettings(MotionConfig);

    /* speed up the target polling for PID[0] and PID-aux[1] */
    fr.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 10); /* plotthread is polling aux-pid-sensor-pos */
    fr.setStatusFramePeriod(StatusFrame.Status_10_Targets, 10);
    fr.setStatusFramePeriod(StatusFrame.Status_17_Targets1, 10);

    // The left side must also be inverted so that we can drive forward with two
    bl.setInverted(true);
    ml.setInverted(true);

    // Back motors must be reversed because of the gear box
    fr.setInverted(true);
    fl.setInverted(false);

    // initMotionProfile();

    ml.follow(fl);
    bl.follow(fl);

    mr.follow(fr);
    br.follow(fr);

    fl.setSensorPhase(false);
    fr.setSensorPhase(true);
    pigeon.setYaw(0);
    fl.setSelectedSensorPosition(0);
    fr.setSelectedSensorPosition(0);

    zeroEncoders();

    fl.setNeutralMode(NeutralMode.Brake);
    fr.setNeutralMode(NeutralMode.Brake);

  }

  public void startMotionProfile(BufferedTrajectoryPointStream buffer)
  {
    fr.startMotionProfile(buffer, 10, ControlMode.MotionProfileArc);
    fl.follow(fr, FollowerType.AuxOutput1);
  }

  public boolean isMotionProfileFinished()
  {
    return fr.isMotionProfileFinished();
  }

  public void driveArcade(double speed, double turn)
  {
    drive.driveArcade(speed, turn);
  }

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

  @Override
  public void initDefaultCommand()
  {
    setDefaultCommand(new DriveCommand());
  }

}
