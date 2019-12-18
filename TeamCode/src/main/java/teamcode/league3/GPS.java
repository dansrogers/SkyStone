package teamcode.league3;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import teamcode.common.Coordinates;
import teamcode.common.Debug;

/**
 * Always call shutdown() in onStop() of AbstractOpMode.
 */
public class GPS {

    private Coordinates currentCoordinates;
    private final DcMotor leftOdo, rightOdo, horizontalOdo;
    private double prevLeftOdoTicks, prevRightOdoTicks, prevHorizontalOdoTicks;

    public GPS(HardwareMap hardwareMap, Coordinates startingCoordinates) {
        this.currentCoordinates = startingCoordinates;
        leftOdo = hardwareMap.dcMotor.get(Constants.LEFT_VERTICAL_ODOMETER);
        rightOdo = hardwareMap.dcMotor.get(Constants.RIGHT_VERTICAL_ODOMETER);
        horizontalOdo = hardwareMap.dcMotor.get(Constants.HORIZONTAL_ODOMETER);
        prevLeftOdoTicks = 0;
        prevRightOdoTicks = 0;
        prevHorizontalOdoTicks = 0;
        correctDirections();
        resetEncoders();
    }

    private void correctDirections() {
        leftOdo.setDirection(DcMotorSimple.Direction.REVERSE);
        rightOdo.setDirection(DcMotorSimple.Direction.REVERSE);
        horizontalOdo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void resetEncoders() {
        leftOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontalOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void updateCoordinates() {
        double leftOdoTicks = leftOdo.getCurrentPosition();
        double rightOdoTicks = rightOdo.getCurrentPosition();
        double deltaLeftOdoTicks = leftOdoTicks - prevLeftOdoTicks;
        double deltaRightOdoTicks = rightOdoTicks - prevRightOdoTicks;
        
        double deltaRot = (deltaLeftOdoTicks - deltaRightOdoTicks) / Constants.VERTICAL_ODOMETER_SEPARATION_DISTANCE;
        rotation += deltaRot;

        double horizontalPos = horizontalOdo.getCurrentPosition();
        double deltaHorizontal = horizontalPos - prevHorizontalOdoTicks - deltaRot * Constants.HORIZONTAL_ODOMETER_DEGREES_TO_TICKS;

        double p = (deltaLeftOdoTicks + deltaRightOdoTicks) / 2;
        double n = deltaHorizontal;

        double x = position.getX() + p * Math.sin(rotation) + n * Math.cos(rotation);
        double y = position.getY() + p * Math.cos(rotation) - n * Math.sin(rotation);
        position.setX(x);
        position.setY(y);

        prevLeftOdoTicks = leftOdoTicks;
        prevRightOdoTicks = rightOdoTicks;
        prevHorizontalOdoTicks = horizontalPos;
    }

    public void logCoordinates() {
        Debug.log(this.getCoordinates().toString());
    }

    /**
     * Gets the current coordinates.
     * @return The current coordinates.
     */
    public Coordinates getCoordinates() {
        // clone so that position can not be externally modified
        return new Coordinates(this.currentCoordinates);
    }
}
