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

    private PracticeTree practiceTree = new PracticeTree();

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
        return "tree";
    }
}