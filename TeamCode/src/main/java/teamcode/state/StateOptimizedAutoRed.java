package teamcode.state;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.security.Policy;
import java.util.Timer;
import java.util.TimerTask;

import teamcode.common.AbstractOpMode;
import teamcode.common.Debug;
import teamcode.common.Utils;
import teamcode.common.Vector2D;
import teamcode.state.VisionOnInit.SkystonePos;

@Autonomous(name="Red auto")
public class StateOptimizedAutoRed extends AbstractOpMode {
    DriveSystem drive;
    MoonshotArmSystem arm;
    VisionOnInit vision;
    GPS gps;
    SkystonePos pos;
    Timer timer1;
    Timer timer2;
    private final double SPEED = 1.0;
    @Override
    protected void onInitialize() {
        gps = new GPS(hardwareMap, new Vector2D(-9, 38.5), Math.toRadians(180));
        drive = new DriveSystem(hardwareMap, gps, new Vector2D(-9, 38.5), Math.toRadians(180));
        vision = new VisionOnInit(hardwareMap);
        arm = new MoonshotArmSystem(hardwareMap);
        timer1 = new Timer();
        timer2 = new Timer();
        while(!opModeIsActive()){
            pos = vision.vuforiascan(false, true);
            Debug.log(pos);
        }
    }

    @Override
    protected void onStart() {

        if(pos == SkystonePos.LEFT){
            intakeStone(1, true);
            scoreStone( true);
            intakeStone(4, false);
            scoreStone(false);
            park();
        }else if(pos == SkystonePos.CENTER){
            intakeStone(2, true);
            scoreStone( true);
            intakeStone(5, false);
            scoreStone( false);
            park();
        }else if(pos == SkystonePos.RIGHT){
            intakeStone(3, true);
            scoreStone( true);
            intakeStone(6, false);
            scoreStone(false);
            park();
        }
    }

    private void scoreStone( boolean pullFoundation) {
        if(pullFoundation){
            //y value at 122
            drive.goTo(new Vector2D(-30, 113), SPEED);
            drive.setRotation(Math.toRadians(0), SPEED);

            drive.goTo(new Vector2D(-41, 113), 0.3);
            arm.adjustFoundation();
            arm.scoreAUTO();
            drive.goTo(new Vector2D(-36, 113), SPEED);
            //drive.goTo(new Vector2D(18, 114), SPEED);
            //drive.goTo(new Vector2D(36,96), SPEED);
            drive.setRotation(Math.toRadians(90), SPEED);
            drive.goTo(new Vector2D(-36,96), SPEED);
            arm.adjustFoundation();
        }else{
            drive.goTo(new Vector2D(-24, 113), SPEED);
            arm.scoreAUTO();
        }
        //drive.goTo(new Vector2D(36, 96), SPEED);
        //drive.goTo(parkingSpot, SPEED);
    }

    Vector2D parkingSpot = new Vector2D(-36, 72);

    private void intakeStone(double stoneNum, boolean isFirst) {

        if(stoneNum == 1){
            intakeStone();
//            drive.goTo(new Vector2D(24, 62), SPEED);
//            drive.setRotation(Math.toRadians(-30), SPEED);
            drive.goTo(new Vector2D(-49, 52), 0.4);
        } else {
            //10, half the robot +1
            double stoneNumToInch = Constants.STONE_LENGTH_INCHES * (7 - stoneNum) + 10;
//            drive.goTo(new Vector2D(36, 37 - ((6 - stoneNum) - 2) * 8), SPEED);
//            drive.goTo(new Vector2D(57, 37 - ((6 - stoneNum) - 2) * 8), 0.8);
//            intakeStone();
//            drive.goTo(new Vector2D(57, 37 - ((6 - stoneNum) - 1.5) * 8), SPEED);
            drive.goTo(new Vector2D(-36, stoneNumToInch), SPEED);
            drive.goTo(new Vector2D(-57, stoneNumToInch), 0.8);
            if(isFirst) {
                drive.setRotation(Math.toRadians(90), SPEED);
            }
            intakeStone();
            drive.goTo(new Vector2D(-57, stoneNumToInch - 4), SPEED);
        }

        //Utils.sleep(250);
        drive.goTo(new Vector2D(-36, 48), SPEED);


    }

    public void intakeStone(){
        new Thread(){
            public void run(){
                arm.suck(-1);
                Utils.sleep(50);
                Debug.log("Intake active");
                arm.intakeSequenceAUTO(4000);
                arm.primeToScoreAUTO();
            }
        }.start();

    }

    private void park(){
        drive.goTo(parkingSpot, SPEED);
    }

    @Override
    protected void onStop() {
        gps.shutdown();
        timer1.cancel();
        timer2.cancel();

    }



}
