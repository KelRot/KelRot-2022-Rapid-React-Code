// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

public class DriveDistance extends CommandBase {
  /** Creates a new DriveDistance. */
  DriveBase m_drive;
  double m_setpoint;
  double drivekP;
  double drivekI;
  double drivekD;
  PIDController drivepid;
  public DriveDistance(DriveBase drive, double setpoint) {
    m_drive= drive;
    m_setpoint = setpoint;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.resetEncoder();
    drivekP = Preferences.getDouble("drivekP", 0); 
    drivekI = Preferences.getDouble("drivekI", 0); 
    drivekD = Preferences.getDouble("drivekD", 0); 
    drivepid= new PIDController(drivekP, 0, drivekD);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double dist = drivepid.calculate(m_drive.getDistance(),m_setpoint);
    m_drive.arcadeDrive(dist, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
