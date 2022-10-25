// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.photonvision.PhotonCamera;
import edu.wpi.first.util.net.PortForwarder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.FeederSystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants.VisionConstants;
import frc.robot.commands.TargetAlign;
import frc.robot.commands.Back;
import frc.robot.commands.CoolShooting;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.RunFeeder;
import frc.robot.commands.Turn;
import frc.robot.commands.coolishshooting;
import frc.robot.commands.PassLine;
import frc.robot.commands.TestShooters;
import frc.robot.commands.ugabugacizgigecamaintake;

public class RobotContainer {
  
    private final PhotonCamera camera = new PhotonCamera(VisionConstants.cameraName);
    private final DriveBase drive = new DriveBase();
    private final Shooter shooter = new Shooter();
    private final FeederSystem feeder = new FeederSystem();
    private final Intake intake = new Intake();
    private final Climber climber = new Climber();

    private final Joystick js = new Joystick(1);
    private final Joystick js2 = new Joystick(1);



    private final DriveCommand driveCommand = new DriveCommand(drive, js);
    private final TargetAlign align = new TargetAlign(drive, camera);
    private final TestShooters testShooters = new TestShooters(shooter);
    private final coolishshooting eehh = new coolishshooting(feeder, shooter);
    private final PassLine step2 = new PassLine(drive);
    private final RunFeeder teleopFeed = new RunFeeder(feeder, -0.6);
    private final RunFeeder autoFeed = new RunFeeder(feeder, -0.6);


    private final Turn turn180degrees = new Turn(drive);

    private final SequentialCommandGroup ball = new SequentialCommandGroup(
        eehh , step2
    );

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        drive.setDefaultCommand(driveCommand);
        PortForwarder.add(5800, "photonvision.local", 5800);  
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        
        new JoystickButton(js, Button.kX.value).whenPressed(turn180degrees);
        
        new JoystickButton(js, Button.kB.value).whenHeld(new InstantCommand( ()-> intake.intakeRun(0.7))).whenReleased(new InstantCommand( ()-> intake.intakeRun(0)));
        
        new JoystickButton(js, 3).whenHeld( new InstantCommand( ()-> feeder.feedBall(-0.8) ) ).whenReleased(new InstantCommand( ()-> feeder.feedBall(0)));//yukarı
        new JoystickButton(js, 4).whenHeld( new InstantCommand( ()-> feeder.feedBall(0.8) ) ).whenReleased(new InstantCommand( ()-> feeder.feedBall(0)));//aşağı
        
        new JoystickButton(js, Button.kA.value).whenHeld(new InstantCommand( ()-> intake.intakeRun(-0.7))).whenReleased(new InstantCommand( ()-> intake.intakeRun(0)));
        
        /* Hub Align */

        new JoystickButton(js, Button.kRightBumper.value).whenHeld(align);
        
        /* Teleop Feeder */

        new JoystickButton(js, Button.kRightBumper.value).whenPressed(teleopFeed);
        
        /*

        new JoystickButton(js, 1).whenHeld(testShooters).whenReleased(new InstantCommand(shooter::stopShooters));

        /* Climber */

        new JoystickButton(js, 5).whenHeld(new InstantCommand( ()-> climber.Rotate(0.6))).whenReleased(new InstantCommand( ()-> climber.Rotate(0)));
        new JoystickButton(js, 6).whenHeld(new InstantCommand( ()-> climber.Rotate(-0.6))).whenReleased(new InstantCommand( ()-> climber.Rotate(0)));
        new JoystickButton(js2, 5).whenHeld(new InstantCommand(() -> climber.Climb(0.8))).whenReleased(new InstantCommand( ()-> climber.Climb(0)));
        new JoystickButton(js2, 3).whenHeld(new InstantCommand(()->climber.Climb(-0.8))).whenReleased(new InstantCommand( ()-> climber.Climb(0)));

        /* Feeder */

        new JoystickButton(js2, 12).whenHeld( new InstantCommand( ()-> feeder.feedBall(-0.8) ) ).whenReleased(new InstantCommand( ()-> feeder.feedBall(0)));//yukarı
        new JoystickButton(js2, 11).whenHeld( new InstantCommand( ()-> feeder.feedBall(0.8) ) ).whenReleased(new InstantCommand( ()-> feeder.feedBall(0)));//aşağı

      
    }

  
    public Command getAutonomousCommand() {
      //an auto command will not run in autonomous
        return ball;
    }
}
