package teamcode.test.AdvancedOdometry.LinearAlgebraInterpretation;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Arrays;
import java.util.List;

import teamcode.common.AbstractOpMode;
import teamcode.test.revextensions2.ExpansionHubEx;
import teamcode.test.revextensions2.ExpansionHubMotor;
import teamcode.test.revextensions2.RevBulkData;

public class LinearAlgebraLocalizer extends Localizer{

    public static double TICKS_PER_REV = 8192;
    public static double WHEEL_RADIUS = 1.181; // in
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (encoder) speed

    public static double LATERAL_DISTANCE = -13.5; // in; distance between the left and right wheels
    public static double FORWARD_OFFSET = -7.5; // in; offset of the lateral wheel

    private DcMotor leftEncoder, rightEncoder, frontEncoder;
    private ExpansionHubEx hub1, hub2;
    private RevBulkData data1, data2;

    public LinearAlgebraLocalizer(HardwareMap hardwareMap) {
        super(Arrays.asList(
                new Pose2d(0, LATERAL_DISTANCE / 2, 0), // left
                new Pose2d(0, -LATERAL_DISTANCE / 2, 0), // right
                new Pose2d(FORWARD_OFFSET, 0, Math.toRadians(90)) // front
        ));

        hub1 = hardwareMap.get(ExpansionHubEx.class,"Expansion Hub 1");
        hub2 = hardwareMap.get(ExpansionHubEx.class,"Expansion Hub 2");
        frontEncoder = hardwareMap.dcMotor.get("HorizontalOdometer");
        rightEncoder = hardwareMap.dcMotor.get("RightVerticalOdometer");
        leftEncoder = hardwareMap.dcMotor.get("LeftVerticalOdometer");
        frontEncoder.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public static double encoderTicksToInches(int ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }



    @Override
    public List<Double> getWheelPositions() {
        data1 = hub1.getBulkInputData();
        data2 = hub2.getBulkInputData();
        return Arrays.asList(
                encoderTicksToInches(data2.getMotorCurrentPosition(leftEncoder)),
                encoderTicksToInches(-data1.getMotorCurrentPosition(rightEncoder)),
                encoderTicksToInches(-data1.getMotorCurrentPosition(frontEncoder)));
    }
}
