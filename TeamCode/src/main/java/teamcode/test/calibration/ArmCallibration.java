package teamcode.test.calibration;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import teamcode.common.AbstractOpMode;
import teamcode.common.Debug;
import teamcode.league2.ArmSystemLeague2;

@Autonomous(name = "Arm Callibration")
public class ArmCallibration extends AbstractOpMode {

    private static final int TICKS = 1500;
    private static final double POWER = 0.5;

    private ArmSystemLeague2 arm;

    @Override
    protected void onInitialize() {
        arm = new ArmSystemLeague2(this);
    }

    @Override
    protected void onStart() {
        Debug.log("Raising: " + TICKS);
        arm.lift(4, POWER);
        while (opModeIsActive()) ;
    }

    @Override
    protected void onStop() {

    }
}
