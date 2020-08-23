package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

public class TemplateTree {

    int id;
    List<StringNode> stringNodes;

    public TemplateTree(int id) {
        this.id = id;
        buildList();
    }

    public TemplateTree() {
        this.id = 0;
        buildList();
    }

    public List<StringNode> buildList() {

        stringNodes = new ArrayList<StringNode>();
        Attack child = new Attack("child");
        Attack parent = new Attack("parent");
        Node nullNode = new Node(child, parent);
        stringNodes.add(new StringNode(nullNode));


        if (this.id == 0) {
            // blank tree
        } else
        if (this.id == 1) {
            // practice tree
            
            Attack a0 = new Attack("Open Safe");
            Attack a1 = new Attack("Pick Lock");

            Attack a2 = new Attack("Learn Combo");
            Attack a3 = new Attack("Find Written Combo");

            Node n0 = new Node(a0);
            Node n1 = new Node(a1, a0);
            Node n2 = new Node(a2, a0);
            Node n3 = new Node(a3, a2);

            stringNodes.add(new StringNode(n0));
            stringNodes.add(new StringNode(n1));
            stringNodes.add(new StringNode(n2));
            stringNodes.add(new StringNode(n3));

        } else
        if (this.id == 2) {
            // attack defense tree
        } else
        if (this.id == 3) {
            // capec tree
        } else {
            // retrieve from library
        }

        return stringNodes;
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

    public void addStringNode(StringNode sn) {
        stringNodes.add(sn);
    }

    public void deleteStringNode(StringNode sn) {
        stringNodes.remove(sn);
    }

    public void setID(int newID) {
        this.id = newID;
        buildList();
    }

    
}