package com.timber.timberrestservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ModelTests {

    List<StringNode> stringNodes = new ArrayList<StringNode>();
    StringNode root = new StringNode("Open Safe", "");
    StringNode sn1 = new StringNode("Pick Lock", "Open Safe");
    StringNode sn2 = new StringNode("Learn Combo", "Open Safe");
    StringNode sn3 = new StringNode("Get Combo From Target", "Learn Combo");


    TemplateTree testTree = new TemplateTree();

    @Test
    public void builtList() {
        int listLength = testTree.getList().size();
        assertEquals(1, listLength);
    }

    @Test
    public void addRoot() {
        testTree.addStringNode(root);
        int listLength = testTree.getList().size();
        assertEquals(2, listLength);

    }

    @Test
    public void addChildren() {
        addRoot();
        testTree.addStringNode(sn1);
        testTree.addStringNode(sn2);
        testTree.addStringNode(sn3);

        int listLength = testTree.getList().size();
        assertEquals(5, listLength);
    }

    @Test
    public void deleteRootAndAllChildren() {
        addChildren();

        testTree.removeAttack("Open Safe");

        int listLength = testTree.getList().size();
        assertEquals(1, listLength);
    }

    XMLService xmlService = new XMLService();

    @Test
    public void testParseAttacks() {
        List<Attack> attacks = xmlService.parseAttacks();
        int listLength = attacks.size();

        // There are a total of 524 attack patterns as of CAPEC download
        assertEquals(524, listLength);
    }

    @Test
    public void testParseCategory() {
        List<Category> categories = xmlService.parseCategory();
        int listLength = categories.size();

        // There are currently 6 Domain categories as of CAPEC download
        assertEquals(6, listLength);
    }




}