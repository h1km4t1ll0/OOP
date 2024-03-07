package ru.nsu.dolgov.pizzeria.service.queues;

import ru.nsu.dolgov.pizzeria.service.entities.Order;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class BaseQueue implements BlockingQueue<Order> {
    private final int capacity;
    private Deque<Order> orders;

    public BaseQueue(int capacity) {
        this.capacity = capacity;
        this.orders = new ArrayDeque<>();
    }

    @Override
    public synchronized Order get() {
        while (this.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        return this.orders.pop();
    }

    @Override
    public void put(Order item) {
        while (this.orders.size() == this.capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.orders.push(item);
        notifyAll();
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public boolean isEmpty() {
        return this.orders.isEmpty();
    }
}
