// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FeederSystem;
import frc.robot.subsystems.Shooter;

public class coolishshooting extends CommandBase {
    /** Creates a new coolishshooting. */
    FeederSystem m_feeder;
    Shooter m_shooter;
    Timer timer = new Timer();
    public coolishshooting(FeederSystem feeder, Shooter shooter) {
        m_feeder= feeder;
        m_shooter = shooter;
        addRequirements(feeder, shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_shooter.useShooters(0.6);
        if(timer.get() >= 3){
            m_feeder.feedBall(-0.6);
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_feeder.feedBall(0);
        m_shooter.useShooters(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if(timer.get() >= 7){
            System.out.println("TOP ATIS BİTTİ");
            return true;
        }
        else{
            System.out.println("TOP ATIS DEVAM");
            return false;
        }
    }
}
