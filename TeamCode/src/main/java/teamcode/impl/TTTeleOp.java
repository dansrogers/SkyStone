package teamcode.impl;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import teamcode.common.TTArm;
import teamcode.common.TTDriveSystem;
import teamcode.common.TTOpMode;
import teamcode.common.Vector2;

@TeleOp(name = "TT TeleOp")
public class TTTeleOp extends TTOpMode {

    private static final double TURN_SPEED_MODIFIER = 0.6;
    private static final double REDUCED_DRIVE_SPEED = 0.6;

    private TTDriveSystem driveSystem;
    private TTArm arm;

    @Override
    protected void onInitialize() {
    }

    @Override
    protected void onStart() {
        driveSystem = new TTDriveSystem(hardwareMap);

        while (opModeIsActive()) {
            update();
        }
    }

    private void update() {
        //driveUpdate();
        armUpdate();
    }

    protected void onStop() {
    }

    private void driveUpdate() {
        double vertical = gamepad1.right_stick_y;
        double horizontal = gamepad1.right_stick_x;
        double turn = gamepad1.left_stick_x * TURN_SPEED_MODIFIER;
        Vector2 velocity = new Vector2(vertical, horizontal);
        if (!gamepad1.right_bumper) {
            velocity = velocity.multiply(REDUCED_DRIVE_SPEED);
        }
        driveSystem.continuous(velocity, turn);
    }

    private void armUpdate(){
        arm.armLift(gamepad2.right_trigger);
        arm.armLower(gamepad2.left_trigger * -1);
        if(gamepad2.x && arm.getClawPos() == 1){
            arm.rotateClaw(0);
        }
        if (gamepad2.x && arm.getClawPos() == 0){
            arm.rotateClaw(1);
        }
        if (gamepad2.b && arm.getWristPos() < 0.7) {
            arm.rotateWrist(0);
        }
        if (gamepad2.b && arm.getWristPos() > 0){
            arm.rotateWrist(0.7);
        }
    }

}
