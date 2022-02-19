// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

public class DriveCommand extends CommandBase {
    private final DriveBase m_drivebase;
    private final Joystick joystick;
    public DriveCommand(DriveBase subsystem, Joystick js) {
        m_drivebase = subsystem;
        joystick = js;
        addRequirements(subsystem);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    @Override
    public void initialize() {
        m_drivebase.resetGyro();
    }

    @Override
    public void execute() {
        m_drivebase.curvatureDrive(joystick);
        //System.out.println(m_drivebase.getAngle());
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}
