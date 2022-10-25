// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FeederConstants;

public class FeederSystem extends SubsystemBase {
    VictorSPX feederMotor = new VictorSPX(FeederConstants.motor);
    DigitalInput irSensor = new DigitalInput(FeederConstants.sensor);
    
    public FeederSystem() {}

    @Override
    public void periodic(){}

    public void feedBall(double output){
        feederMotor.set(ControlMode.PercentOutput, output);
    }

    public boolean ballFed(){
        return !irSensor.get();
    }
  }
