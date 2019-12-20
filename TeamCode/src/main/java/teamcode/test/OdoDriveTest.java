package teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import teamcode.common.AbstractOpMode;
import teamcode.league3.OdoDriveSystem;

@Autonomous(name = "OdoDriveTest")
public class OdoDriveTest extends AbstractOpMode {
    private OdoDriveSystem drive;

    @Override
    protected void onInitialize() {
        drive = new OdoDriveSystem(hardwareMap);
    }

    @Override
    protected void onStart() {
        //drive.logOdo();
        drive.move(12, 0.3);

        while(opModeIsActive()) {
            drive.logOdo();
        }
    }

    @Override
    protected void onStop() {

    }
}
