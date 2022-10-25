// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;



import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;

public class ugabugacizgigecamaintake extends CommandBase {
  /** Creates a new ugabugacizgigec. */
  DriveBase m_drive;
  Intake m_intake;
  Timer timer = new Timer();
  public ugabugacizgigecamaintake(DriveBase drive, Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = drive;
    m_intake = intake;
    addRequirements(drive, intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.resetEncoder();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_drive.getDistance()<=150){
      m_drive.arcadeDrive(0.5, 0);
      m_intake.intakeRun(-0.7);
      System.out.println(m_drive.getDistance());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(timer.get()>=5){
      return true;
    }
    return false;
  }
}
