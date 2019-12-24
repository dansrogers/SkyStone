package teamcode.test.odometry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;

import teamcode.common.AbstractOpMode;
import teamcode.common.Point;
import teamcode.common.Vector2D;
import teamcode.league3.DriveSystem;
import teamcode.league3.GPS;


@Autonomous(name = "Pursuit Test")
public class PurePursuitSimpleTest extends AbstractOpMode {
    DriveSystem driveSystem;
    OdometryWheelsFinal wheels;
    PurePursuitSimple movement;
    ArrayList<Point> path;

    @Override
    protected void onInitialize() {
       GPS gps = new GPS(this.hardwareMap, new Vector2D(24, 24), 0);

        driveSystem = new DriveSystem(AbstractOpMode.currentOpMode().hardwareMap, wheels);
        movement = new PurePursuitSimple(wheels, driveSystem);
        path = new ArrayList<>();
    }

    @Override
    protected void onStart() {

        path.add(new Point(100, 100));
        path.add(new Point(300,100));
        path.add(new Point(310, 100));
        movement.followCurve(path, 0.5);

    }

    @Override
    protected void onStop() {

    }
}
