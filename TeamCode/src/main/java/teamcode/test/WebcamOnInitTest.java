package teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import teamcode.common.AbstractOpMode;
import teamcode.common.Debug;
import teamcode.state.VisionOnInit;

@Disabled
@Autonomous(name = "Webcam Init")
public class WebcamOnInitTest extends AbstractOpMode {
    public VisionOnInit webcam;

    @Override
    protected void onInitialize() {
        webcam = new VisionOnInit(hardwareMap);
        while (!opModeIsActive()) {
            Debug.log(webcam.vuforiascan(false, true));
        }
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
