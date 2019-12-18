package teamcode.common;

public class Coordinates {
    private double x; // measures position in the x-axis in inches
    private double y; // measures position in the y-axis in inches
    private double z; // measures position in the z-axis in inches
    private double pitch; // measures rotation about the x-axis in radians
    private double roll; // measures rotation about the y-axis in radians
    private double yaw; // measures rotation about the z-axis in radians

    /**
     * Creates a new instance of the Coordinates class used to keep track
     * of relative position and orientation.
     */
    public Coordinates() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.pitch = 0;
        this.roll = 0;
        this.yaw = 0;
    }

    /**
     * Copy constructor.
     * @param c The coordinates to create a copy from.
     */
    public Coordinates(Coordinates c) {
        this.x = c.x;
        this.y = c.y;
        this.z = c.z;
        this.pitch = c.pitch;
        this.roll = c.roll;
        this.yaw = c.yaw;
    }

    /**
     * Creates a new instance of the Coordinates class used to keep track
     * of relative position and orientation.
     * @param x The offset value in the x-axis in centimeters.
     * @param y The offset value in the y-axis in centimeters.
     * @param z The offset value in the z-axis in centimeters.
     * @param pitch The rotation value about the x-axis in radians.
     * @param roll The rotation value about the y-axis in radians.
     * @param yaw The rotation value about the z-axis in radians.
     */
    public Coordinates(double x, double y, double z, double pitch, double roll, double yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.roll = roll;
        this.yaw = yaw;
    }

    /**
     * Gets the position in the x-axis in centimeters.
     * @return The offset value in the x-axis in centimeters.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Sets the position in the x-axis in centimeters.
     * @param x The offset value in the x-axis in centimeters.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the position in the y-axis in centimeters.
     * @return The offset value in the y-axis in centimeters.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets the position in the y-axis in centimeters.
     * @param y The offset value in the y-axis in centimeters.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the position in the z-axis in centimeters.
     * @return The offset value in the z-axis in centimeters.
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Sets the position in the z-axis in centimeters.
     * @param z The offset value in the z-axis in centimeters.
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Gets the rotation about the x-axis in radians.
     * @return The rotation value about the x-axis in radians.
     */
    public double getPitch() {
        return this.pitch;
    }

    /**
     * Sets the rotation about the x-axis in radians.
     * @param pitch The rotation value about the x-axis in radians.
     */
    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    /**
     * Gets the rotation about the y-axis in radians.
     * @return The rotation value about the y-axis in radians.
     */
    public double getRoll() {
        return this.roll;
    }

    /**
     * Sets the rotation about the x-axis in radians.
     * @param roll The rotation value about the y-axis in radians.
     */
    public void setRoll(double roll) {
        this.roll = roll;
    }

    /**
     * Gets the rotation about the z-axis in radians.
     * @return The rotation value about the z-axis in radians.
     */
    public double getYaw() {
        return this.yaw;
    }

    /**
     * Sets the rotation about the z-axis in radians.
     * @param yaw The rotation value about the z-axis in radians.
     */
    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    @Override
    public String toString() {
        return String.format("x=%.1f, y=%.1f, z=%1.f, pitch=%.1f, roll=%.1f, yaw=%.1f",
                this.x,
                this.y,
                this.z,
                this.pitch,
                this.roll,
                this.yaw);
    }
}
