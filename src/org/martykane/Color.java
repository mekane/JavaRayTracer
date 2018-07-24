package org.martykane;

import java.util.Collections;

public class Color implements Exportable {
    private int red;
    private int green;
    private int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    @Override
    public String toJson() {
        return toJson(0);
    }

    public String toJson(int indentLevel) {
        String indent = ""; //TODO: extract this
        if (indentLevel > 0)
            indent = String.join("", Collections.nCopies(indentLevel, "  "));

        return String.join("\n",
                indent + "color: {",
                indent + "  r: " + this.red + ",",
                indent + "  g: " + this.green + ",",
                indent + "  b: " + this.blue,
                indent + "}"
        );
    }

    @Override
    public Object fromJson() {
        return null;
    }
}
