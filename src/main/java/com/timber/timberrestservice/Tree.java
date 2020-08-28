package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    public List<Attack> attackList;
    public List<Node> nodeList;
    public Attack root;

    public Tree() {
        attackList = new ArrayList<Attack>();
        nodeList = new ArrayList<Node>();
    }

    public void assignRoot(Attack attack) {
        root = attack;
        // attack.setIsRoot(true);
    }

    public void addAttack(Attack attack) {
        // root
        assignRoot(attack);
        attackList.add(attack);
    }

    public void addAttack(Attack parent, Attack child) {
        // any attack after root
        if (attackList.contains(parent)) {
            attackList.add(child);
            Node n = new Node(parent, child);
            nodeList.add(n);
        }

    }
    
}