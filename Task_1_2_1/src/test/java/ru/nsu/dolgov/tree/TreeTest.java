package ru.nsu.dolgov.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

import org.junit.jupiter.api.Test;


/**
 * Tests for the tree implementation class.
 */
public class TreeTest {

    @Test
    void checkRoot() {
        Tree<String> tree = new Tree<>("root");
        assertEquals("root", tree.value);
    }

    @Test
    void checkRootHasNoParent() {
        Tree<String> tree = new Tree<>("root");
        assertNull(tree.parentNode);
    }

    @Test
    void checkChildValueSetter() {
        Tree<String> tree = new Tree<>("root");
        Tree<String> child = tree.addChild("child");
        assertEquals("child", child.value);
    }


    @Test
    void checkRootsEquality() {
        Tree<String> tree1 = new Tree<>("root");
        Tree<String> tree2 = new Tree<>("root");
        assertEquals(tree1, tree2);
    }

    @Test
    void checkEqualTreesEquality() {
        Tree<String> tree1 = new Tree<>("root");
        tree1.addChild("child");
        Tree<String> tree2 = new Tree<>("root");
        tree2.addChild("child");
        assertEquals(tree1, tree2);
    }


    @Test
    void checkNotEqualTreesEquality() {
        Tree<String> tree1 = new Tree<>("root");
        tree1.addChild("child");
        Tree<String> tree2 = new Tree<>("root");
        tree2.addChild("otherChild");
        assertNotEquals(tree1, tree2);
    }

    @Test
    void checkRemoveChildNode() {
        Tree<String> tree1 = new Tree<>("root");
        Tree<String> childNode = tree1.addChild("child");
        tree1.removeChild(childNode);
        Tree<String> tree2 = new Tree<>("root");
        assertEquals(tree1, tree2);
    }

    @Test
    void checkRemoveSubtree() {
        Tree<String> tree = new Tree<>("root");
        var child1 = tree.addChild("child1");
        child1.addChild("child2");
        Tree<String> subtree = new Tree<>("root2");
        subtree.addChild("child3");
        subtree.addChild("child4");
        tree.addChild(subtree);
        tree.removeChild(subtree);
        tree.removeChild(child1);
        Tree<String> tree2 = new Tree<>("root");
        assertEquals(tree, tree2);
    }

    @Test
    void checkBFS() {
        Tree<String> tree = new Tree<>("root", null);
        Tree<String> child1 = tree.addChild("child1");
        tree.addChild("child1");
        tree.addChild("child18");
        child1.addChild("child2");
        Tree<String> subtree = new Tree<>("root2");
        subtree.addChild("child3");
        subtree.addChild("child4");
        subtree.addChild("child48");
        tree.addChild(subtree);

        StringBuilder builder = new StringBuilder();
        for (String node : tree) {
            builder.append(node);
        }
        assertEquals("rootchild1child1child18root2child2child3child4child48", builder.toString());
    }

    @Test
    void checkDFS() {
        Tree<String> tree = new Tree<>("root", "DFS");
        Tree<String> child1 = tree.addChild("child1");
        tree.addChild("child10");
        child1.addChild("child2");
        Tree<String> subtree = new Tree<>("root8");
        subtree.addChild("child4");
        subtree.addChild("child7");
        subtree.addChild("child5");
        subtree.addChild("child9");
        tree.addChild(subtree);

        StringBuilder builder = new StringBuilder();
        for (String node : tree) {
            builder.append(node);
        }
        assertEquals("rootchild1child2child10root8child4child7child5child9", builder.toString());
    }

    @Test
    public void checkDFSThrowsConcurrentModificationException() {
        Tree<String> tree = new Tree<>("root");
        Tree<String> child = tree.addChild("child1");
        child.addChild("child5");
        Iterator<String> iterator = tree.iterator();
        tree.addChild("child3");
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    public void checkBFSThrowsConcurrentModificationException() {
        Tree<String> tree = new Tree<>("root", "DFS");
        Tree<String> child = tree.addChild("child1");
        child.addChild("child5");
        Iterator<String> iterator = tree.iterator();
        tree.addChild("child3");
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

}