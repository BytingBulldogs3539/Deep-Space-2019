/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

/**
 * Add your docs here.
 */
public class ElevatorSetpointCommand extends InstantCommand {
  /**
   * Add your docs here.
   */
  double inches =0;
  public ElevatorSetpointCommand(){
    super();
    requires(Robot.elevator);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    inches = inches;
  }
  public ElevatorSetpointCommand(double inch) {
    super();
    requires(Robot.elevator);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    inches = inch;
    System.out.println("set inches to "+ inch);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.elevator.setHeightInches(inches);
  }

}
