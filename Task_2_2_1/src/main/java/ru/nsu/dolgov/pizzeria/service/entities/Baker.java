package ru.nsu.dolgov.pizzeria.service.entities;

import ru.nsu.dolgov.pizzeria.service.Utils;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueue;
import ru.nsu.dolgov.pizzeria.service.interfaces.Employee;

public class Baker implements Employee {
    private final int efficiency;
    private final int dayDuration;
    private final BlockingQueue<Order> deliveryQueue;
    private final BlockingQueue<Order> waitingQueue;

    public Baker(
            int efficiency,
            BlockingQueue<Order> deliveryQueue,
            BlockingQueue<Order> doneQueue,
            int dayDuration
    ) {
        this.efficiency = efficiency;
        this.deliveryQueue = deliveryQueue;
        this.waitingQueue = doneQueue;
        this.dayDuration = dayDuration;
    }

    @Override
    public Order getOrder() {
        return this.waitingQueue.get();
    }

    @Override
    public void putOrder(Order order) {
        this.deliveryQueue.put(order);
    }

    @Override
    public void startConsuming() {
        while (!Thread.interrupted()) {
            Order orders = this.getOrder();
            this.consumeOrder();
            this.putOrder(orders);
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
