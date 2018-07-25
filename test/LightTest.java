import org.junit.Test;
import org.martykane.Light;

import java.awt.Color;

import static org.junit.Assert.assertEquals;

public class LightTest {
    @Test
    public void canExportToJson() {
        Light test = new Light(1.0, 2.0, 3.0, 9.0, new Color(16, 32, 64));

        String expectedJson = String.join("\n",
                "{",
                "  position: {",
                "    x: 1.0,",
                "    y: 2.0,",
                "    z: 3.0",
                "  },",
                "  brightness: 9.0,",
                "  color: {",
                "    r: 16,",
                "    g: 32,",
                "    b: 64",
                "  }",
                "}");

        assertEquals(expectedJson, test.toJson());
    }
}
