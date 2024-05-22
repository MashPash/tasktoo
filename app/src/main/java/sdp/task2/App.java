package sdp.task2;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class App {

    public JsonArray parseXmlFile(String filePath, List<String> selectedFields) {
        JsonArray jsonArray = new JsonArray();
        try {
            File fXmlFile = new File(filePath);
            if (!fXmlFile.exists()) {
                System.err.println("File not found: " + filePath);
                return jsonArray;
            }
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            XmlHandler handler = new XmlHandler(selectedFields);
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(filePath));

            jsonArray = handler.getJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void parseXmlFileAndWriteToFile(String xmlFilePath, String outputFilePath, List<String> selectedFields) {
        JsonArray jsonArray = parseXmlFile(xmlFilePath, selectedFields);
        if (jsonArray.size() == 0) {
            System.err.println("No valid records found to write to file.");
            return;
        }
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            Gson gson = new Gson();
            gson.toJson(jsonArray, writer);
            System.out.println("Data successfully written to " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        App app = new App();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter comma-separated fields you want to include (e.g., name, country): ");
        String fieldsInput = scanner.nextLine();
        if (fieldsInput.trim().isEmpty()) {
            System.err.println("No fields provided. Exiting.");
            return;
        }

        List<String> selectedFields = List.of(fieldsInput.split("\\s*,\\s*"));

        String xmlFilePath = "src/main/resources/data.xml";
        String outputFilePath = "src/main/resources/output.json";

        app.parseXmlFileAndWriteToFile(xmlFilePath, outputFilePath, selectedFields);

        scanner.close();
    }
}
