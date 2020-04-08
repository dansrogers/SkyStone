package teamcode.test.AdvancedOdometry.DanielInterpretation;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import teamcode.common.AbstractOpMode;
import teamcode.test.AdvancedOdometry.Point;

interface IMU {
        Acceleration getAcceleration();
        Velocity getVelocity();
        Position getPosition();
        AngularVelocity getAngularVelocity();
        Orientation getAngularOrientation();
}

class RealIMU implements IMU {
    
}

class FakeIMU implements IMU {
}

public class imuUpdater {

    RobotState currentState;

    IMU imu;
    public imuUpdater(IMU imu){
        this.imu = imu;
        currentState = new RobotState(System.currentTimeMillis(), new Point(0,0), new Point(0,0), 0.0, 0.0, new Point(0.,0), 0);
        new Thread(){
            public void run(){
                while(AbstractOpMode.currentOpMode().opModeIsActive()){
                    update();
                }
            }
        }.start();

    }

    //reads the current state and uses the previous state to determine change in values, assuming linear change in acceleration
    private void update(){
        Acceleration current = imu.getAcceleration();
        Velocity currentVel = imu.getVelocity();
        Position pos = imu.getPosition();
        AngularVelocity currentAngVel = imu.getAngularVelocity();
        Orientation currentAngPos = imu.getAngularOrientation();
        RobotState newState = new RobotState(System.currentTimeMillis(), new Point(currentVel.xVeloc, currentVel.yVeloc), new Point(current.xAccel, current.yAccel), 0,  currentAngVel.zRotationRate, new Point(pos.x, pos.y), currentAngPos.thirdAngle);
        currentState = newState;
    }
}
