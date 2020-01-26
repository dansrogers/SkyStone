package teamcode.state;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import teamcode.common.AbstractOpMode;
import teamcode.common.Vector2D;

@Autonomous(name="park")
public class Park extends AbstractOpMode {
    DriveSystem drive;
    GPS gps;

    @Override
    protected void onInitialize() {
        gps = new GPS(hardwareMap, new Vector2D(9, 81), 0);
        drive = new DriveSystem(hardwareMap, gps, new Vector2D(9, 81), 0);

    }

    @Override
    protected void onStart() {
       drive.lateral(-1, 0.5);

    }

    @Override
    protected void onStop() {

    }
}
