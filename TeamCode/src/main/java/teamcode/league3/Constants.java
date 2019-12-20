package teamcode.league3;

public class Constants {

    // hardware device names
    public static final String FRONT_LEFT_DRIVE = "FrontLeftDrive";
    public static final String FRONT_RIGHT_DRIVE = "FrontRightDrive";
    public static final String REAR_LEFT_DRIVE = "RearLeftDrive";
    public static final String REAR_RIGHT_DRIVE = "RearRightDrive";
    public static final String LEFT_VERTICAL_ODOMETER = "LeftVerticalOdometer";
    public static final String RIGHT_VERTICAL_ODOMETER = "RightVerticalOdometer";
    public static final String HORIZONTAL_ODOMETER = "HorizontalOdometer";

    public static final int TICKS_PER_REVOLUTION = 8192; //1102 ticks / inch
    public static final int TICKS_PER_INCHES = 1102;

    public static final int DRIVE_TOLERANCE_TICKS = 50;

}
