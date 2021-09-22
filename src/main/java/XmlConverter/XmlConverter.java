package XmlConverter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.ArrayList;

public class XmlConverter {
    public XmlConverter() {
    }

    public void convertFile(ArrayList<ArrayList<String>> dataList) throws IOException, XMLStreamException, TransformerException {
        String filePath = "src/main/resources/writeFile/data.xml";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writeXml(out, dataList);

        String xml = new String(out.toByteArray(), StandardCharsets.UTF_8);
        String prettyPrintXML = formatXML(xml);

        Files.writeString(Paths.get("src/main/resources/writeFile/data.xml"),
                prettyPrintXML, StandardCharsets.UTF_8);

    }

    private void writeXml(ByteArrayOutputStream out, ArrayList<ArrayList<String>> dataList) throws XMLStreamException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = output.createXMLStreamWriter(out);
        writer.writeStartDocument("utf-8", "1.0");

        // <Players>
        writer.writeStartElement("Players");
        ArrayList<String> headerElement = formatHeader(dataList);
        for (int i = 1; i < headerElement.size(); i++) {
            writer.writeStartElement("Info");
            for (int j = 0; j < headerElement.size(); j++) {
                String temp = headerElement.get(j);
                writer.writeStartElement(temp);
                if (j >= dataList.get(i).size()) {
                    writer.writeCharacters("#N/A");
                } else {
                    writer.writeCharacters(dataList.get(i).get(j));
                }
                writer.writeEndElement();
            }
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.flush();
        writer.close();
    }

    private String formatXML(String xml) throws TransformerException {
        // write data to xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print by indention
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // add standalone="yes", add line break before the root element
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

        StreamSource source = new StreamSource(new StringReader(xml));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));

        return output.toString();
    }

    public ArrayList<String> formatHeader(ArrayList<ArrayList<String>> dataList) {
        ArrayList<String> temp = dataList.get(0);
        for (int i = 0; i < temp.size(); i++) {
            String format = temp.get(i);
            format = format.replaceAll("\\s", "");
            format = format.replaceAll("[(){}]", "");
            format = format.replaceAll("/", "");
            format = format.toLowerCase();

            for (int j = 0; j < format.length(); j++) {
                Boolean flag = Character.isDigit(format.charAt(j));
                if (flag) {
                    Character dig = format.charAt(j);
                    String temdig = Character.toString(dig);
                    format = format.replace(temdig, "");
                    format = format.replaceAll("^0*", "");
                }
            }
            temp.set(i, format);
        }
        return temp;
    }
}
