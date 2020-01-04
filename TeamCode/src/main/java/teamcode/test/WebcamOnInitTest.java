package teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import teamcode.common.AbstractOpMode;
import teamcode.common.Debug;
import teamcode.league3.DriveSystem;


@Autonomous(name= "Webcam Init")
public class WebcamOnInitTest extends AbstractOpMode {
    public VisionOnInit webcam;

    @Override
    protected void onInitialize() {
        webcam = new VisionOnInit(hardwareMap);
        Debug.log(webcam.vuforiascan(true, false));
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onStop() {

    }
}
