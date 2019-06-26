/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.autoncommands.*;

public class DanHatchSolenoidsCommand extends CommandGroup {
  /**
   * Add your docs here.
   */
  public DanHatchSolenoidsCommand(boolean hatchstate,boolean beakstate) {
    {
      
    
        addSequential(new HatchExtendActuateCommand(hatchstate));
        addSequential(new PanelPlacementCommand(beakstate));
       // addSequential(new ExtensionActuateCommand(state));
  
  
    }
   
    
  }
}
