package teamcode.state;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.Vector;

import teamcode.common.AbstractOpMode;
import teamcode.common.Vector2D;

@Autonomous(name = "Strafe Right")
public class StrafeRightAuto extends AbstractOpMode {
    DriveSystem drive;
    GPS gps;

    @Override
    protected void onInitialize() {
        Vector2D startPosition = new Vector2D(9, 81);
        double startRotation = 0;
        gps = new GPS(hardwareMap, startPosition, startRotation);
        drive = new DriveSystem(hardwareMap, gps, startPosition, startRotation);
    }

    @Override
    protected void onStart() {
        drive.goTo(new Vector2D(9, 72), 0.8);
    }

    @Override
    protected void onStop() {
        gps.shutdown();
    }
}
