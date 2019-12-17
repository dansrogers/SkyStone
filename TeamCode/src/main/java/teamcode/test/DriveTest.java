package teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import teamcode.common.AbstractOpMode;
import teamcode.common.Debug;
import teamcode.common.Utils;
import teamcode.common.Vector2D;
import teamcode.league3.DriveSystem;
import teamcode.league3.GPS;

@Autonomous(name = "Drive Test")
public class DriveTest extends AbstractOpMode {

    GPS gps;
    private DriveSystem driveSystem;
    private CoordinateUpdateControl coordinateUpdateControl;
    private CoordinateLoggerControl coordinateLoggerControl;

    @Override
    protected void onInitialize() {
        Debug.log("onInitialize 1");
        gps = new GPS(hardwareMap, Vector2D.zero(), Math.PI);
        driveSystem = new DriveSystem(hardwareMap, gps);

        Debug.log("onInitialize 2");
        this.coordinateUpdateControl = new CoordinateUpdateControl();

        Debug.log("onInitialize 3");
        this.coordinateLoggerControl = new CoordinateLoggerControl();

        Debug.log("onInitialize 4");
    }

    @Override
    protected void onStart() {
        Debug.log("onStart 1");
        this.coordinateUpdateControl.start();
        Debug.log("onStart 2");
        this.coordinateLoggerControl.start();
        Debug.log("onStart 3");
        Vector2D target = new Vector2D(15000, 50000);
        driveSystem.goTo(target, 0.2);
        Debug.log("onStart 4");

        // Block until the autonomous period expires.
        while(opModeIsActive());

        Debug.log("onStart 4");
    }

    @Override
    protected void onStop() {
        Debug.log("onStop 1");
        this.coordinateUpdateControl.stopIt();
        Debug.log("onStop 2");
        this.coordinateLoggerControl.stopIt();
        Debug.log("onStop 3");
    }

    private class CoordinateUpdateControl extends Thread {
        private boolean stopThread;
        public CoordinateUpdateControl() {
            this.stopThread = false;
        }

        @Override
        public void run() {
            while (!this.stopThread) {
                gps.updateCoordinates();
            }
        }

        public void stopIt() {
            this.stopThread = true;
        }
    }

    private class CoordinateLoggerControl extends Thread {
        private boolean stopThread;
        public CoordinateLoggerControl() {
            this.stopThread = false;
        }

        @Override
        public void run() {
            while (!this.stopThread) {
                gps.logCoordinates();
                Utils.sleep(2000);
            }
        }

        public void stopIt() {
            this.stopThread = true;
        }
    }
}
