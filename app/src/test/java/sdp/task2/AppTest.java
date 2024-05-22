package sdp.task2;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testParseXmlFile() {
        App classUnderTest = new App();
        String testFilePath = "src/test/resources/testDataJson.xml";
        List<String> selectedFields = List.of("name", "country", "postalZip", "region", "address", "list");

        JsonArray result = classUnderTest.parseXmlFile(testFilePath, selectedFields);

        // Verify the JsonArray is not null and not empty
        assertNotNull(result, "Resulting JsonArray is null");
        assertFalse(result.size() == 0, "Resulting JsonArray is empty");

        // Log the size of the array
        System.out.println("JsonArray size: " + result.size());

        // Verify the contents of the first object
        if (result.size() > 0) {
            JsonObject firstObject = result.get(0).getAsJsonObject();
            assertNotNull(firstObject, "First JsonObject is null");
            assertEquals("Xena Bradford", firstObject.get("name").getAsString(), "Name field does not match");
            assertEquals("Brazil", firstObject.get("country").getAsString(), "Country field does not match");
            assertEquals("17716", firstObject.get("postalZip").getAsString(), "PostalZip field does not match");
            assertEquals("Bahia", firstObject.get("region").getAsString(), "Region field does not match");
            assertEquals("Ap #593-1566 Lectus Ave", firstObject.get("address").getAsString(), "Address field does not match");
            assertEquals("61, 1, 41, 37, 13", firstObject.get("list").getAsString(), "List field does not match");
        }
    }

    @Test
    public void testParseXmlFileAndWriteToFile() {
        App classUnderTest = new App();
        String testFilePath = "src/test/resources/testDataJson.xml";
        String outputFilePath = "src/test/resources/output.json";
        List<String> selectedFields = List.of("name", "country", "postalZip", "region", "address", "list");

        classUnderTest.parseXmlFileAndWriteToFile(testFilePath, outputFilePath, selectedFields);

        // Verify that the output file exists
        File outputFile = new File(outputFilePath);
        assertTrue(outputFile.exists(), "Output file does not exist");

        // Verify the contents of the output file
        try (FileReader reader = new FileReader(outputFile)) {
            JsonArray result = JsonParser.parseReader(reader).getAsJsonArray();
            assertNotNull(result, "Resulting JsonArray is null");
            assertFalse(result.size() == 0, "Resulting JsonArray is empty");

            // Log the size of the array
            System.out.println("JsonArray size: " + result.size());

            if (result.size() > 0) {
                JsonObject firstObject = result.get(0).getAsJsonObject();
                assertNotNull(firstObject, "First JsonObject is null");
                assertEquals("Xena Bradford", firstObject.get("name").getAsString(), "Name field does not match");
                assertEquals("Brazil", firstObject.get("country").getAsString(), "Country field does not match");
                assertEquals("17716", firstObject.get("postalZip").getAsString(), "PostalZip field does not match");
                assertEquals("Bahia", firstObject.get("region").getAsString(), "Region field does not match");
                assertEquals("Ap #593-1566 Lectus Ave", firstObject.get("address").getAsString(), "Address field does not match");
                assertEquals("61, 1, 41, 37, 13", firstObject.get("list").getAsString(), "List field does not match");
            }
        } catch (Exception e) {
            fail("Exception thrown during test: " + e.getMessage());
        }
    }
}
