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
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants.VisionConstants;
import frc.robot.commands.AlignCommand;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.Turn;
import frc.robot.commands.UseShooters;

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

  private final DriveCommand driveCommand = new DriveCommand(drive, js);
  private final AlignCommand align = new AlignCommand(drive, camera);
  private final UseShooters useShooters= new UseShooters(shooter);

  private final Turn turn180degrees = new Turn(drive);

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
    new JoystickButton(js , Button.kY.value).whenPressed(align);
    new JoystickButton(js , Button.kX.value).whenPressed(turn180degrees);
    new JoystickButton(js, Button.kB.value).whenPressed(useShooters);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //an auto command will not run in autonomous
    return turn180degrees;
  }
}
