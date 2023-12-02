package hu.domparse.d51mxc;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;


public class DomWriteD51mxc {

    /**
     * Kiírom a dokumentumot egy fájlba
     * @param xmlDocument XML fájl
     * @throws TransformerException TransformerException
     */
    public static void write(Document document) throws TransformerException {

        document.normalizeDocument();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File("XMLD51MXC_uj.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);

        System.out.println("Az XML fájl sikeresen kiíródott!");
    }

    // Listázó
    public static void printNode(Source source, int depth) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // DOMSource átkonvertálása karakterlánccá
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            String xmlString = writer.toString();

            System.out.println("XML fa struktúra:");

            // XML karakterlánc kiíratása fa struktúrába
            String[] lines = xmlString.split("\n");
            for (String line : lines) {
                for (int i = 0; i < depth; i++) {
                    System.out.print("    "); // TAB
                }
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
