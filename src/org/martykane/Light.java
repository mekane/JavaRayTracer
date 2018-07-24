package org.martykane;

/**
 * A class that encapsulates a light
 */

public class Light implements Exportable {
    public static final Color WHITE = new Color(255, 255, 255);

    private double x;
    private double y;
    private double z;
    private double brightness = 0;
    private Color color = WHITE;

    private Light() {
    }

    /**
     * Make a new light, at the given position, with the given brightness and
     * color
     *
     * @param lx     light's position X coordinate
     * @param ly     light's position Y coordinate
     * @param lz     light's position Z coordinate
     * @param bright light's brightness
     * @param c      light's color
     */
    public Light(double lx, double ly, double lz, double bright, Color c) {
        this.x = lx;
        this.y = ly;
        this.z = lz;
        this.brightness = bright;
        this.color = c;
    }

    /**
     * Make a white light at the given position with the given brightness
     */
    public Light(double lx, double ly, double lz, double bright) {
        this(lx, ly, lz, bright, WHITE);
    }

    /**
     * Make a white light at the given position with the given brightness
     */
    public Light(Point3d pos, double bright) {
        this(pos.getX(), pos.getY(), pos.getZ(), bright, WHITE);
    }

    public Light(Point3d pos, double bright, Color c) {
        this(pos.getX(), pos.getY(), pos.getZ(), bright, c);
    }

    //XYZ
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Point3d getPosition() {
        return new Point3d(this.x, this.y, this.z);
    }

    public double getBrightness() {
        return this.brightness;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public String toJson() {
        return String.join("\n",
                "{",
                this.getPosition().toTriple().toJsonObjectWithLabel("position", 1) + ",",
                "  brightness: " + this.getBrightness() + ",",
                this.color.toJson(1),
                "}"
        );
    }

    @Override
    public Object fromJson() {
        return null;
    }
}
