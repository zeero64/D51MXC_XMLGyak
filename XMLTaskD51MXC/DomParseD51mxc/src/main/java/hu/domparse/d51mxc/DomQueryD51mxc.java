package hu.domparse.d51mxc;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DomQueryD51mxc
{
    public static void main(String[] args) {
        try {

            // Beolvasom az adatbázisfájlta projekt "resource" mappájából
            URL url = DomQueryD51mxc.class.getClassLoader().getResource("XMLD51MXC.xml");

            // Megnyitom a fájlt
            File file = new File(url.toURI());
            FileInputStream fileInputStream = new FileInputStream(file);

            // Használom a szerkesztő könyvtárat
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileInputStream);

            // Normalizálom a fájlom (előtte utána üres karektereket levágom stb...)
            document.normalize();

            System.out.println("Kilistázom a neveket:");
            DomReadD51mxc.printAdat(document, "szemely", "teljes_nev");

            System.out.println("Kilistázom az elérhetőségeket:");
            DomReadD51mxc.printAdat(document, "elerhetoseg", "eleres");

            System.out.println("Kilistázom a számlák egyenlegeit:");
            DomReadD51mxc.printAdat(document, "szamla", "egyenleg");

            System.out.println("Kilistázom a tranzakciókat:");
            DomReadD51mxc.printAdat(document, "tranzakcio", "osszeg");

            System.out.println("Kilistázom az igazolványokat:");
            DomReadD51mxc.printAdat(document, "okirat", "tipus");




        }
        catch (URISyntaxException | ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }


}
