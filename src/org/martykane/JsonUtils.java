package org.martykane;

import java.awt.Color;
import java.util.Collections;

public abstract class JsonUtils {
    public static String colorToJson(Color c) {
        return colorToJson(c, 0);
    }

    public static String colorToJson(Color c, int indentLevel) {
        String indent = ""; //TODO: extract this
        if (indentLevel > 0)
            indent = String.join("", Collections.nCopies(indentLevel, "  "));

        return String.join("\n",
                indent + "color: {",
                indent + "  r: " + c.getRed() + ",",
                indent + "  g: " + c.getGreen() + ",",
                indent + "  b: " + c.getBlue(),
                indent + "}"
        );
    }
}
