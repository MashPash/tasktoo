package sdp.task2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

class AppTest {
    @Test
void testParseXmlFile() {
    App classUnderTest = new App();
    // Specify the path to a test XML file.
    String testFilePath = "src/test/resources/test-data.xml"; 

    // Prompt the user to input the fields they want to include.
    String fieldsInput = "name, country"; // You can change this to prompt the user
    List<String> selectedFields = Arrays.asList(fieldsInput.split("\\s*,\\s*"));

    // Parse the XML file
    List<String> result = classUnderTest.parseXmlFile(testFilePath, selectedFields);

    // Print the result for diagnostics
    System.out.println("Parsed values: " + result);

    // Verify the result is not null and contains expected values
    assertNotNull(result, "Result should not be null");
    assertFalse(result.isEmpty(), "Result should contain elements");
}

}
