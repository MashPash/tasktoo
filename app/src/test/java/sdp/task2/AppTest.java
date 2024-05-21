package sdp.task2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class AppTest {
    @Test
    void testParseXmlFile() {
        App classUnderTest = new App();
        String testFilePath = "src/test/resources/test-data.xml";
        
        // Parse the XML file
        List<String> result = classUnderTest.parseXmlFile(testFilePath);
        
        // Print the result for diagnostics
        System.out.println("Parsed values: " + result);

        // Verify the result is not null and contains expected values
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Result should contain elements");

        // Add assertions based on the expected content of your test XML file
        assertTrue(result.get(0).contains("Name: Xena Bradford"), "First element should match the expected value");
        assertTrue(result.get(1).contains("Name: Zenaida Hensley"), "Second element should match the expected value");
    }
}
