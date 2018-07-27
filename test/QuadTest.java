import org.junit.Test;
import org.martykane.Point3d;
import org.martykane.Quad3d;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class QuadTest {
    @Test
    public void canExportToObject() {
        Quad3d test = new Quad3d(11, 12, 13, 21, 22, 23, 31, 32, 33, 41, 42, 43);

        String expectedJson = String.join("\n",
                "{",
                "  \"name\": \"\",",
                "  \"color\": {",
                "    \"r\": 255,",
                "    \"g\": 255,",
                "    \"b\": 255",
                "  },",
                "  \"diffuse\": 0.0,",
                "  \"v1\": {",
                "    \"x\": 11.0,",
                "    \"y\": 12.0,",
                "    \"z\": 13.0",
                "  },",
                "  \"v2\": {",
                "    \"x\": 21.0,",
                "    \"y\": 22.0,",
                "    \"z\": 23.0",
                "  },",
                "  \"v3\": {",
                "    \"x\": 31.0,",
                "    \"y\": 32.0,",
                "    \"z\": 33.0",
                "  },",
                "  \"v4\": {",
                "    \"x\": 41.0,",
                "    \"y\": 42.0,",
                "    \"z\": 43.0",
                "  }",
                "}");

        assertEquals(expectedJson, test.toJson());
    }

    @Test
    public void importsFromJson() {
        double delta = 0.00001;

        String quadJson = String.join("\n",
                "{",
                "  \"name\": \"Name\",",
                "  \"color\": {",
                "    \"r\": 100,",
                "    \"g\": 200,",
                "    \"b\": 255",
                "  },",
                "  \"diffuse\": 1.0,",
                "  \"v1\": {",
                "    \"x\": 11.0,",
                "    \"y\": 12.0,",
                "    \"z\": 13.0",
                "  },",
                "  \"v2\": {",
                "    \"x\": 21.0,",
                "    \"y\": 22.0,",
                "    \"z\": 23.0",
                "  },",
                "  \"v3\": {",
                "    \"x\": 31.0,",
                "    \"y\": 32.0,",
                "    \"z\": 33.0",
                "  },",
                "  \"v4\": {",
                "    \"x\": 41.0,",
                "    \"y\": 42.0,",
                "    \"z\": 43.0",
                "  }",
                "}");

        Quad3d actualQuad = Quad3d.fromJson(quadJson);

        Color actualColor = actualQuad.getColor();

        Point3d v1 = actualQuad.getV1();
        Point3d v2 = actualQuad.getV2();
        Point3d v3 = actualQuad.getV3();
        Point3d v4 = actualQuad.getV4();

        assertEquals("Name", actualQuad.getName());
        assertEquals(1.0, actualQuad.getDiffuse(), delta);

        assertEquals(100, actualColor.getRed());
        assertEquals(200, actualColor.getGreen());
        assertEquals(255, actualColor.getBlue());

        assertEquals(11.0, v1.getX(), delta);
        assertEquals(12.0, v1.getY(), delta);
        assertEquals(13.0, v1.getZ(), delta);

        assertEquals(21.0, v2.getX(), delta);
        assertEquals(22.0, v2.getY(), delta);
        assertEquals(23.0, v2.getZ(), delta);

        assertEquals(31.0, v3.getX(), delta);
        assertEquals(32.0, v3.getY(), delta);
        assertEquals(33.0, v3.getZ(), delta);

        assertEquals(41.0, v4.getX(), delta);
        assertEquals(42.0, v4.getY(), delta);
        assertEquals(43.0, v4.getZ(), delta);
    }
}
