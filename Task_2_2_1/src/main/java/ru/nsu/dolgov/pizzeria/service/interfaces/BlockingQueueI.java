package ru.nsu.dolgov.pizzeria.service.interfaces;

import java.util.Deque;

/**
 * An interface for the blocking queue.
 *
 * @param <T> parameter for the item in the queue
 */
public interface BlockingQueueI<T> {
    T get() throws InterruptedException;

    void put(T item) throws InterruptedException;

    boolean isEmpty();

    Deque<T> getDump();
}
