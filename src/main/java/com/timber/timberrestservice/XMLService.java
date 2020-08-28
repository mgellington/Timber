package com.timber.timberrestservice;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
// import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class XMLService {


    public List<Category> parseCategory() {

        List<Category> categories = new ArrayList<>();
        List<Attack> attacks = new ArrayList<>();

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("src/main/resources/3000.xml"));

            // normalize XML response
            doc.getDocumentElement().normalize();

            //read category list
            NodeList categoryList = doc.getElementsByTagName("Category");

            

            //loop all available nodes
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
                        if(node.getNodeType() == Node.ELEMENT_NODE) {
                            Element member = (Element) memberNode;
                            String memberId = member.getAttribute("CAPEC_ID");
                            category.addMember(memberId);
                        }
                         
                    }



                    categories.add(category);
                }
            }

            NodeList attackList = doc.getElementsByTagName("Attack_Pattern");

            //loop all available nodes
            for (int i = 0; i < attackList.getLength(); i++) {

                Node node = attackList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    Attack attack = new Attack(
                        elem.getAttribute("Name"),
                        Integer.parseInt(elem.getAttribute("ID"))
                    );

                    attacks.add(attack);
                }

                
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // changing attack id's to names
        for (Category category : categories) {
            List<String> members = category.getMembers();
            int index = 0;
            for (String stringID : members) {
                int id = Integer.parseInt(stringID);
                for(Attack attack : attacks) {
                    if (attack.getCapecID() == id) {
                        category.getMembers().set(index, attack.getName());
                    }
                }
                index++;
            }
        }
        

        return categories;
    }

    
}