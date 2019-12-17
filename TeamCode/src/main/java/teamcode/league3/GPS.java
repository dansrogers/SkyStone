package teamcode.league3;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Timer;
import java.util.TimerTask;

import teamcode.common.Coordinates;
import teamcode.common.Debug;
import teamcode.common.Vector2D;

/**
 * Always call shutdown() in onStop() of AbstractOpMode.
 */
public class GPS {

    /**
     * Whether or not this GPS should continue to update positions.
     */
    private boolean active;
    private Vector2D position;
    /**
     * In radians, unit circle style.
     */
    private double rotation;
    private final DcMotor leftVertical, rightVertical, horizontal;
    private double prevLeftVerticalPos, prevRightVerticalPos, prevHorizontalPos;

    public GPS(HardwareMap hardwareMap, Vector2D currentPosition, double currentBearing) {
        this.position = currentPosition;
        this.rotation = bearingToRadians(currentBearing);
        leftVertical = hardwareMap.dcMotor.get(Constants.LEFT_VERTICAL_ODOMETER);
        rightVertical = hardwareMap.dcMotor.get(Constants.RIGHT_VERTICAL_ODOMETER);
        horizontal = hardwareMap.dcMotor.get(Constants.HORIZONTAL_ODOMETER);
        prevLeftVerticalPos = 0;
        prevRightVerticalPos = 0;
        prevHorizontalPos = 0;
        correctDirections();
        resetEncoders();
    }

    private void correctDirections() {
        leftVertical.setDirection(DcMotorSimple.Direction.REVERSE);
        rightVertical.setDirection(DcMotorSimple.Direction.REVERSE);
        horizontal.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void resetEncoders() {
        leftVertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightVertical.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontal.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void updateCoordinates() {
        double leftVerticalPos = leftVertical.getCurrentPosition();
        double rightVerticalPos = rightVertical.getCurrentPosition();
        double deltaLeftVertical = leftVerticalPos - prevLeftVerticalPos;
        double deltaRightVertical = rightVerticalPos - prevRightVerticalPos;

        double deltaRot = (deltaLeftVertical - deltaRightVertical) / Constants.VERTICAL_ODOMETER_SEPARATION_DISTANCE;
        rotation += deltaRot;

        double horizontalPos = horizontal.getCurrentPosition();
        double deltaHorizontal = horizontalPos - prevHorizontalPos - deltaRot * Constants.HORIZONTAL_ODOMETER_DEGREES_TO_TICKS;

        double p = (deltaLeftVertical + deltaRightVertical) / 2;
        double n = deltaHorizontal;

        double x = position.getX() + p * Math.sin(rotation) + n * Math.cos(rotation);
        double y = position.getY() + p * Math.cos(rotation) - n * Math.sin(rotation);
        position.setX(x);
        position.setY(y);

        prevLeftVerticalPos = leftVerticalPos;
        prevRightVerticalPos = rightVerticalPos;
        prevHorizontalPos = horizontalPos;
    }

    public void logCoordinates() {
        Debug.log(this.getCoordinates().toString());
    }

    public Vector2D getPosition() {
        // clone so that position can not be externally modified
        return position.clone();
    }

    /**
     * Returns the coordinates describing the robot's position and orientation
     * relative to the starting coordinates.
     */
    public Coordinates getCoordinates() {
        return new Coordinates(this.position.getX(), this.position.getY(), 0, 0, 0, this.rotation);
    }

    /**
     * Returns the rotation in radians, unit circle style.
     */
    public double getRotation(){
        return rotation;
    }

    /**
     * Returns the robot's bearing in degrees.
     */
    public double getBearing() {
        return radiansToBearing(rotation);
    }

    private double radiansToBearing(double radians) {
        return radians;
    }

    private double bearingToRadians(double bearing) {
        return bearing;
    }
}
