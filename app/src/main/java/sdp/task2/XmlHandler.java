package sdp.task2;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class XmlHandler extends DefaultHandler {
    private List<String> selectedFields;
    private JsonArray jsonArray = new JsonArray();
    private JsonObject currentRecord = null;
    private StringBuilder currentValue = new StringBuilder();

    private boolean fieldFoundOverall = false; // Flag to check if any field is found overall
    private boolean currentFieldSelected = false; // Flag to check if the current field is selected

    public XmlHandler(List<String> selectedFields) {
        this.selectedFields = selectedFields;
    }

    public JsonArray getJsonArray() {
        if (!fieldFoundOverall) {
            System.err.println("None of the selected fields were found in the XML records.");
        }
        return jsonArray;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("record")) {
            currentRecord = new JsonObject();
        } else if (currentRecord != null && selectedFields.contains(qName)) {
            currentFieldSelected = true;
            currentValue.setLength(0); // Clear the current value buffer
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("record")) {
            if (currentRecord.size() > 0) {
                jsonArray.add(currentRecord);
            }
            currentRecord = null;
        } else if (currentRecord != null && selectedFields.contains(qName)) {
            currentRecord.addProperty(qName, currentValue.toString().trim());
            fieldFoundOverall = true;
            currentFieldSelected = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentFieldSelected) {
            currentValue.append(ch, start, length);
        }
    }
}
