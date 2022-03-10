// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants{
        public static final int solOn= 4;
        public static final int solArka= 1;
    
        public static final int sagOn= 3;
        public static final int sagArka= 2;

        public static final int encoderPorta= 0;
        public static final int encoderPortb= 1;

    }

    public static final class PIDValues{
        public static final double TurnkP = 0.0017;
        public static final double TurnkI = 0;
        public static final double TurnkD = 0;

        public static final double DrivekP = 0;
        public static final double DrivekI = 0;
        public static final double DrivekD = 0;
    }

    public static final class ShooterConstants{
        public static final int encoderPort1[] = {4,5};
        public static final int encoderPort2[] = {6,7};

        public static final int motor1 = 1;
        public static final int motor2 = 2;
    }

    public static final class FeederConstants{
        public static final int motor = 3;  
    }

    public static final class VisionConstants{
        public static final String cameraName= "Microsoft_LifeCam_HD-3000";
    }

    public static final class ClimberConstants{
        public static final int motor1 = 6;
        public static final int motor2 = 7;
        public static final int motorrotation= 4;
    }

}
