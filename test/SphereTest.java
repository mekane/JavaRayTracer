import org.junit.Test;
import org.martykane.Point3d;
import org.martykane.Sphere;
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


    @Test
    public void importsFromJson() {
        double delta = 0.00001;

        String sphereJson = String.join("\n",
                "{",
                "  \"name\": \"Test Sphere\",",
                "  \"color\": {",
                "    \"r\": 100,",
                "    \"g\": 200,",
                "    \"b\": 128",
                "  },",
                "  \"diffuse\": 2.0,",
                "  \"center\": {",
                "    \"x\": 11.0,",
                "    \"y\": 12.0,",
                "    \"z\": 13.0",
                "  },",
                "  \"radius\": 6.0",
                "}");

        Sphere actualSphere = Sphere.fromJson(sphereJson);
        Color actualColor = actualSphere.getColor();
        Point3d position = actualSphere.getPosition();

        assertEquals("Test Sphere", actualSphere.getName());

        assertEquals(100, actualColor.getRed());
        assertEquals(200, actualColor.getGreen());
        assertEquals(128, actualColor.getBlue());

        assertEquals(2.0, actualSphere.getDiffuse(), delta);

        assertEquals(11.0, position.getX(), delta);
        assertEquals(12.0, position.getY(), delta);
        assertEquals(13.0, position.getZ(), delta);

        assertEquals(6.0, actualSphere.getRadius(), delta);
    }
}
