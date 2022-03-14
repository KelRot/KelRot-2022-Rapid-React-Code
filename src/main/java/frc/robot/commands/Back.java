// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;

public class Back extends CommandBase {
    private final DriveBase m_drive;
    double backP, backI, backD;
    PIDController backPID;
    Intake m_intake;
    double distanceToBack; //centimeters
    public Back(DriveBase drivo, Intake intako, double disttoback) {
        distanceToBack = disttoback;
        m_drive = drivo;
        m_intake = intako;
        backP = Preferences.getDouble("backP", 0);
        backI = Preferences.getDouble("backI", 0);
        backD = Preferences.getDouble("backD", 0);
        addRequirements(m_drive);
    }

    
    @Override
    public void initialize() {
        m_drive.resetEncoder();
    } 

    
    @Override
    public void execute() {
        double d = backPID.calculate(-m_drive.getDistance(), distanceToBack);
        m_drive.arcadeDrive(d, 0);
        if(Math.abs(-m_drive.getDistance() - distanceToBack) < distanceToBack*4/10){
            m_intake.intakeRun(0.7);
        }
        System.out.print("DISTANCE: ");
        System.out.println(m_drive.getDistance());
    }

    
    @Override
    public void end(boolean interrupted) {}

    
    @Override
    public boolean isFinished() {
        return false;
        //return backPID.atSetpoint();
        //until first ar is on
    }
}