import org.junit.Test;
import org.martykane.Light;
import org.martykane.Point3d;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

public class LightTest {
    @Test
    public void canExportToJson() {
        Light test = new Light(1.0, 2.0, 3.0, 9.0, new Color(16, 32, 64));

        String expectedJson = String.join("\n",
                "{",
                "  \"position\": {",
                "    \"x\": 1.0,",
                "    \"y\": 2.0,",
                "    \"z\": 3.0",
                "  },",
                "  \"brightness\": 9.0,",
                "  \"color\": {",
                "    \"r\": 16,",
                "    \"g\": 32,",
                "    \"b\": 64",
                "  }",
                "}");

        assertEquals(expectedJson, test.toJson());
    }


    @Test
    public void importsFromJson() {
        double delta = .00001;

        String lightJson = "{" +
                "\"position\": {" +
                "  \"x\": 1.0," +
                "  \"y\": 2.0," +
                "  \"z\": 3.0" +
                "}," +
                "\"brightness\": 0.95," +
                "\"color\": {" +
                "  \"r\": 100," +
                "  \"g\": 200," +
                "  \"b\": 255" +
                "}" +
                "}";

        Light actualLight = Light.fromJson(lightJson);

        Point3d actualPosition = actualLight.getPosition();
        Color actualColor = actualLight.getColor();

        assertEquals(1.0, actualPosition.getX(), delta);
        assertEquals(2.0, actualPosition.getY(), delta);
        assertEquals(3.0, actualPosition.getZ(), delta);

        assertEquals(.95, actualLight.getBrightness(), delta);

        assertEquals(100, actualColor.getRed());
        assertEquals(200, actualColor.getGreen());
        assertEquals(255, actualColor.getBlue());
    }
}
