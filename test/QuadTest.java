import org.junit.Test;
import org.martykane.Quad3d;
import org.martykane.Triple;

import static org.junit.Assert.assertEquals;

public class QuadTest {
    @Test
    public void canExportToObject() {
        Quad3d test = new Quad3d(11, 12, 13, 21, 22, 23, 31, 32, 33, 41, 42, 43);

        String expectedJson = String.join("\n",
                "{",
                "  name: ,", //\"\"
                "  color: {",
                "    r: 255,",
                "    g: 255,",
                "    b: 255",
                "  },",
                "  diffuse: 0.0,",
                "  v1: {",
                "    x: 11.0,",
                "    y: 12.0,",
                "    z: 13.0",
                "  },",
                "  v2: {",
                "    x: 21.0,",
                "    y: 22.0,",
                "    z: 23.0",
                "  },",
                "  v3: {",
                "    x: 31.0,",
                "    y: 32.0,",
                "    z: 33.0",
                "  },",
                "  v4: {",
                "    x: 41.0,",
                "    y: 42.0,",
                "    z: 43.0",
                "  }",
                "}");

        assertEquals(expectedJson, test.toJson());
    }
}
