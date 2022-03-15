// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class autoIntakeRotate extends CommandBase {
  /** Creates a new autoIntakeRotate. */
  Intake m_intake;
  Timer m_timer;
  public autoIntakeRotate(Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake= intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intake.rotateIntake();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_timer.get()>=0.5){
      return true;
    }
    else{
      return false;
    }
    
  }
}
