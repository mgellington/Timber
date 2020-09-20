package com.timber.timberrestservice;

// This class stores information about individual nodes in the tree in the form of two strings
// It defines the connection between a parent and child node
public class StringNode {

    public String child;
    public String parent;


    // CONSTRUCTORS
    public StringNode(String child, String parent) {
        this.parent = parent;
        this.child = child;
    }

    public StringNode() {
        this.child = "";
        this.parent = "";
    }


    // Checks if two nodes are equal to each other
    // (same parent and child Strings)
    public boolean equals(StringNode other) {
        boolean isEqual = false;
        if (other.getParent().equals(this.getParent())
        && other.getChild().equals(this.getChild())) {
            isEqual = true;
        }

        return isEqual;
    }

    // GETTERS AND SETTERS
    public String getParent() {
        return this.parent;
    }

    public String getChild() {
        return this.child;
    }

    public void setParent(String p) {
        this.parent = p;
    }

    public void setChild(String c) {
        this.child = c;
    }
    
}