// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FeederSystem;
import frc.robot.subsystems.Shooter;

public class coolishshooting extends CommandBase {
  /** Creates a new coolishshooting. */
  FeederSystem m_feeder;
  Shooter m_shooter;
  Timer timer;
  public coolishshooting(FeederSystem feeder, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_feeder= feeder;
    m_shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    while(m_feeder.ballFed()){
      m_feeder.feedBall(0.5); //düzelt
    }
    m_shooter.useShooters(0.7);
    if(timer.get()>=5 && !m_feeder.ballFed()){
      m_feeder.feedBall(0.3);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_feeder.feedBall(0);
    m_shooter.useShooters(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
      if(timer.get()>=10){
        return true;
      }
      else{
        return false;
      }
  }
}
