package teamcode.common;

public class Coordinates {
    private double x; // measures position in the x-axis in inches
    private double y; // measures position in the y-axis in inches
    private double z; // measures position in the z-axis in inches
    private double pitch; // measures rotation about the x-axis in radians
    private double roll; // measures rotation about the y-axis in radians
    private double yaw; // measures rotation about the z-axis in radians

    public Coordinates() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.pitch = 0;
        this.roll = 0;
        this.yaw = 0;
    }

    /**
     * Creates a new instance of the Coordinates class used to keep track of relative position and orientation.
     * x: the position in the x-axis in inches
     * y: the position in the y-axis in inches
     * z: position in the z-axis in inches
     * pitch: the rotation about the x-axis in radians
     * roll: the rotation about the y-axis in radians
     * yaw: the measures rotation about the z-axis in radians
     */
    public Coordinates(double x, double y, double z, double pitch, double roll, double yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.roll = roll;
        this.yaw = yaw;
    }

    @Override
    public String toString() {
        return String.format("x=%.1f, y=%.1f, z=%1.f, p=%.1f, r=%.1f, y=%.1f",
                this.x,
                this.y,
                this.z,
                this.pitch,
                this.roll,
                this.yaw);
    }
}
