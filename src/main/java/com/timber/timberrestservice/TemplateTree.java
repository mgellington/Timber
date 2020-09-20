package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

// This class stores the entire tree in a List of StringNodes
public class TemplateTree {

    List<StringNode> stringNodes;

    // CONSTRUCTOR
    public TemplateTree() {
        buildList();
    }


    // The list must be built as a template containing a "blank" node ("child", "parent")
    // This is necessary for building the tree using Graphviz's dot notation
    public void buildList() {

        stringNodes = new ArrayList<StringNode>();
        stringNodes.add(new StringNode("child", "parent"));
    }

    // GETTERS
    public List<StringNode> getList() {
        return stringNodes;
    }

    // Retrives list of all attacks in the tree
    public List<String> getChildren() {
        List<String> children = new ArrayList<String>();
        for (StringNode node : stringNodes) {
            children.add(node.getChild());
        }

        return children;
    }

    // This method is used to add or delete a node from the tree
    // If the node is not on the tree, it is added
    // If node is already on the tree, it is deleted (using removeAttack method)
    // Could only use one method when adding a new node to tree from Controller
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
            boolean exists = false;
            if (!(sn.getParent().equals("Select Attack") 
            || sn.getChild().equals("Select Attack"))) {
                for (StringNode node : stringNodes) {
                    if (node.equals(sn)) {
                        // do not add
                        exists = true;
                    }
                }
                if (!exists) {
                    if ((stringNodes.size() > 1 && !(sn.getParent().equals(""))) || (stringNodes.size() == 1)) {
                        stringNodes.add(sn);
                    }
                    
                }
                
            }
            
        }
        

    }

    // Method for removing an attack from the tree
    // Removes selected node and all that nodes children
    // This is to maintain the structural integrity of the tree
    public void removeAttack(String attackName) {
        List<StringNode> remove = new ArrayList<StringNode>();
        for (StringNode node : stringNodes) {
            if (node.getChild().equals(attackName)) {
                remove.add(node);
            } else if (node.getParent().equals(attackName)) {
                remove.add(node);
            } 
        }

        int originalLength = remove.size();
        boolean finished = false;

        while (!finished) {
            for (int i = 0; i < originalLength; i++) {
                for (StringNode node : stringNodes) {
                    if (node.getChild().equals(remove.get(i).getChild())) {
                        if (!(remove.contains(node))) {
                            remove.add(node);
                        }
                    } else if (node.getParent().equals(remove.get(i).getChild())) {
                        if (!(remove.contains(node))) {
                            remove.add(node);
                        }
                    } 
                }
            }

            if (remove.size() == originalLength) {
                finished = true;
            }

            originalLength = remove.size();
        }

        

        for (StringNode node : remove) {
            stringNodes.remove(node);
        }
    }

    
}