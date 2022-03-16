// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class TestShooters extends CommandBase {
  double output;
  double[] encValues;
  private final Shooter m_Shooter;
  public TestShooters(Shooter shootersystem) {
    m_Shooter= shootersystem;
    addRequirements(shootersystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Shooter.resetEncoders();
    output = Preferences.getDouble("shooter rpm", 0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Shooter.useShooters(output);
    m_Shooter.encoderTest();
    encValues= m_Shooter.getEncoderRate();
    System.out.println("üst " + encValues[0] + " alt " + encValues[1]);

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
