package com.timber.timberrestservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLService {

    private final Logger logger = LoggerFactory.getLogger(XMLService.class);


    public List<Category> parseCategory() {

        List<Category> categories = new ArrayList<>();

        try {
            // retrieves xml
            String URL = "src/main/resources/3000.xml";

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(URL);

            // normalize XML response
            doc.getDocumentElement().normalize();

            //read students list
            NodeList nodeList = doc.getElementsByTagName("Category");

            

            //loop all available student nodes
            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    Category category = new Category(
                            Integer.parseInt(elem.getAttribute("ID")),
                            elem.getAttribute("Name")
                    );
                    categories.add(category);
                }
            }


        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return categories;
    }

    
}