package com.timber.timberrestservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TreeController {

    // chooses which tree to load based on which button pressed on landing screen
    private TemplateTree practiceTree = new TemplateTree();
    // private List<String> memberStrings = new ArrayList<String>();
    private List<Category> listOfCategories = new ArrayList<Category>();

    /* TO DO TOMORROW
        MAKE JSON WITH SET (CATEGORY/MEMBER PAIRS) FOR AJAX TO RETRIEVE FROM
        MAKE FUNCTION IN AJAX CALL THAT FILTERS BY CATEGORY
    */


    @GetMapping("/node")
    public @ResponseBody List<StringNode> stringNode() {
        return practiceTree.getList();
        
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
        model.addAttribute("memberFinder", new MemberFinder());

        // adds CAPEC domains
        XMLService xmlService = new XMLService();
        List<Category> categories = xmlService.parseCategory();
        List<String> categoriesToString = new ArrayList<String>();


        for (Category category : categories) {
            categoriesToString.add(category.toString());
        }
        model.addAttribute("categories", categoriesToString);
        

        // adds possible parent attacks
        List<String> allAttacks = practiceTree.getChildren();
        allAttacks.remove(0); // removes "child" label
        model.addAttribute("attacks", allAttacks);

        return "tree";
    }

    @PostMapping("/tree")
    public String attackSubmit(@ModelAttribute StringNode node, @ModelAttribute MemberFinder memberFinder, Model model) {
        model.addAttribute("node", node);
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
        List<String> allAttacks = practiceTree.getChildren();
        allAttacks.remove(0); // removes "child" label
        model.addAttribute("attacks", allAttacks);

        List<Attack> attacks = xmlService.parseAttacks();
        List<String> suggestedAttacks = new ArrayList<String>();
        for (Attack attack : attacks) {
            if (attack.getName().equals(node.getChild())) {
                for (String child : attack.getChildAttacks()) {
                    suggestedAttacks.add(child);
                }
            }
        }

        model.addAttribute("suggestedAttacks", suggestedAttacks);

        return "tree";
    }

}