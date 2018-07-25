import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.martykane.Triple;

public class TripleTest {
    @Test
    public void exportsToJsonArrayByDefault() {
        Triple test = new Triple(1, 2, 3);

        String expectedJson = "[1.0, 2.0, 3.0]";

        assertEquals(expectedJson, test.toJson());
    }

    @Test
    public void canExportToObjectWithLabel() {
        Triple test = new Triple(2, 3, 4);

        String expectedJson = String.join("\n",
                "\"foo\": {",
                "  \"x\": 2.0,",
                "  \"y\": 3.0,",
                "  \"z\": 4.0",
                "}");

        assertEquals(expectedJson, test.toJsonObjectWithLabel("foo"));
    }

    @Test
    public void canExportToObbjectWithLabelAndIndentLevel() {
        Triple test = new Triple(3, 4, 5);

        String expectedJson = String.join("\n",
                "  \"foo\": {",
                "    \"x\": 3.0,",
                "    \"y\": 4.0,",
                "    \"z\": 5.0",
                "  }");

        assertEquals(expectedJson, test.toJsonObjectWithLabel("foo", 1));
    }

    @Test
    public void canExportToObbjectWithLabelAndIndentLevelTwo() {
        Triple test = new Triple(4, 5, 6);

        String expectedJson = String.join("\n",
                "    \"foo\": {",
                "      \"x\": 4.0,",
                "      \"y\": 5.0,",
                "      \"z\": 6.0",
                "    }");

        assertEquals(expectedJson, test.toJsonObjectWithLabel("foo", 2));
    }
}
