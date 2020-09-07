package com.timber.timberrestservice;

public class StringNode {

    public String child;
    public String parent;

    public StringNode(String child, String parent) {
        this.parent = parent;
        this.child = child;
    }

    public StringNode() {
        this.child = "";
        this.parent = "";
    }

    public boolean equals(StringNode other) {
        boolean isEqual = false;
        if (other.getParent().equals(this.getParent())
        && other.getChild().equals(this.getChild())) {
            isEqual = true;
        }

        return isEqual;
    }


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