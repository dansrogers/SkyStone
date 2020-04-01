package teamcode.test.AdvancedOdometry;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import teamcode.common.AbstractOpMode;
import teamcode.common.Vector2D;
import teamcode.state.DriveSystem;
import teamcode.test.AdvancedOdometry.LinearAlgebraInterpretation.Localizer;

public class ArcPosUpdateTest extends AbstractOpMode {
    ArcPositionUpdate localizer;
    DriveSystem driveSystem;
    PurePursuitMovement movement;

    @Override
    protected void onInitialize() {
        localizer = new ArcPositionUpdate(hardwareMap, new Point(0,0), 0);
        driveSystem = new DriveSystem(hardwareMap);
        movement = new PurePursuitMovement(localizer);
    }

    @Override
    protected void onStart() {
        ArrayList<CurvePoint> path =  new ArrayList<>();
        MovementVars.movementX = 0;
        MovementVars.movementY = 0;
        MovementVars.movementTurn = 0;
        new Thread(){
            public void run(){
                while(opModeIsActive()){
                    driveSystem.continuous(new Vector2D(MovementVars.movementX, MovementVars.movementY), MovementVars.movementTurn);
                }
            }
        }.start();
        path.add(new CurvePoint(0.0,0.0,0.5,1.0,20.0,Math.toRadians(50), 1.0));
        path.add(new CurvePoint(180.0,180.0,1.0,1.0,50.0,Math.toRadians(50), 1.0));
        path.add(new CurvePoint(220.0,180.0,1.0,1.0,50.0,Math.toRadians(50), 1.0));
        path.add(new CurvePoint(280.0,50.0,1.0,1.0,50.0,Math.toRadians(50), 1.0));
        try {
            movement.followCurve(path, 90);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {

    }
}
