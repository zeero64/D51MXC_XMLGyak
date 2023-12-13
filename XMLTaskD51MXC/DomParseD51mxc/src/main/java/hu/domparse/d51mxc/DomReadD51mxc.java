package hu.domparse.d51mxc;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;

public class DomReadD51mxc {

    // A függvény átvesz egy beolvasott dokumentumot (argumentumként) és egy egy kifejezést a lekérdezéshez
    public static void printAdat(Document document, String expression, String node)
    {
 
            try {
    
                // Szülő objektum lekérdezése
                Element rootElement = document.getDocumentElement();
    
                // Gyermek objektum(ok) lekérdezése
                NodeList dataList = rootElement.getElementsByTagName(expression);
    
                // Kilistázom a lekérdezett objektumokat
                for (int i = 0; i < dataList.getLength(); i++) {
                    Element databaseElement = (Element) dataList.item(i);
                    String title = getTextContent(databaseElement, node);
                    if(title != "N/A")
                    {
                        System.out.println(node + ": " + title);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

            // Listázófüggvény
            private static String getTextContent(Element parentElement, String childTagName) {
                NodeList nodeList = parentElement.getElementsByTagName(childTagName);
                if (nodeList.getLength() > 0) {
                    return nodeList.item(0).getTextContent();
                } else {
                    return "N/A";
                }
            }


}
