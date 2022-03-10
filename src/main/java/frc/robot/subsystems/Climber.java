// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {
  /** Creates a new Climber. */
  CANSparkMax motor1 = new CANSparkMax(ClimberConstants.motor1, MotorType.kBrushed);
  CANSparkMax motor2 = new CANSparkMax(ClimberConstants.motor2, MotorType.kBrushed);
  VictorSPX motorrotation= new VictorSPX(ClimberConstants.motorrotation);
  public Climber() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void Climb(double output){
    motor1.set(output);
    motor2.set(output);
  }

  public void Rotate(double output){
    motorrotation.set(ControlMode.PercentOutput, output);
  }
}
