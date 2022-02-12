// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  VictorSPX motor= new VictorSPX(2);
  Encoder enc= new Encoder (ShooterConstants.encoderPort[0],ShooterConstants.encoderPort[1]); //DEĞERLERI DÜZELT KOD PATLAMASIN DİYE KOYDUM
  
  public Shooter() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    enc.reset();
  }

  public void getShooterSpeed(){
    
  }
}
