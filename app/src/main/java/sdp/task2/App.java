package sdp.task2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public List<String> parseXmlFile(String filePath, List<String> selectedFields) {
        List<String> result = new ArrayList<>();
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

                    StringBuilder recordBuilder = new StringBuilder();
                    for (String field : selectedFields) {
                        NodeList fieldList = recordElement.getElementsByTagName(field);
                        if (fieldList.getLength() > 0) {
                            String fieldValue = fieldList.item(0).getTextContent();
                            recordBuilder.append(field).append(": ").append(fieldValue).append(", ");
                        }
                    }
                    // Remove trailing comma and space
                    if (recordBuilder.length() > 0) {
                        recordBuilder.deleteCharAt(recordBuilder.length() - 2);
                        result.add(recordBuilder.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void parseXmlFileAndWriteToFile(String xmlFilePath, String outputFilePath, List<String> selectedFields) {
        List<String> values = parseXmlFile(xmlFilePath, selectedFields);
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            for (String value : values) {
                writer.write(value + System.lineSeparator());
            }
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
        String outputFilePath = "src/main/resources/output.txt";
        app.parseXmlFileAndWriteToFile(xmlFilePath, outputFilePath, selectedFields);

        scanner.close();
    }
}
