// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveBase;
import org.photonvision.PhotonCamera;
import frc.robot.Constants.PIDValues;
import frc.robot.Constants.VisionConstants;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AlignCommand extends CommandBase {
    private final PhotonCamera cam;
    private final DriveBase m_drive;
    private final PIDController anglepid = new PIDController(PIDValues.TurnkP, PIDValues.TurnkI, PIDValues.TurnkD);

    public AlignCommand(DriveBase subSystem, PhotonCamera camera) {
        m_drive = subSystem;
        cam = camera;
        addRequirements(subSystem);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        var result = cam.getLatestResult();
        if(result.hasTargets()){
            double rotation = anglepid.calculate(m_drive.getAngle(), result.getBestTarget().getYaw());
            //m_drive.arcadeDrive(0, rotation);
            System.out.println(rotation);
            System.out.println(m_drive.getAngle());
        }else{
            System.out.println("No targets found!");
        }
    }

    public double getDistanceToHub(){
        var result = cam.getLatestResult();
        double ans = -1;
        if(result.hasTargets()){
            ans = VisionConstants.dist / Math.tan(Math.toRadians(VisionConstants.cameraPitch + result.getBestTarget().getPitch()));
        }
        return ans;
    }

    @Override
    public boolean isFinished() {
        return false;
    } 

    @Override
    public void end(boolean interrupted) {}

}
