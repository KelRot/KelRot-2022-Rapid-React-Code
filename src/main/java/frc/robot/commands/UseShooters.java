// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class UseShooters extends CommandBase {
  
  double[] encValues;
  private final Shooter m_Shooter;
  private final double m_output;
  public UseShooters(Shooter shootersystem, double output) {
    m_Shooter= shootersystem;
    m_output= output;
    addRequirements(shootersystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Shooter.resetEncoders();
    encValues= m_Shooter.getEncoderRate();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Shooter.useShooters(m_output);
    m_Shooter.encoderTest();
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
