package teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import teamcode.common.AbstractOpMode;
import teamcode.state.MoonshotArmSystem;

@Disabled
@Autonomous(name="StoneProcess")
public class StoneProcessorTest extends AbstractOpMode {


    private MoonshotArmSystem arm;

    @Override
    protected void onInitialize() {
        arm = new MoonshotArmSystem(hardwareMap);
    }

    @Override
    protected void onStart() {
        arm.intakeSequence();
        while(opModeIsActive());
    }

    @Override
    protected void onStop() {

    }
}
