package teamcode.test.AdvancedOdometry.GlutenFreeKinematics;

import com.qualcomm.robotcore.util.Range;

import teamcode.test.AdvancedOdometry.MathFunctions;
import teamcode.test.AdvancedOdometry.MovementVars;

public class GFKinematicUpdateModel {
    /**
     * Creates a robot simulation
     */
    public GFKinematicUpdateModel(double worldXPosition, double worldYPosition, double worldAngle_rad){
        this.worldXPosition = worldXPosition;
        this.worldYPosition = worldYPosition;
        this.worldAngle_rad = worldAngle_rad;
        active = true;
        new Thread(){
            public void run(){
                while(active){
                    update();
                }
            }
        }.start();

    }

    boolean active;
    //the actual speed the robot is moving
    public static double xSpeed = 0;
    public static double ySpeed = 0;
    public static double turnSpeed = 0;

    public static double worldXPosition;
    public static double worldYPosition;
    public static double worldAngle_rad;

    public double getXPos(){
        return worldXPosition;
    }

    public double getYPos(){
        return worldYPosition;
    }


    public double getWorldAngle_rad() {
        return worldAngle_rad;
    }


    //last update time
    private long lastUpdateTime = 0;

    /**
     * Calculates the change in position of the robot
     */
    public void update(){
        //get the current time
        long currentTimeMillis = System.currentTimeMillis();
        //get the elapsed time
        double elapsedTime = (currentTimeMillis - lastUpdateTime)/1000.0;
        //remember the lastUpdateTime
        lastUpdateTime = currentTimeMillis;
        if(elapsedTime > 1){return;}

        //increment the positions
        double totalSpeed = Math.hypot(xSpeed,ySpeed);
        double angle = Math.atan2(ySpeed,xSpeed) - Math.toRadians(90);
        //System.out.println("angle: " + angle);
        double outputAngle = worldAngle_rad + angle;
        worldXPosition += totalSpeed * Math.cos(outputAngle) * elapsedTime * 1000 * 0.2;
        worldYPosition += totalSpeed * Math.sin(outputAngle) * elapsedTime * 1000 * 0.2;
        //System.out.println("after tan: " + worldAngle_rad);
        worldAngle_rad += MovementVars.movementTurn * elapsedTime * 20 / (2 * Math.PI);
        //System.out.println("after turn: " + worldAngle_rad);

        xSpeed += Range.clip((MovementVars.movementX - xSpeed)/0.2,-1,1) * elapsedTime;
        ySpeed += Range.clip((MovementVars.movementY - ySpeed)/0.2,-1,1) * elapsedTime;
        turnSpeed += Range.clip((MovementVars.movementTurn - turnSpeed)/0.2,-1,1) * elapsedTime;

        SpeedOmeter.yDistTraveled += ySpeed * elapsedTime * 1000;
        SpeedOmeter.xDistTraveled += xSpeed * elapsedTime * 1000;

        SpeedOmeter.update();

        worldAngle_rad = MathFunctions.angleWrap(worldAngle_rad);
        xSpeed *= 1.0 - (elapsedTime);
        ySpeed *= 1.0 - (elapsedTime);
        turnSpeed *= 1.0 - (elapsedTime);
    }


}
