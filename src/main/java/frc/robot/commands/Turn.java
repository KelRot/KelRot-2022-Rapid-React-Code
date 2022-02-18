// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.PIDValues;
import frc.robot.subsystems.DriveBase;

public class Turn extends CommandBase {
  /** Creates a new Turn. */
  private final DriveBase m_drive;
  private final double m_angle;
  PIDController gyropid = new PIDController(PIDValues.TurnkP , PIDValues.TurnkI , PIDValues.TurnkD);
  public Turn(DriveBase subsystem, double angle) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive= subsystem;
    m_angle= angle;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
   m_drive.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double rotation = gyropid.calculate(m_drive.getAngle(),m_angle);
    if(rotation>1){
      rotation=1;
    }
    else if(rotation<-1){
      rotation= -1;
    }
    m_drive.arcadeDrive(0, rotation);
    System.out.println(rotation);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
