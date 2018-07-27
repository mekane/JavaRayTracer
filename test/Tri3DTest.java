import org.junit.Test;
import org.martykane.Point3d;
import org.martykane.Quad3d;
import org.martykane.Tri3d;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class Tri3DTest {
    @Test
    public void canExportToObject() {
        Tri3d test = new Tri3d(11, 12, 13, 21, 22, 23, 31, 32, 33);

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
                "  }",
                "}");

        assertEquals(expectedJson, test.toJson());
    }


    @Test
    public void importsFromJson() {
        double delta = 0.00001;

        String triJson = String.join("\n",
                "{",
                "  \"name\": \"Test Tri\",",
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
                "  }",
                "}");

        Tri3d actualTri = Tri3d.fromJson(triJson);

        Color actualColor = actualTri.getColor();

        Point3d v1 = actualTri.getV1();
        Point3d v2 = actualTri.getV2();
        Point3d v3 = actualTri.getV3();

        assertEquals("Test Tri", actualTri.getName());
        assertEquals(1.0, actualTri.getDiffuse(), delta);

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
    }
}
