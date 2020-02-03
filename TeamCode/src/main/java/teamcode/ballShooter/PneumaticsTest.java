package teamcode.ballShooter;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import teamcode.common.AbstractOpMode;

@TeleOp(name = "Pneumatics Test")
public class PneumaticsTest extends AbstractOpMode {

    private Servo pump;

    @Override
    protected void onInitialize() {
        pump = hardwareMap.servo.get("pump");
    }

    @Override
    protected void onStart() {
        if (gamepad1.a) {
            pump.setPosition(0);
        } else if (gamepad1.y) {
            pump.setPosition(1);
        }
    }

    @Override
    protected void onStop() {
    }
}
