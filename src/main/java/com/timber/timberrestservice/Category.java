package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

// Stores information about the Domain categories the CAPEC list is organized by
public class Category {
    public int id;
    public String name;

    // The meta-attacks associated with each category
    public List<String> members;
    

    // CONSTRUCTOR
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.members = new ArrayList<String>();

    }

    
    // GETTERS AND SETTERS
    
    public void addMember(String id) {
        members.add(id);
    }

    public List<String> getMembers() {
        return this.members;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }


}