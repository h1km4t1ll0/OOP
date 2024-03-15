package ru.nsu.dolgov.pizzeria.service.queues;

import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueueI;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * An implementation of the blocking queue.
 */
public class BaseQueue implements BlockingQueueI<Order> {
    private final int capacity;
    private final Deque<Order> orders;

    public BaseQueue(int capacity, Deque<Order> initial) {
        this.capacity = capacity;

        this.orders = Objects.requireNonNullElseGet(initial, ArrayDeque::new);
    }

    /**
     * Method to take te order from the queue.
     *
     * @return an order.
     * @throws InterruptedException when the bakery is closed.
     */
    @Override
    public synchronized Order get() throws InterruptedException {
        while (this.isEmpty()) {
            wait();
        }
        notify();
        return this.orders.pop();
    }

    /**
     * A method to put an order into the Queue.
     *
     * @param item order to put into.
     * @throws InterruptedException when the bakery is closed.
     */
    @Override
    public synchronized void put(Order item) throws InterruptedException {
        while (this.orders.size() == this.capacity) {
            wait();
        }
        this.orders.push(item);
        notifyAll();
    }

    /**
     * Checks whether the queue is empty.
     *
     * @return boolean value representing
     * whether the queue is empty or not.
     */
    @Override
    public boolean isEmpty() {
        return this.orders.isEmpty();
    }

    /**
     * Get the internal orders storage.
     *
     * @return deque of internal orders.
     */
    @Override
    public Deque<Order> getDump() {
        return this.orders;
    }
}
