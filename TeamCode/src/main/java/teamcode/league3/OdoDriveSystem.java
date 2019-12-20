package teamcode.league3;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import teamcode.common.Debug;
import teamcode.common.Vector2D;

public class OdoDriveSystem {
    private DcMotor frontLeft, frontRight, rearLeft, rearRight;
    private DcMotor leftOdo, rightOdo, horizontalOdo;
    private int prevLeftOdoTicks, prevRightOdoTicks, prevHorizontalOdoTicks;

    public OdoDriveSystem(HardwareMap hardwareMap) {
        initDrive( hardwareMap);
        initOdo(hardwareMap);
    }

    public void move(double inches, double power) {
        int ticksToMove = (int)(inches * Constants.TICKS_PER_INCHES);
        prevLeftOdoTicks = leftOdo.getCurrentPosition();
        prevRightOdoTicks = rightOdo.getCurrentPosition();
        int prevTicks = (prevLeftOdoTicks + prevRightOdoTicks) / 2;
        int targetTicks = prevTicks + ticksToMove;
        power = Math.abs(power);
        if (ticksToMove < 0) {
            power = -1 * power;
        }

        while (!nearTargetPosition(targetTicks)) {
            int delta = horizontalOdo.getCurrentPosition();
            double leftPower = power;
            double rightPower = power;
            if (delta > 0) {
                if (delta > 100) {
                    leftPower = 0;
                }
                else {
                    leftPower -= delta / 100;
                }
            }
            else {
                if (delta < -100) {
                    rightPower = 0;
                }
                else {
                    rightPower += delta / 100;
                }
            }
            frontLeft.setPower(leftPower);
            frontRight.setPower(power);
            rearLeft.setPower(leftPower);
            rearRight.setPower(power);
            logOdo();
        }

        brake();
    }

    public void brake() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        rearLeft.setPower(0);
        rearRight.setPower(0);
    }

    public void logOdo() {
        Debug.log("LeftOdo: " + leftOdo.getCurrentPosition() + ", RightOdo: " + rightOdo.getCurrentPosition() + "HorizOdo: " + horizontalOdo.getCurrentPosition());
    }

    private void initDrive(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.dcMotor.get(Constants.FRONT_LEFT_DRIVE);
        frontRight = hardwareMap.dcMotor.get(Constants.FRONT_RIGHT_DRIVE);
        rearLeft = hardwareMap.dcMotor.get(Constants.REAR_LEFT_DRIVE);
        rearRight = hardwareMap.dcMotor.get(Constants.REAR_RIGHT_DRIVE);

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        rearRight.setDirection(DcMotor.Direction.REVERSE);
    }

    private void initOdo(HardwareMap hardwareMap) {
        leftOdo = hardwareMap.dcMotor.get(Constants.LEFT_VERTICAL_ODOMETER);
        rightOdo = hardwareMap.dcMotor.get(Constants.RIGHT_VERTICAL_ODOMETER);
        horizontalOdo = hardwareMap.dcMotor.get(Constants.HORIZONTAL_ODOMETER);

        leftOdo.setDirection(DcMotor.Direction.FORWARD);
        rightOdo.setDirection(DcMotor.Direction.FORWARD);
        horizontalOdo.setDirection(DcMotor.Direction.FORWARD);

        leftOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horizontalOdo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private boolean nearTargetPosition(int targetTicks) {
        int leftTicks = leftOdo.getCurrentPosition();
        int rightTicks = rightOdo.getCurrentPosition();

        return Math.abs(targetTicks) - Math.abs(leftTicks) <= 0 &&
                Math.abs(targetTicks) - Math.abs(rightTicks) <= 0;
    }
}
