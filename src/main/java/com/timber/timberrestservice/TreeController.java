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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TreeController {

    // chooses which tree to load based on which button pressed on landing screen
    // private PracticeTree practiceTree = new PracticeTree();
    private TemplateTree practiceTree = new TemplateTree();


    @GetMapping("/node")
    public @ResponseBody List<StringNode> stringNode() {
        return practiceTree.getList();
        
    }


    @GetMapping("/tree")
    public String attackForm(Model model) {
        model.addAttribute("node", new StringNode());

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
    public String attackSubmit(@ModelAttribute StringNode node, Model model) {
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

        // // finds members belonging to chosen CAPEC domain
        // model.addAttribute("memberFinder", memberFinder);
        // List<String> memberStrings = memberFinder.getMembers();
        // model.addAttribute("members", memberStrings);

        // adds possible parent attacks
        List<String> allAttacks = practiceTree.getChildren();
        allAttacks.remove(0); // removes "child" label
        model.addAttribute("attacks", allAttacks);

        return "tree";
    }

}