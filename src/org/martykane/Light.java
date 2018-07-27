package org.martykane;

import org.json.JSONObject;

import java.awt.Color;

/**
 * A class that encapsulates a light
 */

public class Light implements Exportable {
    private double x;
    private double y;
    private double z;
    private double brightness = 0;
    private Color color = Color.WHITE;

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
        this(lx, ly, lz, bright, Color.WHITE);
    }

    /**
     * Make a white light at the given position with the given brightness
     */
    public Light(Point3d pos, double bright) {
        this(pos.getX(), pos.getY(), pos.getZ(), bright, Color.WHITE);
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

    public static Light fromJson(String json) {
        JSONObject lightJson = new JSONObject(json);

        JSONObject positionJson = lightJson.getJSONObject("position");
        JSONObject colorJson = lightJson.getJSONObject("color");

        Point3d position = new Point3d(positionJson.getDouble("x"), positionJson.getDouble("y"), positionJson.getDouble("z"));
        double brightness = lightJson.getDouble("brightness");
        Color color = new Color(colorJson.getInt("r"), colorJson.getInt("g"), colorJson.getInt("b"));

        return new Light(position, brightness, color);
    }

    @Override
    public String toJson() {
        return String.join("\n",
                "{",
                this.getPosition().toTriple().toJsonObjectWithLabel("position", 1) + ",",
                "  \"brightness\": " + this.getBrightness() + ",",
                JsonUtils.colorToJson(this.color, 1),
                "}"
        );
    }
}
