package ru.nsu.dolgov.pizzeria.service.entities;

import ru.nsu.dolgov.pizzeria.service.Utils;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueue;
import ru.nsu.dolgov.pizzeria.service.interfaces.Employee;

import java.util.ArrayList;
import java.util.List;

public class Deliverer implements Employee {
    private final int capacity;
    private final int efficiency;
    private final int dayDuration;
    private final BlockingQueue<Order> deliveryQueue;
    private final BlockingQueue<Order> doneQueue;

    public Deliverer(
            int capacity,
            int efficiency,
            BlockingQueue<Order> deliveryQueue,
            BlockingQueue<Order> doneQueue,
            int dayDuration
    ) {
        this.capacity = capacity;
        this.efficiency = efficiency;
        this.deliveryQueue = deliveryQueue;
        this.doneQueue = doneQueue;
        this.dayDuration = dayDuration;
    }

    @Override
    public Order getOrder() {
        return this.deliveryQueue.get();
    }

    @Override
    public void putOrder(Order order) {
        this.doneQueue.put(order);
    }

    private void putOrders(List<Order> orders) {
        orders.forEach(this::putOrder);
    }

    private List<Order> getOrders(int quantity) {
        int counterOfOrders = 0;
        List<Order> orders = new ArrayList<>();
        while (counterOfOrders != quantity) {
            orders.add(this.getOrder());
            counterOfOrders++;
        }

        return orders;
    }

    @Override
    public void startConsuming() {
        while (!Thread.interrupted()) {
            List<Order> orders = this.getOrders(this.capacity);
            this.consumeOrder();
            this.putOrders(orders);
        }
    }

    @Override
    public void changeStateOfOrder(Order order, OrderState newState) {
        order.setState(newState);
    }

    @Override
    public void consumeOrder() {
        try {
            Thread.sleep(
                    Utils.getRandomDuration(1, this.dayDuration) / this.efficiency
            );
        } catch (InterruptedException e) {
            System.out.println("Deliverer thread was interrupted!");
            e.printStackTrace();
        }
    }
}
