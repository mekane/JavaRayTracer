import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.martykane.Camera;

public class CameraTest {
    @Test
    public void exportsToJson() {
        Camera testCam = new Camera(1.0, 2.0, 3.0,
                4.0, 5.0, 6.0,
                7.0, 8.0, 9.0);

        String expectedJson = String.join("\n",
                "{",
                "  position: {",
                "    x: 1.0,",
                "    y: 2.0,",
                "    z: 3.0",
                "  },",
                "  center: {",
                "    x: 4.0,",
                "    y: 5.0,",
                "    z: 6.0",
                "  },",
                "  up: {",
                "    x: 7.0,",
                "    y: 8.0,",
                "    z: 9.0",
                "  }",
                "}");

        assertEquals(expectedJson, testCam.toJson());
    }
}
