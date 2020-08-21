package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

public class PracticeTree {

    List<StringNode> stringNodes;

    public PracticeTree() {
        stringNodes = new ArrayList<StringNode>();

        Attack child = new Attack("child");
        Attack parent = new Attack("parent");
        Attack a0 = new Attack("Open Safe");
        Attack a1 = new Attack("Pick Lock");

        Attack a2 = new Attack("Learn Combo");
        Attack a3 = new Attack("Find Written Combo");

        Node nullNode = new Node(child, parent);
        Node n0 = new Node(a0);
        Node n1 = new Node(a1, a0);
        Node n2 = new Node(a2, a0);
        Node n3 = new Node(a3, a2);

        stringNodes.add(new StringNode(nullNode));
        stringNodes.add(new StringNode(n0));
        stringNodes.add(new StringNode(n1));
        stringNodes.add(new StringNode(n2));
        stringNodes.add(new StringNode(n3));

    }

    public List<StringNode> getList() {
        return stringNodes;
    }

    public void addStringNode(StringNode sn) {
        stringNodes.add(sn);
    }

    public void deleteStringNode(StringNode sn) {
        stringNodes.remove(sn);
    }

    
}