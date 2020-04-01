package teamcode.test.AdvancedOdometry;

import android.util.Pair;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModuleIntf;
import com.qualcomm.hardware.lynx.commands.core.LynxGetBulkInputDataCommand;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import teamcode.common.AbstractOpMode;
import teamcode.common.Vector2D;
import teamcode.state.Constants;
import teamcode.test.revextensions2.ExpansionHubEx;
import teamcode.test.revextensions2.ExpansionHubMotor;
import teamcode.test.revextensions2.RevBulkData;

public class ArcPositionUpdate {
    private static final double ODOMETER_TICKS_TO_INCHES = 1.0 / 1102.0;
    private static final double HORIZONTAL_ODOMETER_ROTATION_OFFSET_TICKS = 0.4;
    private static final double VERTICAL_ODOMETER_TICKS_TO_RADIANS = 0.00006714153;
    private static final int LEFT_VERTICAL_ODOMETER_DIRECTION = -1;
    private static final int RIGHT_VERTICAL_ODOMETER_DIRECTION = -1;
    private static final int HORIZONTAL_ODOMETER_DIRECTION = -1;
    private static final double TICKS_PER_REV = 8192;
    private static final double WHEEL_RADIUS = 1.181;
    private static final double GEAR_RATIO = 1;
    private static final double CHASSIS_LENGTH = 16;
    private static final double LENGTH_TOLERANCE = encoderTicksToInches(100);

    /**
     * Whether or not this GPS should continue to update positions.
     */
    private boolean active;
    /**
     * Position stored in ticks. When exposed to external classes, it is represented in inches.
     */
    private Point currentPosition;
    /**
     * In radians, as a direction.
     */
    private double globalRads;
    private final ExpansionHubMotor leftVertical, rightVertical, horizontal;
    private final ExpansionHubEx hub1 , hub2;
    private RevBulkData data1, data2;
    private double previousOuterArcLength;
    private double previousInnerArcLength;
    private double previousHorizontalArcLength;
    private BNO055IMU imu;
    private double thetaGyro;
    /**
     * @param position in inches
     * @param globalRads in radians
     */
    public ArcPositionUpdate(HardwareMap hardwareMap, Point position, double globalRads) {
        position.dilate(1.0 / ODOMETER_TICKS_TO_INCHES);
        this.globalRads = globalRads;
        hub1 = hardwareMap.get(ExpansionHubEx.class,"Expansion Hub 1");
        hub2 = hardwareMap.get(ExpansionHubEx.class,"Expansion Hub 2");
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        leftVertical = (ExpansionHubMotor)hardwareMap.dcMotor.get(Constants.LEFT_VERTICAL_ODOMETER_NAME);
        rightVertical = (ExpansionHubMotor)hardwareMap.dcMotor.get(Constants.RIGHT_VERTICAL_ODOMETER_NAME);
        horizontal = (ExpansionHubMotor)hardwareMap.dcMotor.get(Constants.HORIZONTAL_ODOMETER_NAME);
        resetEncoders();
        active = true;
        Thread positionUpdater  = new Thread(){
            public void run(){
                while(active){
                    updateValues();
                }
            }

        };

        Thread positionCalculator = new Thread() {
            @Override
            public void run() {
                while (active) {
                    update();
                }
            }
        };
        positionUpdater.start();
        positionCalculator.start();
    }

    private synchronized void updateValues() {
        data1 = hub1.getBulkInputData();
        data2 = hub2.getBulkInputData();
        thetaGyro = imu.getAngularOrientation().firstAngle;
    }

    private void resetEncoders() {
        leftVertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightVertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setDirection(DcMotorSimple.Direction.REVERSE);
    }




    private synchronized void update() {
        double innerArcLength = encoderTicksToInches(data2.getMotorCurrentPosition(leftVertical));
        double outerArcLength = encoderTicksToInches(data1.getMotorCurrentPosition(rightVertical));
        double horizontalArcLength = encoderTicksToInches(data1.getMotorCurrentPosition(horizontal));
        double deltaOuterArcLength = outerArcLength - previousOuterArcLength;
        double deltaInnerArcLength = innerArcLength - previousInnerArcLength;
        double medianArc = (deltaOuterArcLength + deltaInnerArcLength) / 2.0; //median arc
        double deltaArcLength = deltaOuterArcLength - deltaInnerArcLength;
        double thetaOdo = deltaArcLength / CHASSIS_LENGTH;
        globalRads += thetaOdo;
        if(medianArc > LENGTH_TOLERANCE){
            //means the update does actually matter
            double radius = (innerArcLength - deltaArcLength) / (2.0 * thetaOdo);
            double deltaX = radius * Math.sin(medianArc / radius);
            currentPosition.x += deltaX;
            double deltaHorizontalArcLength = horizontalArcLength - previousHorizontalArcLength;
            if(deltaHorizontalArcLength > 0) {
                currentPosition.y += Math.sqrt(Math.pow(radius, 2) - Math.pow(deltaX, 2));
            }else{
                currentPosition.y -= Math.sqrt(Math.pow(radius, 2) - Math.pow(deltaX, 2));
            }
            //idk if my logic is correct here, future me or someone else check this up top (the Y Stuff)
        }
        previousInnerArcLength = innerArcLength;
        previousOuterArcLength = outerArcLength;
        previousHorizontalArcLength = horizontalArcLength;

    }


    public static double encoderTicksToInches(int ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public Point getCurrentPosition(){
        return currentPosition;
    }
    public double getGlobalRads(){
        return globalRads;
    }


}
