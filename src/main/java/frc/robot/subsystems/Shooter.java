  package frc.robot.subsystems;
  import com.ctre.phoenix.motorcontrol.ControlMode;
  import com.ctre.phoenix.motorcontrol.can.VictorSPX;

  import edu.wpi.first.math.controller.PIDController;
  import edu.wpi.first.math.controller.SimpleMotorFeedforward;
  import edu.wpi.first.wpilibj.Encoder;
  import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
  import edu.wpi.first.wpilibj2.command.SubsystemBase;
  import frc.robot.Constants.ShooterConstants;

  public class Shooter extends SubsystemBase {

    VictorSPX motor1= new VictorSPX(ShooterConstants.motor1);
    VictorSPX motor2 = new VictorSPX(ShooterConstants.motor2);

    Encoder enc1= new Encoder (ShooterConstants.encoderPort1[0], ShooterConstants.encoderPort1[1]); 
    Encoder enc2= new Encoder (ShooterConstants.encoderPort2[0], ShooterConstants.encoderPort2[1]);
    
    SimpleMotorFeedforward feedforward= new SimpleMotorFeedforward(0, 0);
    
    PIDController pcontroltop = new PIDController(0.0, 0.0, 0.0);
    PIDController pcontrolbottom = new PIDController(0.0, 0.0, 0.0);
    
    
    public Shooter() {
        enc1.setDistancePerPulse(1.0/400.0);
        enc2.setDistancePerPulse(1.0/400.0);

        motor1.configVoltageCompSaturation(12);
        motor1.enableVoltageCompensation(true);
        
        motor2.configVoltageCompSaturation(12);
        motor2.enableVoltageCompensation(true);
    }

    @Override
    public void periodic() {}

    public double[] getEncoderRPM(){
        double[] encoderValues = {enc1.getRate()*60, enc2.getRate()*60};
        return encoderValues;
    }

    public void controlShooter(double setpoint) {
        double topoutput = feedforward.calculate(setpoint) + pcontroltop.calculate(enc1.getRate(), setpoint);
        double bottomoutput = feedforward.calculate(setpoint) + pcontrolbottom.calculate(enc1.getRate(), setpoint);

        motor1.set(ControlMode.PercentOutput, topoutput);
        motor2.set(ControlMode.PercentOutput, bottomoutput);
    }

    public boolean atSetpoint(){
        return pcontroltop.atSetpoint() && pcontrolbottom.atSetpoint();
    }

    public void stopShooters(){
        motor1.set(ControlMode.PercentOutput,0);
        motor2.set(ControlMode.PercentOutput,0);
    }

    public void encoderTest(){
        SmartDashboard.putNumber("üst", enc1.getRate()*60);
        SmartDashboard.putNumber("alt", enc2.getRate()*60);
    }

    public void useShooters(double output){
        motor1.set(ControlMode.PercentOutput, output);
        motor2.set(ControlMode.PercentOutput, output);
        encoderTest();
        System.out.println(motor1.getMotorOutputVoltage() + " " + motor2.getMotorOutputVoltage());
    }

    public void resetEncoders(){
        enc1.reset();
        enc2.reset();
    }
  }
