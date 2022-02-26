// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveBase;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Turn extends CommandBase {
  /** Creates a new Turn. */
  private final DriveBase m_drive;
  //private final double m_angle;

  double turnkP;
  double turnkI;
  double turnkD;
  double setpoint;
  PIDController gyropid;
  double totalerror;
  double integralrange = 1;
  Timer time;
  public Turn(DriveBase subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive= subsystem;
    //m_angle= angle;
    addRequirements(m_drive);//galiba bundan patladık

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
   m_drive.resetGyro();
   turnkP = Preferences.getDouble("turnkP", 0); 
   turnkI = Preferences.getDouble("turnkI", 0); 
   turnkD = Preferences.getDouble("turnkD", 0); 
   setpoint = Preferences.getDouble("setpoint", 180);
   gyropid= new PIDController(turnkP, 0, turnkD);
   //gyropid.setSetpoint(setpoint);
   gyropid.setTolerance(2, 0.1);
   gyropid.setIntegratorRange(-integralrange, integralrange);
   totalerror = 0;
   time.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Math.abs(gyropid.getPositionError())<=8){
      gyropid.setI(turnkI);
    }
    double rotation = gyropid.calculate(m_drive.getAngle() , setpoint);
    /*if(rotation>1){
      rotation=1;
    }
    else if(rotation<-1){
      rotation=-1;
    }
    if(gyropid.atSetpoint()){
      rotation=0;
    }
*/
    m_drive.arcadeDrive(0, rotation);
    totalerror = MathUtil.clamp(totalerror + gyropid.getPositionError() * gyropid.getPeriod() , -integralrange/ gyropid.getI() , integralrange / gyropid.getI());
    SmartDashboard.putNumber("gyro",m_drive.getAngle());
    System.out.println(m_drive.getAngle());
    SmartDashboard.putNumber("i output", totalerror*gyropid.getI());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("COMMAND END");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(time.get()>=3){
      return true;
    }
    else{
      return gyropid.atSetpoint();
    }
  }

}
