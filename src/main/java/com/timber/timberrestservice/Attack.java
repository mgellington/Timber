package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

public class Attack {
    public int capecID;
    public String name;
    public List<String> suggestedChildren;

    public Attack (String name) {
        this.name = name;
        this.capecID = 0;
        suggestedChildren = new ArrayList<String>();
    }

    public Attack (String name, int capecID) {
        this.name = name;
        this.capecID = capecID;
        suggestedChildren = new ArrayList<String>();
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

    public List<String> getSuggestedChildren() {
        return suggestedChildren;
    }

    public void addSuggestedChild(String child) {
        suggestedChildren.add(child);
    }

}