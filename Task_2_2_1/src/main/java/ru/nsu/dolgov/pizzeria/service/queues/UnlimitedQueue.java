package ru.nsu.dolgov.pizzeria.service.queues;

import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class UnlimitedQueue implements BlockingQueue<Order> {
    private final Deque<Order> orders;

    public UnlimitedQueue() {
        this.orders = new ArrayDeque<>();
    }

    @Override
    public synchronized Order get() throws InterruptedException {
        while (this.isEmpty()) {
            wait();
        }
        notify();
        return this.orders.pop();
    }

    @Override
    public synchronized void put(Order item) {
        this.orders.push(item);
    }

    @Override
    public boolean isEmpty() {
        return this.orders.isEmpty();
    }

    @Override
    public Deque<Order> getDump() {
        return this.orders;
    }
}
