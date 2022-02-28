
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;
import org.photonvision.PhotonCamera;

public class AlignCommand extends CommandBase {
    private final DriveBase m_drive;
    private final PhotonCamera cam;
    double alignkP;
    double alignkI;
    double alignkD;
    double setpoint;
    PIDController anglepid;

    public AlignCommand(DriveBase subSystem, PhotonCamera camera) {
        m_drive = subSystem;
        cam = camera;
        addRequirements(subSystem);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
        m_drive.resetGyro();  
        alignkP = Preferences.getDouble("alignkP", 0); 
        alignkI = Preferences.getDouble("alignkI", 0); 
        alignkD = Preferences.getDouble("alignkD", 0); 
        setpoint = Preferences.getDouble("alignsetpoint", 30);
        anglepid= new PIDController(alignkP, 0, alignkD);
        //anglepid.setSetpoint(setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        double range=0;
        double rotation = 0;
        var result = cam.getLatestResult();
        if(result.hasTargets()){
            if(Math.abs(anglepid.getPositionError())<=3){
                anglepid.setI(alignkI);
              }
            rotation = anglepid.calculate(m_drive.getAngle(), result.getBestTarget().getYaw() + m_drive.getAngle());
            System.out.print("yaw:");
            System.out.println(result.getBestTarget().getYaw());
           
        }
        else {
            System.out.println("No targets found!");
            rotation = -0.5;
        }
     
        m_drive.arcadeDrive(0, rotation);
        SmartDashboard.putNumber("gyro", m_drive.getAngle());
        System.out.println(m_drive.getAngle());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
      //return anglepid.atSetpoint();
      return false;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {}

}
