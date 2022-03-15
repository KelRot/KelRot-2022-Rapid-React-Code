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
    /** Creates a new FeederSystem. */
    VictorSPX feedermotor= new VictorSPX(FeederConstants.motor);
    DigitalInput irsensor= new DigitalInput(FeederConstants.sensor);
    
    public FeederSystem() {

    }

    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }

    public void feedBall(double output){
        feedermotor.set(ControlMode.PercentOutput, output);
    }

    public boolean ballFed(){
        return !irsensor.get();
      }
  }
