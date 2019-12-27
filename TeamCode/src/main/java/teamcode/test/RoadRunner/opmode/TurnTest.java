package teamcode.test.RoadRunner.opmode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import teamcode.test.RoadRunner.mecanum.SampleMecanumDriveBase;
import teamcode.test.RoadRunner.mecanum.SampleMecanumDriveREVOptimized;

/*
 * This is a simple routine to test turning capabilities.
 */

@Autonomous(group = "drive")
public class TurnTest extends LinearOpMode {
    public static double ANGLE = 90; // deg

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveBase drive = new SampleMecanumDriveREVOptimized(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        drive.turnSync(Math.toRadians(ANGLE));
    }
}
