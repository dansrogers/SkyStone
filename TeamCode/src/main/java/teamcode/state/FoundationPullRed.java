package teamcode.state;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import teamcode.common.AbstractOpMode;
import teamcode.common.Utils;
import teamcode.common.Vector2D;

@Autonomous(name="foundationPull")
public class FoundationPullRed extends AbstractOpMode {
    final double SPEED = 0.6;
    MoonshotArmSystem arm;
    DriveSystem drive;
    GPS gps;


    @Override
    protected void onInitialize() {
        gps = new GPS(hardwareMap, new Vector2D(-9, 107), 0);
        drive = new DriveSystem(hardwareMap, gps, new Vector2D(-9, 107), 0);
        arm = new MoonshotArmSystem(hardwareMap);
    }

    @Override
    protected void onStart() {
        drive.goTo(new Vector2D(-43, 120), SPEED);
        arm.adjustFoundation();
        Utils.sleep(1000);
        drive.goTo(new Vector2D(-9, 120), SPEED);
        drive.setRotation(Math.toRadians(-90), SPEED);
        arm.adjustFoundation();
        drive.goTo(new Vector2D(-9, 72), SPEED);
    }

    @Override
    protected void onStop() {

    }
}
