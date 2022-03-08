// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FeederSystem;
import frc.robot.subsystems.Shooter;

public class CoolShooting extends CommandBase {
  FeederSystem m_feeder;
  Shooter m_shooter;
  double m_output;
  double m_setpoint;
  public CoolShooting(FeederSystem feeder, Shooter shooter, double output, double setpoint) {
    m_feeder= feeder;
    m_shooter= shooter;
    m_output= output;
    m_setpoint= setpoint;
    addRequirements(feeder,shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.useShooter(m_setpoint);
    if(m_shooter.atSetpoint()){
      m_feeder.feedBall(m_output);
    }
    if(m_feeder.ballFed()&& m_shooter.atSetpoint()){
      m_shooter.stopShooters();
    }
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
