package teamcode.state;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import teamcode.common.AbstractOpMode;
import teamcode.common.Vector2D;


@TeleOp(name="DeliveryTeleOp")
public class DeliveryTeleOp extends AbstractOpMode {
    MoonshotArmSystem arm;
    DriveSystem drive;
    Thread driveUpdate;
    Thread armUpdate;
    boolean driverOne;


    private double NORMAL_MODIFIER_ROTATIONAL = 0.6;
    private double SPRINT_MODIFIER_ROTATIONAL = 0.75;
    private double NORMAL_MODIFIER_LINEAR = 1.0;
    private double SPRINT_MODIFIER_LINEAR = 1.0;

    private final double SPRINT_MODIFIER_LINEAR_DRIVER_TWO = 0.8;
    private final double SPRINT_MODIFIER_ROTATIONAL_DRIVER_TWO = 0.75;
    private final double NORMAL_MODIFIER_LINEAR_DRIVER_TWO = 0.25;
    private final double NORMAL_MODIFIER_ROTATIONAL_DRIVER_TWO = 0.3;
    private static final double TURN_SPEED_CORRECTION_MODIFIER = 0;

    @Override
    protected void onInitialize() {
        drive = new DriveSystem(hardwareMap);
    }

    @Override
    protected void onStart() {
        arm = new MoonshotArmSystem(hardwareMap);
        driveUpdate = new Thread(){
            public void run(){
                while(opModeIsActive()){
                    driveUpdate();
                }
            }
        };
        armUpdate = new Thread(){
            public void run(){
                while(opModeIsActive()){
                    armUpdate();
                }
            }
        };

        driveUpdate.start();
        armUpdate.start();
        while(opModeIsActive());
    }

    private void armUpdate() {

        if(gamepad1.right_trigger > 0.3){
            while(gamepad1.right_trigger > 0.3){
                arm.suck(1, "");
            }
            arm.suck(0, "");
        }
        if(gamepad1.left_trigger > 0.3){
            while(gamepad1.left_trigger > 0.3){
                arm.suck(-0.5, "");
            }
            arm.suck(0, "");
        }

    }

    private void driveUpdate() {
        double turnSpeed;
        Vector2D velocity;

            turnSpeed = -gamepad1.left_stick_x;
            double x = gamepad1.right_stick_x;
            if (gamepad1.right_stick_y == 0)
            {
                // strafing
                x = x * 0.75;
            }

            velocity = new Vector2D(-x, gamepad1.right_stick_y);
            velocity = velocity.multiply(-SPRINT_MODIFIER_LINEAR);
            turnSpeed *= SPRINT_MODIFIER_ROTATIONAL;

            turnSpeed += TURN_SPEED_CORRECTION_MODIFIER * velocity.magnitude();
            drive.continuous(velocity, turnSpeed);
    }


    @Override
    protected void onStop() {

    }
}
