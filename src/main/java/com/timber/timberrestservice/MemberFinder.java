package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

public class MemberFinder {
    
    private Category category;
    private List<Category> listOfCategories;

    public MemberFinder(Category category) {
        this.category = category;
        XMLService xmlService = new XMLService();
        this.listOfCategories = xmlService.parseCategory();
    }

    // public List<String> getMembers() {

    //     List<String> members = new ArrayList<String>();

    //     for (Category cat: listOfCategories) {
    //         if (cat.equals(category)) {
    //             members = cat.getMembers();
    //         }
    //     }

    //     return members;
    // }

    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category c) {
        this.category = c;
    }

//     public List<Category> getListOfCategories() {
//         return listOfCategories;
//     }

//     public void setListOfCategories(List<Category> list) {
//         listOfCategories = list;
//     }
// }