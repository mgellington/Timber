package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

public class Attack {
    public int capecID;
    public String name;

    public List<String> parentAttacks;
    public List<String> childAttacks;

    public Attack (String name) {
        this.name = name;
        this.capecID = 0;
        parentAttacks = new ArrayList<String>();
        childAttacks = new ArrayList<String>();
    }

    public Attack (String name, int capecID) {
        this.name = name;
        this.capecID = capecID;
        parentAttacks = new ArrayList<String>();
        childAttacks = new ArrayList<String>();
    }


    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public int getCapecID() {
        return capecID;
    }

    public List<String> getParentAttacks() {
        return parentAttacks;
    }

    public void addParentAttacks(String parent) {
        parentAttacks.add(parent);
    }

    public List<String> getChildAttacks() {
        return childAttacks;
    }

    public void addChildAttacks(String child) {
        childAttacks.add(child);
    }

}