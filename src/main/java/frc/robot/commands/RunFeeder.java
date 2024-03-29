// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FeederSystem;

public class RunFeeder extends CommandBase {
  private final FeederSystem m_feeder;
  private final double m_output;
  Timer timer= new Timer();
  public RunFeeder(FeederSystem feeder, double output) {
    m_feeder= feeder;
    m_output= output;
    addRequirements(feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_feeder.feedBall(m_output);
    System.out.println(m_feeder.ballFed());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_feeder.feedBall(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(timer.get()>=7 || m_feeder.ballFed()){
      return true;
    }
    else{
      return false;
    }
  }

}
