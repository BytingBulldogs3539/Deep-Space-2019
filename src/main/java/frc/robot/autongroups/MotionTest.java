/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autongroups;

import frc.robot.utilities.*;
import frc.robot.Robot;
import frc.robot.autoncommands.*;
import frc.robot.commands.*;

public class MotionTest extends MotionCommandGroup
{

  public MotionTest()
  {
    addSequential(new AutonDrivePath("AUSA.json", true, this));
    addMotionProfile("AUSA.json");
    setOnEvent("AUSA.json", "T90", new TurretPositionCommand(90), Robot.drivetrain.fr);
    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
