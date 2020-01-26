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
    private final double SPEED = 0.8;
    @Override
    protected void onInitialize() {
        gps = new GPS(hardwareMap, new Vector2D(-9, 36.5), Math.toRadians(180));
        drive = new DriveSystem(hardwareMap, gps, new Vector2D(-9, 36.5), Math.toRadians(180));
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
            intakeStone(3, true);
            scoreStone( true);
            intakeStone(6, false);
            scoreStone(false);
            park();
        }else if(pos == SkystonePos.CENTER){
            intakeStone(2, true);
            scoreStone( true);
            intakeStone(5, false);
            scoreStone( false);
            park();
        }else if(pos == SkystonePos.RIGHT){
            intakeStone(1, true);
            scoreStone( true);
            intakeStone(4, false);
            scoreStone(false);
            park();
        }
    }

    private void scoreStone( boolean pullFoundation) {
        if(pullFoundation){
            //y value at 122
            drive.goTo(new Vector2D(-35.5, 120), SPEED);
            drive.setRotation(Math.toRadians(360), SPEED);

            drive.goTo(new Vector2D(-44, 120), 0.3);
            arm.adjustFoundation();
            arm.scoreAUTO();
            drive.goTo(new Vector2D(-35, 98.5), SPEED);
            //drive.goTo(new Vector2D(18, 114), SPEED);
            //drive.goTo(new Vector2D(36,96), SPEED);
            drive.setRotation(Math.toRadians(270), SPEED);
            //drive.setRotation(Math.toRadians(0), SPEED);
            drive.goTo(new Vector2D(-36,96), SPEED);
            arm.adjustFoundation();
        }else{
            drive.goTo(new Vector2D(-32, 111), SPEED);
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
            intakeStone();
        } else {
            //10, half the robot +1
            double stoneNumToInch = Constants.STONE_LENGTH_INCHES * (7 - stoneNum) + 10;
            if(stoneNum == 6){
                stoneNumToInch = 20;
            }
            double xValue = -50;
            if (!isFirst) {
                xValue -= 4;
            }
            if(stoneNum == 6){
                xValue -= 2;
            }


//            drive.goTo(new Vector2D(36, 37 - ((6 - stoneNum) - 2) * 8), SPEED);
//            drive.goTo(new Vector2D(57, 37 - ((6 - stoneNum) - 2) * 8), 0.8);
//            intakeStone();
//            drive.goTo(new Vector2D(57, 37 - ((6 - stoneNum) - 1.5) * 8), SPEED);

            drive.goTo(new Vector2D(-32, stoneNumToInch), SPEED);
            if (isFirst) {
                drive.setRotation(Math.toRadians(270), SPEED);
            }
            drive.goTo(new Vector2D(xValue, stoneNumToInch), 0.8);
            intakeStone();
            drive.goTo(new Vector2D(xValue, stoneNumToInch - 6), SPEED);
        }

        //Utils.sleep(250);
        drive.goTo(new Vector2D(-36, 48), SPEED);
        if(stoneNum == 1){
            drive.setRotation(Math.toRadians(270), SPEED);
        }
    }

    public void intakeStone(){
        new Thread(){
            public void run(){
                arm.suck(-1);
                Utils.sleep(50);
                Debug.log("Intake active");
                arm.intakeSequenceAUTO(800);
                arm.primeToScoreAUTO();
            }
        }.start();

    }

    private void park(){
        drive.goTo(new Vector2D(-41, 72), SPEED);
    }

    @Override
    protected void onStop() {
        gps.shutdown();
        timer1.cancel();
        timer2.cancel();

    }



}
