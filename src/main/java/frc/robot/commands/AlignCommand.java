
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.PIDValues;
import frc.robot.subsystems.DriveBase;
import org.photonvision.PhotonCamera;

public class AlignCommand extends CommandBase {
    private final DriveBase m_drive;
    private final PhotonCamera cam;
    private final PIDController anglepid= new PIDController(PIDValues.TurnkP , PIDValues.TurnkI , PIDValues.TurnkD);

    public AlignCommand(DriveBase subSystem, PhotonCamera camera) {
        m_drive = subSystem;
        cam = camera;
        addRequirements(subSystem);
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

            double rotation= anglepid.calculate(m_drive.getAngle(), result.getBestTarget().getYaw());
            m_drive.arcadeDrive(0, rotation);
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
