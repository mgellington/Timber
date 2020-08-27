package com.timber.timberrestservice;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
// import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class XMLService {

    // private final Logger logger = LoggerFactory.getLogger(XMLService.class);


    public List<Category> parseCategory() {

        List<Category> categories = new ArrayList<>();

        try {
            // retrieves xml
            // String URL = "src/main/resources/3000.xml";

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("src/main/resources/3000.xml"));

            // normalize XML response
            doc.getDocumentElement().normalize();

            //read students list
            NodeList categoryList = doc.getElementsByTagName("Category");

            

            //loop all available student nodes
            for (int i = 0; i < categoryList.getLength(); i++) {

                Node node = categoryList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    Category category = new Category(
                            Integer.parseInt(elem.getAttribute("ID")),
                            elem.getAttribute("Name")
                    );
                    
                    NodeList relationships = elem.getElementsByTagName("Relationships");
                    Element relationshipLine = (Element) relationships.item(0);
                    NodeList members = relationshipLine.getElementsByTagName("Has_Member");
                    for (int j = 0; j < members.getLength(); j++) {
                        Node memberNode = members.item(j);
                        Element member = (Element) memberNode;
                        String memberId = member.getAttribute("CAPEC_ID");
                        category.addMember(memberId); 
                    }



                    categories.add(category);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        

        return categories;
    }

    
}