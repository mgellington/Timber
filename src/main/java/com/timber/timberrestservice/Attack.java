package com.timber.timberrestservice;


public class Attack {
    public String name;
    public boolean isRoot;
    public boolean isBest;

    public Attack (String name) {
        this.name = name;
        isRoot = false;
        isBest = false;
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

    public boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public boolean getIsBest() {
        return isBest;
    }

    public void setIsBest(boolean isBest) {
        this.isBest = isBest;
    }
}