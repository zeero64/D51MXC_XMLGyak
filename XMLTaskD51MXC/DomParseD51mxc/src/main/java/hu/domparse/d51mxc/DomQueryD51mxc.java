package hu.domparse.d51mxc;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
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

            // XPATH lekérdezések

            System.out.println("Kiválasztom az utolsó személyt (függvénnyel):");
            DomReadD51mxc.printnode(document, "/database/szemelyek/szemely[last()]");

            System.out.println("Kiválasztom a második személyt:");
            DomReadD51mxc.printnode(document, "/database/szemelyek/szemely[2]");

            System.out.println("Kilistázom az összes számlát:");
            DomReadD51mxc.printnode(document, "/database/szamlak/szamla");

            System.out.println("Kilistázom az összes tranzakciót:");
            DomReadD51mxc.printnode(document, "/database/tranzakciok/tranzakcio");

            System.out.println("Kilistázom az 1000 forintnál nagyobb tranzakciókat:");
            DomReadD51mxc.printnode(document, "/database/tranzakciok/tranzakcio[osszeg>1000]");

        }
        catch (URISyntaxException | ParserConfigurationException | IOException | SAXException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }


}
