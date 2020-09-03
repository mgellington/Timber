package com.timber.timberrestservice;

import java.util.List;

public class MemberFinder {
    
    private String category;
    private List<String> members;

    // public MemberFinder(Category category) {
    //     this.category = category;
    // }

    public MemberFinder(String catString) {
        this.category = catString;
        XMLService xmlService = new XMLService();
        List<Category> categories = xmlService.parseCategory();
        for (Category cat : categories) {
            if (cat.getName().equals(catString)) {
                members = cat.getMembers();
            }
        }
    }

    public MemberFinder() {
        this.category = "";
    }

    public List<String> getMembers() {

        // List<String> members = category.getMembers();

        return members;
    }

    public String getCategory() {
        return category;
    }
    
    public void setCategory(String c) {
        this.category = c;
    }

    // public void setCategory(String s) {
    //     XMLService xmlService = new XMLService();
    //     List<Category> categories = xmlService.parseCategory();
    //     for (Category cat : categories) {
    //         if (cat.getName().equals(s)) {
    //             category = cat;
    //         } else {
    //             category = null;
    //         }
    //     }
    // }

//     public List<Category> getListOfCategories() {
//         return listOfCategories;
//     }

//     public void setListOfCategories(List<Category> list) {
//         listOfCategories = list;
//     }

}