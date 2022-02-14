// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants.DriveConstants;

public class DriveBase extends SubsystemBase {
    /** Creates a new DriveBase. */
    ADIS16470_IMU gyro= new ADIS16470_IMU();
    Talon frontLeft = new Talon(DriveConstants.solOn);
    Talon backLeft = new Talon(DriveConstants.solArka);
    Talon frontRight = new Talon(DriveConstants.sagOn);
    Talon backRight = new Talon(DriveConstants.sagArka);


    MotorControllerGroup left= new MotorControllerGroup(frontLeft, backLeft);
    MotorControllerGroup right= new MotorControllerGroup(frontRight, backRight);

    DifferentialDrive drivetrain = new DifferentialDrive(left, right);

    public DriveBase() {
        frontRight.setInverted(true);
        backRight.setInverted(true);
    }

    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }

    public void curvatureDrive(Joystick js){
        drivetrain.curvatureDrive(-js.getRawAxis(1),js.getRawAxis(4),js.getRawButton(5));
    }

    public double getAngle(){
        return gyro.getAngle();
    }

    public void resetGyro(){
        gyro.reset();
    }
}
