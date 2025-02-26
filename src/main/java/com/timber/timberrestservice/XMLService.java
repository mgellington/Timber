package com.timber.timberrestservice;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Service for parsing the downloaded CAPEC XML file (3000.xml)
public class XMLService {
    List<Attack> attacks;
    List<Category> categories;

    // PARSES ATTACKS, RETURNS LIST OF ATTACKS WITH NECESSARY INFORMATION
    public List<Attack> parseAttacks() {

        attacks = new ArrayList<>();

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("src/main/resources/3000.xml"));

            // normalize XML response
            doc.getDocumentElement().normalize();

            NodeList attackList = doc.getElementsByTagName("Attack_Pattern");

            //loop all available nodes
            for (int i = 0; i < attackList.getLength(); i++) {

                Node node = attackList.item(i);

                // Get name and ID
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    Attack attack = new Attack(
                        elem.getAttribute("Name"),
                        Integer.parseInt(elem.getAttribute("ID"))
                    );

                    // Description
                    if (elem.getElementsByTagName("Description").getLength() > 0) {
                        attack.setDescription(elem.getElementsByTagName("Description").item(0).getTextContent());
                    } else {
                        attack.setDescription("");
                    }

                    // Likelihood
                    if (elem.getElementsByTagName("Likelihood_Of_Attack").getLength() > 0) {
                        attack.setLikelihood(elem.getElementsByTagName("Likelihood_Of_Attack").item(0).getTextContent());
                    } else {
                        attack.setLikelihood("");
                    }

                    // Severity
                    if (elem.getElementsByTagName("Typical_Severity").getLength() > 0) {
                        attack.setSeverity(elem.getElementsByTagName("Typical_Severity").item(0).getTextContent());
                    } else {
                        attack.setSeverity("");
                    }

                    // Related attacks (list of children)
                    NodeList relatedAttacks = elem.getElementsByTagName("Related_Attack_Pattern");
                    for (int j = 0; j < relatedAttacks.getLength(); j++) {
                        Node relatedNode = relatedAttacks.item(j);
                        if(relatedNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element parent = (Element) relatedNode;
                            if (parent.getAttribute("Nature").equals("ChildOf")) {
                                String parentId = parent.getAttribute("CAPEC_ID");
                                attack.addParentAttacks(parentId);
                            }
                        }
                            
                    }


                    attacks.add(attack);
                }

                
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Changes attack ids to name to allow searching and displaying
        for (Attack parent : attacks) {
            int pid = parent.getCapecID();
            for (Attack child : attacks) {
                List<String> parentAttacks = child.getParentAttacks();
                for (String stringID : parentAttacks) {
                    int id = Integer.parseInt(stringID);
                    if (pid == id) {
                        parent.addChildAttacks(child.getName());
                    }
                }
            }
        }

        return attacks;

    }


    // PARSES DOMAIN CATEGORIES, RETURNS LIST OF CATEGORIES (WITH CORRESPONDING META-ATTACKS)
    public List<Category> parseCategory() {

        categories = new ArrayList<>();
        attacks = parseAttacks();


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

                // Gets name and ID
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    Category category = new Category(
                            Integer.parseInt(elem.getAttribute("ID")),
                            elem.getAttribute("Name")
                    );

                    // Associates list of meta-attacks with each category
                    NodeList relationships = elem.getElementsByTagName("Relationships");
                    Element relationshipLine = (Element) relationships.item(0);
                    NodeList members = relationshipLine.getElementsByTagName("Has_Member");
                    for (int j = 0; j < members.getLength(); j++) {
                        Node memberNode = members.item(j);
                        if(memberNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element member = (Element) memberNode;
                            String memberId = member.getAttribute("CAPEC_ID");
                            category.addMember(memberId);
                        }
                         
                    }



                    categories.add(category);
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