package teamcode.ballShooter;

import teamcode.common.AbstractOpMode;

public class TeleOp extends AbstractOpMode {

    private DriveSystem drive;
    private BallShooter shooter;

    @Override
    protected void onInitialize() {
        drive = new DriveSystem(hardwareMap);
        shooter = new BallShooter(hardwareMap);
    }

    @Override
    protected void onStart() {
    }

    @Override
    protected void onStop() {

    }
}
