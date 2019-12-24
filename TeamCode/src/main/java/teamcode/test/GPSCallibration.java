package teamcode.test;

import com.qualcomm.hardware.motors.GoBILDA5202Series;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import teamcode.common.AbstractOpMode;
import teamcode.common.Debug;
import teamcode.common.Vector2D;
import teamcode.league3.DriveSystem;
import teamcode.league3.GPS;

@TeleOp(name = "GPS Callibration")
public class GPSCallibration extends AbstractOpMode {

    private GPS gps;
    private DriveSystem driveSystem;

    @Override
    protected void onInitialize() {
        Vector2D startPosition = Vector2D.zero();
        double startBearing = Math.PI / 2;
        gps = new GPS(hardwareMap, startPosition, startBearing);

        driveSystem = new DriveSystem(hardwareMap, gps);
    }

    @Override
    protected void onStart() {
//        double speed = 0.25;
//        //Calibration for arcs
//        while(opModeIsActive()){
//            telemetry.addData("GPS Position", gps.getPosition());
//            telemetry.addData("GPS Direction", gps.getRotation());
//            telemetry.update();
//            Vector2D velocity = new Vector2D(gamepad1.left_stick_x, -gamepad1.left_stick_y);
//            driveSystem.continuous(velocity.multiply(speed), gamepad1.right_stick_x * speed);
//        }

        Debug.log("here");
        driveSystem.goTo(new Vector2D(96, 0), 1);

        //GoTo test
//        Vector2D target1 = new Vector2D(24, 0);
//        Vector2D target2 = new Vector2D(24, 24);
//        Vector2D target3 = new Vector2D(0, 24);
//        Vector2D target4 = new Vector2D(0, 0);
//        driveSystem.goTo(target1, speed);
//        driveSystem.goTo(target2, speed);
//        driveSystem.goTo(target3, speed);
//        driveSystem.goTo(target4, speed);
    }

    @Override
    protected void onStop() {
        gps.shutdown();
    }

}
