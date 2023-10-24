package ru.nsu.dolgov.tree;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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
    private int modificationsCounter = 0;

    /**
     * Enum for an iterator type.
     */
    public enum IteratorTypes {
        Bfs,
        Dfs
    }

    public IteratorTypes iteratorType = IteratorTypes.Bfs;

    /**
     * A constructor for class Tree.
     *
     * @param value - value of the root node.
     */
    public Tree(T value) {
        this.value = value;
        this.childNodes = new ArrayList<>();
        this.parentNode = null;
    }

    public void setIterator(IteratorTypes newIteratorType) {
        iteratorType = newIteratorType;
    }

    /**
     * Method for adding child node.
     *
     * @param node - node to add to the tree.
     */
    public void addChild(Tree<T> node) {
        node.parentNode = this;
        this.childNodes.add(node);
        this.modificationsCounter++;
    }

    /**
     *  Method for adding a child node.
     *
     * @param value - value of the child node.
     * @return - new node of the tree.
     */
    public Tree<T> addChild(T value) {
        Tree<T> child = new Tree<>(value);
        child.setIterator(this.iteratorType);
        child.parentNode = this;
        this.childNodes.add(child);
        this.modificationsCounter++;

        return child;
    }

    /**
     * Method for removing child node.
     *
     * @param node - node to remove from the tree.
     */
    public void removeChild(Tree<T> node) {
        if (childNodes.remove(node)) {
            node.parentNode = null;
            modificationsCounter++;
        }
    }


    /**
     * Overriding for the hashCode() method for the tree.
     *
     * @return - hash for the tree.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(this.value);
        if (parentNode != null) {
            result += Objects.hash(parentNode.value);
        }
        for (Tree<?> child : childNodes) {
            result += child.hashCode();
        }
        return result;
    }

    /**
     * Overriding for the equals() method for the tree.
     *
     * @param o - object to check equality.
     * @return - true if trees are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tree<?> otherTree = (Tree<?>) o;

        if (this.parentNode == null && otherTree.parentNode != null) {
            return false;
        }

        if (this.parentNode != null && !this.parentNode.value.equals(otherTree.parentNode.value)) {
            return false;
        }

        return this.value.equals(otherTree.value);
    }

    /**
     * Overriding for the equals() method for the tree.
     *
     * @return - an iterator for the tree.
     */
    @Override
    public Iterator<T> iterator() {
        if (this.iteratorType == IteratorTypes.Bfs) {
            return new BreadthIterator();
        } else {
            return new DepthIterator();
        }
    }

    /**
     * Depth first search for the tree.
     */
    private class DepthIterator implements Iterator<T> {
        private final List<Tree<T>> listNodes = new ArrayList<>();
        private int currentPositionInListNodes = 0;
        private final int expectedModificationCount = modificationsCounter;

        /**
         * DepthIterator constructor.
         *
         */
        DepthIterator() {
            listNodes.add(Tree.this);
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
            if (modificationsCounter != this.expectedModificationCount) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Tree<T> currentNode = this.listNodes.get(this.currentPositionInListNodes);

            this.currentPositionInListNodes++;

            this.listNodes.addAll(this.currentPositionInListNodes, currentNode.childNodes);
            return currentNode.value;
        }
    }

    private class BreadthIterator implements Iterator<T> {
        private final Deque<Tree<T>> queue = new LinkedList<>();
        private final int expectedModificationsFlag = modificationsCounter;

        /**
         * BreadthIterator constructor.
         */
        BreadthIterator() {
            this.queue.offer(Tree.this);
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
        public T next() throws NoSuchElementException {
            if (modificationsCounter != this.expectedModificationsFlag) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Tree<T> currentNode = this.queue.poll();

            if (currentNode != null) {
                for (Tree<T> node : currentNode.childNodes) {
                    this.queue.offer(node);
                }
                return currentNode.value;
            }

            throw new NoSuchElementException();
        }
    }
}


