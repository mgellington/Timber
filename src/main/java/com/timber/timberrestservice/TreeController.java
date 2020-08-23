package com.timber.timberrestservice;

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
    // private PracticeTree practiceTree = new PracticeTree();
    private TemplateTree practiceTree = new TemplateTree();
    

    @GetMapping("/node")
    public @ResponseBody List<StringNode> stringNode() {
        return practiceTree.getList();
        
    }

    @GetMapping("/tree")
    public String attackForm(Model model) {
        model.addAttribute("node", new StringNode());

        return "tree";
    }

    @PostMapping("/tree")
    public String attackSubmit(@ModelAttribute StringNode node, Model model) {
        model.addAttribute("node", node);
        practiceTree.addStringNode(node);

        List<String> allAttacks = practiceTree.getChildren();
        allAttacks.remove(0); // removes "child" label
        model.addAttribute("attacks", allAttacks);

        return "tree";
    }

}