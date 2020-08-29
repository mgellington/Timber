package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

public class TemplateTree {

    List<StringNode> stringNodes;

    public TemplateTree() {
        buildList();
    }

    public void buildList() {

        stringNodes = new ArrayList<StringNode>();
        Attack child = new Attack("child");
        Attack parent = new Attack("parent");
        Node nullNode = new Node(child, parent);
        stringNodes.add(new StringNode(nullNode));
    }

    public List<StringNode> getList() {
        return stringNodes;
    }

    public List<String> getChildren() {
        List<String> children = new ArrayList<String>();
        for (StringNode node : stringNodes) {
            children.add(node.getChild());
        }

        return children;
    }

    // add or delete string node
    public void addStringNode(StringNode sn) {
        StringNode remove = null;
        for (StringNode node : stringNodes) {
            if (sn.getParent() == "" && !(sn.getChild() == "")) {
                if (node.getChild().equals(sn.getChild())) {
                    remove = node;
                }
                
            } 
        }

        if (remove != null) {
            removeAttack(remove.getChild());
        } else {
            if (!(sn.getParent().equals("Select Attack") 
            || sn.getChild().equals("Select Attack"))) {
                stringNodes.add(sn);
            }
            
        }
        

    }

    public void removeAttack(String attackName) {
        List<StringNode> remove = new ArrayList<StringNode>();
        for (StringNode node : stringNodes) {
            if (node.getChild().equals(attackName)) {
                remove.add(node);
            } else if (node.getParent().equals(attackName)) {
                remove.add(node);
            }
        }

        for (StringNode node : remove) {
            stringNodes.remove(node);
        }
    }

    
}