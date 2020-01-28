package teamcode.state;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import teamcode.common.AbstractOpMode;
import teamcode.common.Vector2D;

@Autonomous(name = "Strafe Left")
public class StrafeLeftAuto extends AbstractOpMode {
    DriveSystem drive;
    GPS gps;

    @Override
    protected void onInitialize() {
        Vector2D startPosition = new Vector2D(0, 0);
        double startRotation = 0;
        gps = new GPS(hardwareMap, startPosition, startRotation);
        drive = new DriveSystem(hardwareMap, gps, startPosition, startRotation);
    }

    @Override
    protected void onStart() {
        drive.lateral(-9, 0.8);
    }

    @Override
    protected void onStop() {
        gps.shutdown();
    }
}
