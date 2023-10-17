package ru.nsu.dolgov.tree;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Class for tree implementation.
 *
 * @author Долгов Даниил
 * @version 1.0
 */
public class Tree<T> implements Iterable<T> {
    public final T value;
    private final List<Tree<T>> childNodes;
    public Tree<T> parentNode;
    private int modificationsFlag;
    private final String iterator;

    /**
     * A constructor for class Tree.
     *
     * @param value - value of the root node.
     * @param typeOfIterator - type of iterator for the tree.
     */
    public Tree(T value, String typeOfIterator) {
        if (typeOfIterator == null) {
            this.iterator = "BFS";
        } else {
            this.iterator = "DFS";
        }

        this.value = value;
        this.childNodes = new ArrayList<>();
        this.parentNode = null;
        this.modificationsFlag = 0;
    }

    /**
     * A constructor for class Tree without specifying typeOfIterator.
     *
     * @param value - value of the root node.
     */
    public Tree(T value) {
        this.iterator = "BFS";

        this.value = value;
        this.childNodes = new ArrayList<>();
        this.parentNode = null;
        this.modificationsFlag = 0;
    }

    /**
     * Method for adding child node.
     *
     * @param node - node to add to the tree.
     */
    public void addChild(Tree<T> node) {
        node.parentNode = this;
        this.childNodes.add(node);
        this.modificationsFlag++;
    }

    /**
     *  Method for adding a child node.
     *
     * @param value - value of the child node.
     * @return - new node of the tree.
     */
    public Tree<T> addChild(T value) {
        Tree<T> child = new Tree<>(value, this.iterator);
        child.parentNode = this;
        this.childNodes.add(child);
        this.modificationsFlag++;

        return child;
    }

    /**
     * Method for removing child node.
     *
     * @param node - node to remove from the tree.
     */
    public void removeChild(Tree<T> node) {
        if (this.childNodes.remove(node)) {
            node.parentNode = null;
            this.modificationsFlag++;
        }
    }


    /**
     * Overriding for the hashCode() method for the tree.
     *
     * @return - hash for the tree.
     */
    @Override
    public int hashCode() {
        int nodeHash = Objects.hash(this.value);

        if (this.parentNode != null) {
            nodeHash += Objects.hash(this.parentNode.value);
        }

        for (Tree<?> childNode : this.childNodes) {
            nodeHash += childNode.hashCode();
        }

        return nodeHash;
    }

    /**
     * Overriding for the equals() method for the tree.
     *
     * @param o - object to check equality.
     * @return - true if trees are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Tree)) {
            return false;
        }

        Tree<?> otherTree = (Tree<?>) o;

        if (this.parentNode == null && otherTree.parentNode != null) {
            return false;
        }

        if (this.parentNode != null && !this.parentNode.value.equals(otherTree.parentNode.value)) {
            return false;
        }

        return this.value.equals(otherTree.value) && this.hashCode() == o.hashCode();
    }

    /**
     * Overriding for the equals() method for the tree.
     *
     * @return - an iterator for the tree.
     */
    @Override
    public Iterator<T> iterator() {
        if (this.iterator.equals("BFS")) {
            return new BreadthIterator(this);
        } else {
            return new DepthIterator(this);
        }
    }

    /**
     * Depth first search for the tree.
     */
    private class DepthIterator implements Iterator<T> {
        private final List<Tree<T>> listNodes = new ArrayList<>();
        private int currentPositionInListNodes = 0;
        private final int expectedModificationFlag = modificationsFlag;

        /**
         * DepthIterator constructor.
         *
         * @param tree - tree, to iterate through.
         */
        DepthIterator(Tree<T> tree) {
            listNodes.add(tree);
        }

        /**
         * Overriding of the hasNext() method for the tree.
         *
         * @return - true if an iterator has nodes to iterate, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return currentPositionInListNodes < listNodes.size();
        }

        /**
         * Overriding of the next() method for the tree.
         *
         * @return - value of the next node.
         */
        @Override
        public T next() {
            if (modificationsFlag != this.expectedModificationFlag) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Tree<T> currentNode = this.listNodes.get(this.currentPositionInListNodes);

            this.currentPositionInListNodes++;

            this.listNodes.addAll(this.currentPositionInListNodes, currentNode.childNodes);
            return currentNode.value;
        }
    }

    private class BreadthIterator implements Iterator<T> {
        private final Queue<Tree<T>> queue = new LinkedList<>();
        private final int expectedModificationsFlag = modificationsFlag;

        /**
         * BreadthIterator constructor.
         *
         * @param tree - tree, to iterate through.
         */
        BreadthIterator(Tree<T> tree) {
            this.queue.offer(tree);
        }

        /**
         * Overriding of the hasNext() method for the tree.
         *
         * @return true if an iterator has nodes to iterate, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        /**
         * Overriding of the next() method for the tree.
         *
         * @return - value of the next node.
         */
        @Override
        public T next() throws java.util.NoSuchElementException {
            if (modificationsFlag != this.expectedModificationsFlag) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Tree<T> currentNode = this.queue.poll();

            if (currentNode != null) {
                for (Tree<T> node : currentNode.childNodes) {
                    this.queue.offer(node);
                }
                return currentNode.value;
            }

            throw new java.util.NoSuchElementException();
        }

    }
}


