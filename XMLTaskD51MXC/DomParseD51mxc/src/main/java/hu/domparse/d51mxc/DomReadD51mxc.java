package hu.domparse.d51mxc;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class DomReadD51mxc {

    // A függvény átvesz egy beolvasott dokumentumot (argumentumként) és egy egy kifejezést a lekérdezéshez
    public static void printnode(Document document, String expression) throws XPathExpressionException
    {
        // Létrehozom az XPATH lekérdezőt
        XPath xPath = XPathFactory.newInstance().newXPath();

        // Beolvasom az útvonalat, XML adatbázis szegmenst
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);

            // Ha üres nincs mit kilistázni
            if (nodeList.getLength() == 0)
            {

                System.out.println("Nem talalhato node, ellenorizd a kifejezest!");

            }
            // Ha egy bejegyzés akkor azt kilistázom
            else if (nodeList.getLength() == 1)
            {

                System.out.println(nodeList.item(0).getTextContent());

            }
            // Egyébként feltételezem, hogy több is van, ekkor végig megyek rajta
            else
            {
                // Beolvasom statikus adatként a hosszát
                Integer counter = nodeList.getLength();

                    for (int i = 0; i < counter; i++)
                    {

                        System.out.println(i + ".");
                        System.out.println(nodeList.item(i).getTextContent());

                    }
            }
        }


}
