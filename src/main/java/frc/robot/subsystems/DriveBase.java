// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.ADIS16470_IMU.IMUAxis;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants.DriveConstants;

public class DriveBase extends SubsystemBase {
    ADIS16470_IMU gyro = new ADIS16470_IMU();
    Encoder enc = new Encoder(DriveConstants.encoderPortA, DriveConstants.encoderPortB, true);

    Talon leftFront = new Talon(DriveConstants.leftFront);
    Talon leftBack = new Talon(DriveConstants.leftBack);
    Talon rightFront = new Talon(DriveConstants.rightFront);
    Talon rightBack = new Talon(DriveConstants.rightBack);

    MotorControllerGroup _left = new MotorControllerGroup(leftFront, leftBack);
    MotorControllerGroup _right = new MotorControllerGroup(rightFront, rightBack);

    DifferentialDrive drive = new DifferentialDrive(_left, _right);

    public DriveBase() {
        rightFront.setInverted(true);
        rightBack.setInverted(true);
        gyro.setYawAxis(IMUAxis.kZ);
        enc.setDistancePerPulse((15.2 * Math.PI)/1024.0);
    }

    @Override
    public void periodic() {}

    public void curvatureDrive(Joystick js){
        drive.curvatureDrive(js.getRawAxis(1), js.getRawAxis(0), js.getRawButton(5));
    }

    public void arcadeDrive(double speed, double rotation){
        drive.arcadeDrive(speed, rotation);
    }

    public double getAngle(){
        return -gyro.getAngle();
    }

    public void resetGyro(){
        gyro.reset();
    }

    public double getDistance(){
        return enc.getDistance();
    }
    public void resetEncoder(){
        enc.reset();
    }
}
