package teamcode.test.AdvancedOdometry.LinearAlgebraInterpretation;

import teamcode.common.AbstractOpMode;
import teamcode.common.Vector2D;
import teamcode.state.DriveSystem;
import teamcode.test.AdvancedOdometry.MovementVars;
import teamcode.test.AdvancedOdometry.PurePursuitMovement;

public class LinearAlgLocalizerTest extends AbstractOpMode {

    Localizer localizer;
    DriveSystem driveSystem;
    PurePursuitMovement movement;
    @Override
    protected void onInitialize() {
        driveSystem = new DriveSystem(hardwareMap);


    }



    @Override
    protected void onStart() {
        new Thread(){
            public void run(){
                while(opModeIsActive()){

                }
            }
        }.start();
        new Thread(){
            public void run(){
                while(opModeIsActive()){
                    driveSystem.continuous(new Vector2D(MovementVars.movementX, MovementVars.movementY), MovementVars.movementTurn);
                }
            }
        }.start();

    }

    @Override
    protected void onStop() {

    }
}
