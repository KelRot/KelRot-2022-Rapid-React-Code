// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import org.photonvision.PhotonCamera;

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
import frc.robot.commands.Turn;
import frc.robot.commands.coolishshooting;
import frc.robot.commands.ugabugacizgigec;
import frc.robot.commands.TestShooters;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final PhotonCamera camera = new PhotonCamera(VisionConstants.cameraName);
  private final DriveBase drive = new DriveBase();
  private final Joystick js = new Joystick(0);
  private final Shooter shooter= new Shooter();
  private final FeederSystem feeder= new FeederSystem();
  private final Intake intake = new Intake();
  private final Climber climber= new Climber();
  private final Joystick js2= new Joystick(1);



  private final DriveCommand driveCommand = new DriveCommand(drive, js);
  private final TargetAlign align = new TargetAlign(drive, camera);
  private final TestShooters testShooters = new TestShooters(shooter);
  private final Back back = new Back(drive, intake, 120);
  private final TestShooters useShooters= new TestShooters(shooter);
  private final CoolShooting coolShooting= new CoolShooting(feeder, shooter, 0.7, 4000);
  private final DriveDistance worstCase = new DriveDistance(drive, 120);
  private final coolishshooting eehh = new coolishshooting(feeder, shooter);
  private final ugabugacizgigec step2 = new ugabugacizgigec(drive);


  private final Turn turn180degrees = new Turn(drive);


 /* private final SequentialCommandGroup two_balls = new SequentialCommandGroup(
        align,
        coolShooting,
        back,
        align,
        coolShooting
  );
*/
  /*private final SequentialCommandGroup worseCase = new SequentialCommandGroup(
      coolShooting,
      back
  );
*/

    private final SequentialCommandGroup ball = new SequentialCommandGroup(
        eehh, step2
    );

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drive.setDefaultCommand(driveCommand);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //new JoystickButton(js , Button.kY.value).whenPressed(align);
    //new JoystickButton(js , Button.kX.value).whenPressed(turn180degrees);
    new JoystickButton(js, Button.kB.value).whenHeld(new InstantCommand( ()-> intake.intakeRun(0.7))).whenReleased(new InstantCommand( ()-> intake.intakeRun(0)));
    new JoystickButton(js, Button.kX.value).whenHeld( new InstantCommand( ()-> feeder.feedBall(-0.7) ) ).whenReleased(new InstantCommand( ()-> feeder.feedBall(0)));
    new JoystickButton(js, Button.kY.value).whenHeld( new InstantCommand( ()-> feeder.feedBall(0.7) ) ).whenReleased(new InstantCommand( ()-> feeder.feedBall(0)));
    new JoystickButton(js, Button.kA.value).whenHeld(new InstantCommand( ()-> intake.intakeRun(-0.7))).whenReleased(new InstantCommand( ()-> intake.intakeRun(0)));
    new JoystickButton(js, Button.kRightBumper.value).whenHeld(align);
    
    new JoystickButton(js2, 1).whenHeld(testShooters).whenReleased(new InstantCommand( shooter::stopShooters));
    new JoystickButton(js2, 2).whenHeld(new InstantCommand(intake::rotateIntake)).whenReleased(new InstantCommand( ()-> intake.intakeRun(0)));
    new JoystickButton(js2, 5).whenHeld(new InstantCommand( ()-> climber.Rotate(0.8))).whenReleased(new InstantCommand( ()-> climber.Rotate(0)));
    new JoystickButton(js2, 3).whenHeld(new InstantCommand( ()-> climber.Rotate(-0.8))).whenReleased(new InstantCommand( ()-> climber.Rotate(0)));

   }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  
  public Command getAutonomousCommand() {
    //an auto command will not run in autonomous
    return ball;
  }
}
