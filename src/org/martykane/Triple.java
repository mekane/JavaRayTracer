package org.martykane;

import java.awt.*;
import java.util.Collections;

/**
 * A class that encapsulates a group of 3 doubles
 * <p>
 * They can be referenced by X,Y,Z or A,B,C or 1,2,3 or R,G,B
 */

public class Triple implements Exportable {
    double x;
    double y;
    double z;

    public Triple() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Triple(double nx, double ny, double nz) {
        this.x = nx;
        this.y = ny;
        this.z = nz;
    }

    public Triple(Color c) {
        this.x = c.getRed();
        this.y = c.getGreen();
        this.z = c.getBlue();
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

    //ABC
    public double getA() {
        return this.x;
    }

    public double getB() {
        return this.y;
    }

    public double getC() {
        return this.z;
    }

    //123
    public double get1() {
        return this.x;
    }

    public double get2() {
        return this.y;
    }

    public double get3() {
        return this.z;
    }

    //RGB
    public double getRed() {
        return this.x;
    }

    public double getGreen() {
        return this.y;
    }

    public double getBlue() {
        return this.z;
    }

    public static Triple fromJson(String json) {
        return new Triple(0, 0, 0);
    }

    @Override
    public String toJson() {
        return String.format("[" + this.x + ", " + this.y + ", " + this.z + "]");
    }

    public String toJsonObjectWithLabel(String label) {
        return this.toJsonObjectWithLabel(label, 0);
    }

    public String toJsonObjectWithLabel(String label, int indentLevel) {
        String indent = "";
        if (indentLevel > 0)
            indent = String.join("", Collections.nCopies(indentLevel, "  "));

        return String.join("\n",
                indent + "\"" + label + "\": {",
                indent + "  \"x\": " + this.x + ",",
                indent + "  \"y\": " + this.y + ",",
                indent + "  \"z\": " + this.z,
                indent + "}");
    }
}
