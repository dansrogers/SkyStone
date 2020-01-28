package teamcode.state;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import teamcode.common.AbstractOpMode;
import teamcode.common.Utils;

@Disabled
@Autonomous(name = "Park With Arm")
public class ParkAutoWithArm extends AbstractOpMode {

    private MoonshotArmSystem arm;

    @Override
    protected void onInitialize() {
        arm = new MoonshotArmSystem(hardwareMap);
    }

    @Override
    protected void onStart() {
        arm.flattenRamp();
        Utils.sleep(28000);
        arm.extend();
        while(opModeIsActive());
    }

    @Override
    protected void onStop() {

    }
}
