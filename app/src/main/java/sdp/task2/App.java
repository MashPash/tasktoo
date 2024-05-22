package sdp.task2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList recordList = doc.getElementsByTagName("record");
            for (int i = 0; i < recordList.getLength(); i++) {
                Node recordNode = recordList.item(i);
                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element recordElement = (Element) recordNode;
                    JsonObject jsonObject = new JsonObject();

                    for (String field : selectedFields) {
                        NodeList fieldList = recordElement.getElementsByTagName(field);
                        if (fieldList.getLength() > 0) {
                            String fieldValue = fieldList.item(0).getTextContent();
                            jsonObject.addProperty(field, fieldValue);
                        }
                    }
                    if (jsonObject.size() > 0) {
                        jsonArray.add(jsonObject);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void parseXmlFileAndWriteToFile(String xmlFilePath, String outputFilePath, List<String> selectedFields) {
        JsonArray jsonArray = parseXmlFile(xmlFilePath, selectedFields);
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
        List<String> selectedFields = List.of(fieldsInput.split("\\s*,\\s*"));

        String xmlFilePath = "src/main/resources/data.xml";
        String outputFilePath = "src/main/resources/output.json";
        app.parseXmlFileAndWriteToFile(xmlFilePath, outputFilePath, selectedFields);

        scanner.close();
    }
}
