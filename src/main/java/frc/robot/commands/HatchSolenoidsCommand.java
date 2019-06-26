/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.autoncommands.*;

public class HatchSolenoidsCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public HatchSolenoidsCommand(boolean state) {
    {
    if(!state){
        addSequential(new HatchExtendActuateCommand(state));
        addSequential((new AutonWait(.5)));
       // addSequential(new ExtensionActuateCommand(state));
        addSequential(new PanelPlacementCommand(state));
        

    }else{
        addSequential(new PanelPlacementCommand(state));
        addSequential(new ExtensionActuateCommand(state));
        addSequential((new AutonWait(.5)));
        //addSequential(new HatchExtendActuateCommand(state));
        // if (Robot.oi.panelLimitSwitch.get())
        //  {
        //   SmartDashboard.putBoolean("Hatch On", true);
        // } 
        // else
        //  {
        //   SmartDashboard.putBoolean("Hatch On", false);
        // }

    }
  
    }
   
    
  }
}
