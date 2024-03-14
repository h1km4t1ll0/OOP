package ru.nsu.dolgov.pizzeria.service.queues;

import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class BaseQueue implements BlockingQueue<Order> {
    private final int capacity;
    private final Deque<Order> orders;

    public BaseQueue(int capacity) {
        this.capacity = capacity;
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
    public synchronized void put(Order item) throws InterruptedException {
        while (this.orders.size() == this.capacity) {
            wait();
        }
        this.orders.push(item);
        notifyAll();
    }

    public int getCapacity() {
        return this.capacity;
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
