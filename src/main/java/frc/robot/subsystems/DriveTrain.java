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
import com.ctre.phoenix.ParamEnum;

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
    // Initiation of Drive Talons
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

    fl.setSensorPhase(true);
    fr.setSensorPhase(true);
    pigeon.setYaw(0);
    fl.setSelectedSensorPosition(0);
    fr.setSelectedSensorPosition(0);

    fr.getSensorCollection().setPulseWidthPosition(0, 10);
    fr.getSensorCollection().setQuadraturePosition(0, 10);

    fl.setNeutralMode(NeutralMode.Brake);
    fr.setNeutralMode(NeutralMode.Brake);

  }

  public void initMotionProfile()
  {
    fr.set(ControlMode.PercentOutput, 0);

    // ------------ talons -----------------//

    // ------------ setup filters -----------------//
    /* other side is quad */
    fl.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, RobotMap.PID_PRIMARY, RobotMap.kTimeoutMs);

    /* Remote 0 will be the other side's Talon */
    fr.configRemoteFeedbackFilter(fl.getDeviceID(), RemoteSensorSource.TalonSRX_SelectedSensor, RobotMap.REMOTE_0, RobotMap.kTimeoutMs);
    /* Remote 1 will be a pigeon */
    fr.configRemoteFeedbackFilter(pigeon.getDeviceID(), RemoteSensorSource.Pigeon_Yaw, RobotMap.REMOTE_1, RobotMap.kTimeoutMs);
    /* setup sum and difference signals */
    fr.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, RobotMap.kTimeoutMs);
    fr.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder, RobotMap.kTimeoutMs);
    fr.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor0, RobotMap.kTimeoutMs);
    fr.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.QuadEncoder, RobotMap.kTimeoutMs);
    /* select sum for distance(0), different for turn(1) */
    fr.configSelectedFeedbackSensor(FeedbackDevice.SensorSum, RobotMap.PID_PRIMARY, RobotMap.kTimeoutMs);

    if (RobotMap.kHeadingSensorChoice == 0)
    {

      fr.configSelectedFeedbackSensor(FeedbackDevice.SensorDifference, RobotMap.PID_TURN, RobotMap.kTimeoutMs);

      /* do not scale down the primary sensor (distance) */
      fr.configSelectedFeedbackCoefficient(1, RobotMap.PID_PRIMARY, RobotMap.kTimeoutMs);

      /*
       * scale empirically measured units to 3600units, this gives us - 0.1 deg resolution - scales to human-readable units - keeps target away from ovefrlow (12bit) Heading units should be scaled to ~4000 per 360 deg, due to the following
       * limitations... - Target param for aux PID1 is 18bits with a range of [-131072,+131072] units. - Target for aux PID1 in motion profile is 14bits with a range of [-8192,+8192] units. ... so at 3600 units per 360', that ensures 0.1
       * deg precision in firmware closed-loop and motion profile trajectory points can range +-2 rotations.
       */
      fr.configSelectedFeedbackCoefficient(RobotMap.kTurnTravelUnitsPerRotation / RobotMap.kEncoderUnitsPerRotation, RobotMap.PID_TURN, RobotMap.kTimeoutMs);
    }
    else
    {

      /*
       * do not scale down the primary sensor (distance). If selected sensor is going to be a sensorSum user can pass 0.5 to produce an average.
       */
      fr.configSelectedFeedbackCoefficient(1.0, RobotMap.PID_PRIMARY, RobotMap.kTimeoutMs);

      fr.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, RobotMap.PID_TURN, RobotMap.kTimeoutMs);

      fr.configSelectedFeedbackCoefficient(RobotMap.kTurnTravelUnitsPerRotation / RobotMap.kPigeonUnitsPerRotation, RobotMap.PID_TURN, RobotMap.kTimeoutMs);
    }

    // ------------ telemetry-----------------//
    fr.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, RobotMap.kTimeoutMs);
    fr.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, RobotMap.kTimeoutMs);
    fr.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, RobotMap.kTimeoutMs);
    fr.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20, RobotMap.kTimeoutMs);
    /* speed up the left since we are polling it's sensor */
    fl.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, RobotMap.kTimeoutMs);

    fl.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);
    fr.configNeutralDeadband(RobotMap.kNeutralDeadband, RobotMap.kTimeoutMs);

    fr.configMotionAcceleration(1000, RobotMap.kTimeoutMs);
    fr.configMotionCruiseVelocity(1000, RobotMap.kTimeoutMs);

    /*
     * max out the peak output (for all modes). However you can limit the output of a given PID object with configClosedLoopPeakOutput().
     */
    fl.configPeakOutputForward(+1.0, RobotMap.kTimeoutMs);
    fl.configPeakOutputReverse(-1.0, RobotMap.kTimeoutMs);
    fr.configPeakOutputForward(+1.0, RobotMap.kTimeoutMs);
    fr.configPeakOutputReverse(-1.0, RobotMap.kTimeoutMs);

    /* distance servo */
    fr.config_kP(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kP, RobotMap.kTimeoutMs);
    fr.config_kI(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kI, RobotMap.kTimeoutMs);
    fr.config_kD(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kD, RobotMap.kTimeoutMs);
    fr.config_kF(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kF, RobotMap.kTimeoutMs);
    fr.config_IntegralZone(RobotMap.kSlot_Distanc, (int) RobotMap.kGains_Distanc.kIzone, RobotMap.kTimeoutMs);
    fr.configClosedLoopPeakOutput(RobotMap.kSlot_Distanc, RobotMap.kGains_Distanc.kPeakOutput, RobotMap.kTimeoutMs);

    /* turn servo */
    fr.config_kP(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kP, RobotMap.kTimeoutMs);
    fr.config_kI(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kI, RobotMap.kTimeoutMs);
    fr.config_kD(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kD, RobotMap.kTimeoutMs);
    fr.config_kF(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kF, RobotMap.kTimeoutMs);
    fr.config_IntegralZone(RobotMap.kSlot_Turning, (int) RobotMap.kGains_Turning.kIzone, RobotMap.kTimeoutMs);
    fr.configClosedLoopPeakOutput(RobotMap.kSlot_Turning, RobotMap.kGains_Turning.kPeakOutput, RobotMap.kTimeoutMs);

    /* magic servo */
    fr.config_kP(RobotMap.kSlot_MotProf, RobotMap.kGains_MotProf.kP, RobotMap.kTimeoutMs);
    fr.config_kI(RobotMap.kSlot_MotProf, RobotMap.kGains_MotProf.kI, RobotMap.kTimeoutMs);
    fr.config_kD(RobotMap.kSlot_MotProf, RobotMap.kGains_MotProf.kD, RobotMap.kTimeoutMs);
    fr.config_kF(RobotMap.kSlot_MotProf, RobotMap.kGains_MotProf.kF, RobotMap.kTimeoutMs);
    fr.config_IntegralZone(RobotMap.kSlot_MotProf, (int) RobotMap.kGains_MotProf.kIzone, RobotMap.kTimeoutMs);
    fr.configClosedLoopPeakOutput(RobotMap.kSlot_MotProf, RobotMap.kGains_MotProf.kPeakOutput, RobotMap.kTimeoutMs);

    /* velocity servo */
    fr.config_kP(RobotMap.kSlot_Velocit, RobotMap.kGains_Velocit.kP, RobotMap.kTimeoutMs);
    fr.config_kI(RobotMap.kSlot_Velocit, RobotMap.kGains_Velocit.kI, RobotMap.kTimeoutMs);
    fr.config_kD(RobotMap.kSlot_Velocit, RobotMap.kGains_Velocit.kD, RobotMap.kTimeoutMs);
    fr.config_kF(RobotMap.kSlot_Velocit, RobotMap.kGains_Velocit.kF, RobotMap.kTimeoutMs);
    fr.config_IntegralZone(RobotMap.kSlot_Velocit, (int) RobotMap.kGains_Velocit.kIzone, RobotMap.kTimeoutMs);
    fr.configClosedLoopPeakOutput(RobotMap.kSlot_Velocit, RobotMap.kGains_Velocit.kPeakOutput, RobotMap.kTimeoutMs);

    fl.setNeutralMode(NeutralMode.Brake);
    fr.setNeutralMode(NeutralMode.Brake);

    /*
     * 1ms per loop. PID loop can be slowed down if need be. For example, - if sensor updates are too slow - sensor deltas are very small per update, so derivative error never gets large enough to be useful. - sensor movement is very slow
     * causing the derivative error to be near zero.
     */
    int closedLoopTimeMs = 1;
    fr.configSetParameter(ParamEnum.ePIDLoopPeriod, closedLoopTimeMs, 0x00, RobotMap.PID_PRIMARY, RobotMap.kTimeoutMs);
    fr.configSetParameter(ParamEnum.ePIDLoopPeriod, closedLoopTimeMs, 0x00, RobotMap.PID_TURN, RobotMap.kTimeoutMs);

    /**
     * false means talon's local output is PID0 + PID1, and other side Talon is PID0 - PID1 true means talon's local output is PID0 - PID1, and other side Talon is PID0 + PID1
     */
    fr.configAuxPIDPolarity(false, RobotMap.kTimeoutMs);

    zeroEncoders();
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
