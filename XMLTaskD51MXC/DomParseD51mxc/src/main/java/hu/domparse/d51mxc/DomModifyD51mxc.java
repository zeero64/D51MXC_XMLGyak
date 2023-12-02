package hu.domparse.d51mxc;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DomModifyD51mxc {

    /**
     * Segítő funkció, hogy lekérdezhessem a Node-ot
     * @param xmlDocument XML fájl
     * @param expression XPATH elérés
     * @return  NodeList amely tartalmazza az XPATH eredményét
     * @throws XPathExpressionException XPathExpressionException
     */
    private static NodeList getNodeList(Document xmlDocument, String expression) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = null;

        nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);

        return nodeList;
    }

    /**
     * Összefűzi a Node-ot minden Node lsita elemmel
     * @param xmlDocument XML fájl
     * @param expression XPATH elérés
     * @param element Node elem
     * @throws XPathExpressionException XPathExpressionException
     */
    private static void addElement(Document xmlDocument, String expression, Node element) throws XPathExpressionException {
        NodeList nodeList = getNodeList(xmlDocument, expression);

        if (nodeList.getLength() == 0) {
            System.out.println("A Node lista üres volt, nincs mit hozzáadni!");
        } else {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                node.appendChild(element.cloneNode(true));
            }

            System.out.println("Elemek hozzáadva!");
        }
    }

    /**
     * Kitörli a gyermek Node-ot
     * @param xmlDocument XML fájl
     * @param expression kifejezés
     * @param elementName a törölni kívánt Node
     * @throws XPathExpressionException XPathExpressionException
     */
    private static void removeElement(Document xmlDocument, String expression, String elementName) throws XPathExpressionException {
        NodeList nodeList = getNodeList(xmlDocument, expression);

        if (nodeList.getLength() == 0) {
            System.out.println("A Node lista üres volt, nincs mit törölni!");
        } else {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                    Node childNode = node.getChildNodes().item(j);

                    if (childNode.getNodeName().equals(elementName)) {
                        node.removeChild(childNode);
                    }
                }
            }

            System.out.println("Elemek törölve!");
        }
    }

    /**
     * Módosítja a kívánt elemet
     * @param xmlDocument XML fájl
     * @param expression kifejezés
     * @param elementName a törölni kívánt Node
     * @throws XPathExpressionException XPathExpressionException
     */
    private static void replaceElement(Document xmlDocument, String expression, Node elementName) throws XPathExpressionException {

        removeElement(xmlDocument, expression, elementName.getNodeName());
        addElement(xmlDocument, expression, elementName);


    }

    public static void main(String[] args)
    {
        try {


            // Beolvasom az adatbázisfalom a projekt "resource" mappájából
            URL url = DomModifyD51mxc.class.getClassLoader().getResource("XMLD51MXC.xml");

            // Megnyitom a fájlt
            File file = new File(url.toURI());
            FileInputStream fileInputStream = new FileInputStream(file);

            // Használom a szerkesztő könyvtárat
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(fileInputStream);

            // Törlök egy elemet
            removeElement(document, "/database/szemelyek/szemely[@id=1]", "szuletesi_datum");

            // Hozzáadok egy elemet
            Node nodeAdd = document.createElement("szuletesi_datum");
            nodeAdd.appendChild(document.createTextNode("1999.09.19"));
            addElement(document, "/database/szemelyek/szemely[@id=1]", nodeAdd);

            // Létrehozom a szülő elemet
            Node nev = document.createElement("teljes_nev");

            Node adat1 = document.createElement("vezetek_nev");
            adat1.appendChild(document.createTextNode("Átok"));

            Node adat2 = document.createElement("kereszt_nev");
            adat2.appendChild(document.createTextNode("Áron"));

            // Összefűzöm a gyermek elemeket
            nev.appendChild(adat1);
            nev.appendChild(adat2);

            // Felülírom a meglévőt
            replaceElement(document, "/database/szemelyek/szemely[@id=1]", nev);

            // Kiíratom egy új fájlba
            DomWriteD51mxc.write(document);

            // Kiíratom a képernyőre
            DomWriteD51mxc.printNode(new DOMSource(document), 0);


        }
        catch (URISyntaxException | IOException | ParserConfigurationException | SAXException | TransformerException | XPathExpressionException e)
        {
            e.printStackTrace();
        }
    }

}
