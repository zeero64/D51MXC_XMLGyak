package hu.domparse.d51mxc;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;

public class DomReadD51mxc {

    // A függvény átvesz egy beolvasott dokumentumot (argumentumként) és egy egy kifejezést a lekérdezéshez
    public static void printAdat(Document document, String expression, String node)
    {
 
            try {
    
                // Get the root element (in this case, <bookstore>)
                Element rootElement = document.getDocumentElement();
    
                // Get a NodeList of all <book> elements
                NodeList dataList = rootElement.getElementsByTagName(expression);
    
                // Iterate over each <book> element and retrieve the title
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

            private static String getTextContent(Element parentElement, String childTagName) {
                NodeList nodeList = parentElement.getElementsByTagName(childTagName);
                if (nodeList.getLength() > 0) {
                    return nodeList.item(0).getTextContent();
                } else {
                    return "N/A";
                }
            }


}
