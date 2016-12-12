package bluoh.feed.service.impl;

import bluoh.feed.model.Horoscope;
import bluoh.feed.service.MessageParser;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Ashutosh on 11-12-2016.
 */
@Service
public class XMLMessageParser implements MessageParser {

    @Override
    public Horoscope parse(String uri) {
        try {
            Horoscope horoscope = new Horoscope();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(uri);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("item");
            Node nNode = nList.item(0);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent().split(" ")[0]);
                System.out.println("Description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
                horoscope.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent().split(" ")[0]);
                horoscope.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
            }
            return horoscope;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}