package teamcode.test.AdvancedOdometry.DanielInterpretation;

import teamcode.test.AdvancedOdometry.Point;

public class RobotState {
    long sampleTime;
    Point velocity;
    Point acceleration;
    double angularVelocity;
    double angularAcceleration;
    Point pos;
    double theta;

    public RobotState(long sampleTime, Point velocity, Point acceleration, double angularAcceleration, double angularVelocity, Point pos, double theta){
        this.sampleTime = sampleTime;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.angularAcceleration = angularAcceleration;
        this.angularVelocity = angularVelocity;
        this.pos = pos;
        this.theta = theta;
    }

}
