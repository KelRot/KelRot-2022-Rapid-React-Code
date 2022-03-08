package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.PIDValues;
import frc.robot.subsystems.DriveBase;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
public class TrackBall extends CommandBase {
    private final DriveBase m_drive;
    private final PIDController anglepid= new PIDController(PIDValues.BallkP , PIDValues.BallkI , PIDValues.BallkD);
    
    public NetworkTableEntry team_color;
    public NetworkTableEntry ball_yaw_angle;
    public NetworkTableEntry target_ball;
    public TrackBall(DriveBase subSystem){
        m_drive = subSystem;
        addRequirements(subSystem);
    }

    public void Connect2NetworkTables(){
        NetworkTableInstance inst =  NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("SmartDashboard");
        
        team_color = table.getEntry("team_color");
        ball_yaw_angle = table.getEntry("ball_yaw_angle");
        target_ball = table.getEntry("target_ball");
        inst.startClientTeam(5655);
    }

    @Override
    public void initialize(){
        m_drive.resetGyro();

    }

    @Override
    public void execute(){
        double yaw_angle = ball_yaw_angle.getDouble(0);
        var targetBall = target_ball.getDouble(0);
        if(targetBall == 0){
            double rotation= anglepid.calculate(m_drive.getAngle(), -yaw_angle);
            m_drive.arcadeDrive(0, rotation);
            System.out.println("Tracking the ball | Yaw Angle : " + yaw_angle);
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
