package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import frc.robot.Constants.PIDValues;
import frc.robot.subsystems.DriveBase;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class TrackBall extends CommandBase {
    private final DriveBase m_drive;
    double alignkP;
    double alignkI;
    double alignkD;
    PIDController anglepid;

    public NetworkTableEntry team_color;
    public NetworkTableEntry ball_yaw_angle;
    public NetworkTableEntry target_ball;
    public NetworkTableEntry ball_radius;
    public TrackBall(DriveBase subSystem){
        m_drive = subSystem;
        addRequirements(subSystem);
    }

    public void Connect2NetworkTables(){
        NetworkTableInstance inst =  NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("SmartDashboard");
        
        team_color = table.getEntry("team_color");
        ball_yaw_angle = table.getEntry("ball_yaw_angle");
        ball_radius = table.getEntry("ball_radius");
        target_ball = table.getEntry("target_ball");
        inst.startClientTeam(5655);
    }

    @Override
    public void initialize(){
        m_drive.resetGyro();
        alignkP = Preferences.getDouble("alignkP", 0);
        alignkI = Preferences.getDouble("alignkI", 0);
        alignkD = Preferences.getDouble("alignkD", 0);
        anglepid= new PIDController(alignkP, 0, alignkD);

    }

    @Override
    public void execute(){
        double rotation = 0;
        double yaw_angle = ball_yaw_angle.getDouble(0);
        double radius = ball_radius.getDouble(0);
        var targetBall = target_ball.getDouble(0);
        if(targetBall == 1){
            if(Math.abs(anglepid.getPositionError())<=3){
                anglepid.setI(alignkI);
              }
            rotation= anglepid.calculate(m_drive.getAngle(), yaw_angle + m_drive.getAngle());
            m_drive.arcadeDrive(0, rotation);
            System.out.println("Tracking the ball | Yaw Angle : " + yaw_angle + " | Ball Radius: " + radius);
            SmartDashboard.putNumber("ball_yaw_angle", yaw_angle);
        } else {
            System.out.println("No balls found!");
        }
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished(){
        return false;
    }
}