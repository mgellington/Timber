package com.timber.timberrestservice;

public class Node {
    public Attack child;
    public Attack parent;

    public Node (Attack child, Attack parent) {
        this.child = child;
        this.parent = parent;
    }

    public Node (Attack child) {
        this.child = child;
        this.parent = null;
    }

    public Attack getParent() {
        return parent;
    }

    public Attack getChild() {
        return child;
    }

}