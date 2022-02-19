// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;
import frc.robot.Constants.PIDValues;

public class Turn extends CommandBase {
    private final DriveBase m_drive;
    private final double m_setpoint;
    private final PIDController anglepid = new PIDController(PIDValues.TurnkP, PIDValues.TurnkI, PIDValues.TurnkD);
    public Turn(DriveBase drivesystem, double setPoint) {
        // Use addRequirements() here to declare subsystem dependencies.
        m_drive = drivesystem;
        m_setpoint = setPoint;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_drive.resetGyro();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
       double rotation = anglepid.calculate(m_drive.getAngle(), m_setpoint);
       m_drive.arcadeDrive(0, rotation);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
