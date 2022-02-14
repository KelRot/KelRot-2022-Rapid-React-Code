// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import javax.swing.text.StyledEditorKit;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.VisionSystem;
import org.photonvision.PhotonCamera;
import java.util.List;

public class AlignCommand extends CommandBase {
    private final VisionSystem m_vision;
    private final PhotonCamera cam;
    private final NetworkTableEntry teamColor;

    public AlignCommand(VisionSystem subSystem, PhotonCamera camera) {
        m_vision = subSystem;
        cam = camera;
        addRequirements(subSystem);
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("Team Color");
        teamColor= table.getEntry("team color");
        SendableChooser<String> team_selector = new SendableChooser<>();
        String red = "red";
        String blue = "red";
        team_selector.setDefaultOption("red", red);
        team_selector.addOption("red", red);
        team_selector.addOption("blue", blue);
        SmartDashboard.putData(team_selector);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        var result = cam.getLatestResult();
        
        
        if(result.hasTargets()){
            System.out.println("Target pos:/n");
            System.out.println(result.getBestTarget().getCorners().get(1).toString());
        }else{
            System.out.println("No targets found!");
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {}

}
