import org.junit.Test;
import org.martykane.Quad3d;
import org.martykane.Sphere;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class SphereTest {
    @Test
    public void canExportToObject() {
        Sphere test = new Sphere(1.0, 2.0, 3.0, 6.0);
        test.setName("Test Sphere");
        test.setColor(new Color(0, 0, 128));

        String expectedJson = String.join("\n",
                "{",
                "  \"name\": \"Test Sphere\",",
                "  \"color\": {",
                "    \"r\": 0,",
                "    \"g\": 0,",
                "    \"b\": 128",
                "  },",
                "  \"diffuse\": 0.0,",
                "  \"center\": {",
                "    \"x\": 1.0,",
                "    \"y\": 2.0,",
                "    \"z\": 3.0",
                "  },",
                "  \"radius\": 6.0",
                "}");

        assertEquals(expectedJson, test.toJson());
    }
}
