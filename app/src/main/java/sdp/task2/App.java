package sdp.task2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {

    // Method to parse the XML file and return the values
    public List<String> parseXmlFile(String filePath) {
        List<String> result = new ArrayList<>();
        try {
            File fXmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("record");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Extract each field
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String postalZip = eElement.getElementsByTagName("postalZip").item(0).getTextContent();
                    String region = eElement.getElementsByTagName("region").item(0).getTextContent();
                    String country = eElement.getElementsByTagName("country").item(0).getTextContent();
                    String address = eElement.getElementsByTagName("address").item(0).getTextContent();
                    String list = eElement.getElementsByTagName("list").item(0).getTextContent();

                    // Combine field values into a single string for easy testing
                    result.add("Name: " + name + ", Postal Zip: " + postalZip + ", Region: " + region +
                               ", Country: " + country + ", Address: " + address + ", List: " + list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Method to parse the XML file and print the values
    public void parseXmlFileAndPrint(String filePath) {
        List<String> values = parseXmlFile(filePath);
        for (String value : values) {
            System.out.println(value);
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.parseXmlFileAndPrint("src/main/resources/data.xml");
                          
    }
}
