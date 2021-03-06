import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.martykane.Camera;
import org.martykane.Point3d;
import org.martykane.Ray3d;

public class CameraTest {

    //getN returns eye minus look
    //getU returns up cross n
    //getV returns n cross u
    //getPixel sets up a ray for the column and row

    @Test
    public void exportsToJson() {
        Camera testCam = new Camera(1.0, 2.0, 3.0,
                4.0, 5.0, 6.0,
                7.0, 8.0, 9.0);

        String expectedJson = String.join("\n",
                "{",
                "  \"position\": {",
                "    \"x\": 1.0,",
                "    \"y\": 2.0,",
                "    \"z\": 3.0",
                "  },",
                "  \"center\": {",
                "    \"x\": 4.0,",
                "    \"y\": 5.0,",
                "    \"z\": 6.0",
                "  },",
                "  \"up\": {",
                "    \"x\": 7.0,",
                "    \"y\": 8.0,",
                "    \"z\": 9.0",
                "  }",
                "}");

        assertEquals(expectedJson, testCam.toJson());
    }

    @Test
    public void importsFromJson() {
        double delta = .00001;

        String camJson = "{\"center\":{\"x\":50,\"y\":51,\"z\":45}," +
                "\"position\":{\"x\":21,\"y\":22,\"z\":23}," +
                "\"up\":{\"x\":10,\"y\":11,\"z\":12}}";

        Camera actualCamera = Camera.fromJson(camJson);

        Point3d actualLookPoint = actualCamera.getLookPoint();
        Point3d actualPosition = actualCamera.getPosition();
        Ray3d actualUpVector = actualCamera.getUpVector();

        assertEquals(50, actualLookPoint.getX(), delta);
        assertEquals(51, actualLookPoint.getY(), delta);
        assertEquals(45, actualLookPoint.getZ(), delta);

        assertEquals(21, actualPosition.getX(), delta);
        assertEquals(22, actualPosition.getY(), delta);
        assertEquals(23, actualPosition.getZ(), delta);

        assertEquals(10, actualUpVector.getX(), delta);
        assertEquals(11, actualUpVector.getY(), delta);
        assertEquals(12, actualUpVector.getZ(), delta);
    }
}
