// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Intake extends SubsystemBase {
    VictorSPX intakeMotor = new VictorSPX(IntakeConstants.motor); 
    VictorSP rotationMotor = new VictorSP(IntakeConstants.motorrotation);

    public Intake() {}

    @Override
    public void periodic() {}
    
    public void intakeRun(double output){
        intakeMotor.set(ControlMode.PercentOutput, -output);
    } 

    public void rotateIntake(){
        rotationMotor.set(-0.5);
    }
}