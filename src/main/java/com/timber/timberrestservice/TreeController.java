package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TreeController {

    // chooses which tree to load based on which button pressed on landing screen
    private TemplateTree practiceTree = new TemplateTree();
    private List<Category> listOfCategories = new ArrayList<Category>();
    private List<Attack> listOfAttacks = new ArrayList<Attack>();


    @GetMapping("/node")
    public @ResponseBody List<StringNode> listNodes() {
        return practiceTree.getList();
        
    }

    @GetMapping("/attacks")
    public @ResponseBody List<Attack> listAttacks() {
        XMLService xmlService = new XMLService();
        listOfAttacks = xmlService.parseAttacks();

        return listOfAttacks;
    }

    @GetMapping("/members")
    public @ResponseBody List<Category> listMembers() {
        XMLService xmlService = new XMLService();
        listOfCategories = xmlService.parseCategory();

        return listOfCategories;
    }


    @GetMapping("/tree")
    public String attackForm(Model model) {
        model.addAttribute("node", new StringNode());

        // adds CAPEC domains
        XMLService xmlService = new XMLService();
        List<Category> categories = xmlService.parseCategory();
        List<String> categoriesToString = new ArrayList<String>();
        categoriesToString.add("Select a Domain.");

        for (Category category : categories) {
            categoriesToString.add(category.toString());
        }
        model.addAttribute("categories", categoriesToString);
        

        // adds possible parent attacks
        List<String> allAttacks = new ArrayList<String>();
        allAttacks.add("Select Attack");
        allAttacks.addAll(practiceTree.getChildren());
        allAttacks.remove(1); // removes "child" label
        model.addAttribute("attacks", allAttacks);

        return "tree";
    }

    @PostMapping("/tree")
    public String attackSubmit(@ModelAttribute StringNode node, Model model) {
        model.addAttribute("node", node);

        // if string node (e.g. parent and no child) already exists
        // delete original
        practiceTree.addStringNode(node);

        // adds CAPEC domains
        XMLService xmlService = new XMLService();
        List<Category> categories = xmlService.parseCategory();
        List<String> categoriesToString = new ArrayList<String>();
        for (Category category : categories) {
            categoriesToString.add(category.toString());
        }
        model.addAttribute("categories", categoriesToString);


        // adds possible parent attacks
        List<String> allAttacks = new ArrayList<String>();
        allAttacks.add("Select Attack");
        allAttacks.addAll(practiceTree.getChildren());
        allAttacks.remove(1); // removes "child" label
        model.addAttribute("attacks", allAttacks);

        return "tree";
    }

}