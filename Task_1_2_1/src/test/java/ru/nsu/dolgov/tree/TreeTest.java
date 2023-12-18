package ru.nsu.dolgov.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
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
    void checkEqualTreesButDifferentStructure() {
        Tree<String> tree1 = new Tree<>("a");
        tree1.addChild("b");
        var child2 = tree1.addChild("c");
        child2.addChild("e");
        child2.addChild("d");


        Tree<String> tree2 = new Tree<>("a");
        var child21 = tree1.addChild("b");
        tree2.addChild("c");
        child21.addChild("e");
        child21.addChild("d");
        assertNotEquals(tree1, tree2);
    }

    @Test
    void checkNotEqualTreesEqualityCornerCaseOne() {
        Tree<String> tree1 = new Tree<>("root");
        tree1.addChild("child");
        Tree<String> tree2 = new Tree<>("root");
        Tree<String> childTree2 = tree2.addChild("child");
        childTree2.addChild("child2");
        assertNotEquals(tree1, tree2);
    }

    @Test
    void checkNotEqualTreesEqualityCornerCaseTwo() {
        Tree<String> tree1 = new Tree<>("root");
        Tree<String> tree2 = new Tree<>("root");
        tree2.addChild("child");
        assertNotEquals(tree1, tree2);
    }

    @Test
    void checkGenericArgInteger() {
        Tree<Integer> tree1 = new Tree<>(1);
        Tree<Integer> tree2 = new Tree<>(2);
        tree2.addChild(49);
        assertNotEquals(tree1, tree2);
    }

    @Test
    void checkGenericArg() {
        Tree<Tree<Integer>> tree1 = new Tree<>(new Tree<>(5));
        Tree<Tree<Integer>> tree2 = new Tree<>(new Tree<>(5));
        assertEquals(tree1, tree2);
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
    void checkBfs() {
        Tree<String> tree = new Tree<>("root");
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
    void checkDfs() {
        Tree<String> tree = new Tree<>("root");
        tree.setIterator(Tree.IteratorTypes.Dfs);
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
    public void checkDfsThrowsConcurrentModificationException() {
        Tree<String> tree = new Tree<>("root");
        Tree<String> child = tree.addChild("child1");
        child.addChild("child5");
        Iterator<String> iterator = tree.iterator();
        tree.addChild("child3");
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    public void checkBfsThrowsConcurrentModificationException() {
        Tree<String> tree = new Tree<>("root");
        tree.setIterator(Tree.IteratorTypes.Dfs);
        Tree<String> child = tree.addChild("child1");
        child.addChild("child5");
        Iterator<String> iterator = tree.iterator();
        tree.addChild("child3");
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

}