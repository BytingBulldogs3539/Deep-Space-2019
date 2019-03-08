/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autongroups;

import frc.robot.utilities.*;
import frc.robot.autoncommands.*;
import frc.robot.motionprofiling.MotionProfiling;
import frc.robot.motionprofiling.TestProfile;
import frc.robot.commands.*;
import frc.robot.*;

public class RightRocketRear extends MotionCommandGroup
{

  public RightRocketRear()
  {
    //addSequential(new AutonDrivePath("RightRocketRear.json", true));
    //addMotionProfile("RightRocketRear.json");
    addSequential(new TurretPositionCommandFin(90));
    addSequential(new AutonDrivePath("RightRocketRear.json", true));

   // setOnEvent("RightRocketRear.json", "T90", new TurretPositionCommandFin(90),Robot.drivetrain.fr);
//addSequential(new TurretPositionCommand(90));
   // addSequential(new AutonWait(.5));

    // //addSequential(new PanelPlacementCommand(false));
    // addSequential(new AutonWait(.2));
    // addSequential(new PanelArmActuateCommand(false));
    // addSequential(new AutonWait(.2));
    // addSequential(new PanelPlacementCommand(true));

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
