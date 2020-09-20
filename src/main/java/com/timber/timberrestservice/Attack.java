package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

// Stores information about CAPEC attacks
public class Attack {
    public int capecID;
    public String name;

    // These lists contain the parent of and children of this attack
    public List<String> parentAttacks;
    public List<String> childAttacks;

    public String description;
    public String likelihood;
    public String severity;

    // CONSTRUCTOR
    public Attack (String name, int capecID) {
        this.name = name;
        this.capecID = capecID;
        parentAttacks = new ArrayList<String>();
        childAttacks = new ArrayList<String>();
    }


    public String toString() {
        return name;
    }


    // GETTERS AND SETTERS

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        if (description == null) {
            this.description = "";
        } else {
            this.description = description;
        }
    }

    public String getLikelihood() {
        return this.likelihood;
    }

    
    public void setLikelihood(String likelihood) {
        if (likelihood == null) {
            this.likelihood = "";
        } else {
            this.likelihood = likelihood;
        }

    }

    public String getSeverity() {
        return this.severity;
    }

    public void setSeverity(String severity) {
        if (severity == null) {
            this.severity = "";
        } else {
            this.severity = severity;
        }
    }

}