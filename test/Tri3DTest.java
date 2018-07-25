import org.junit.Test;
import org.martykane.Tri3d;

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
}
