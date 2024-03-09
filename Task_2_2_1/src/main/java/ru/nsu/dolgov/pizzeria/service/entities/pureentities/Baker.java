package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.Utils;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueue;
import ru.nsu.dolgov.pizzeria.service.interfaces.Employee;

import java.util.UUID;

import static ru.nsu.dolgov.pizzeria.service.Utils.getUUID;

public class Baker implements Employee {
    private final int efficiency;
    public final UUID employeeUUID;
    private final int dayDuration;
    private final BlockingQueue<Order> deliveryQueue;
    private final BlockingQueue<Order> waitingQueue;

    public Baker(
            int efficiency,
            BlockingQueue<Order> deliveryQueue,
            BlockingQueue<Order> waitingQueue,
            int dayDuration
    ) {
        this.employeeUUID = getUUID();
        this.efficiency = efficiency;
        this.deliveryQueue = deliveryQueue;
        this.waitingQueue = waitingQueue;
        this.dayDuration = dayDuration;
    }

    @Override
    public UUID getEmployeeUUID() {
        return this.employeeUUID;
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
            Order order = this.getOrder();
            Employee.changeStateOfOrder(order, OrderState.PREPARING);
            this.consumeOrder();
            Employee.changeStateOfOrder(order, OrderState.DELIVERING);
            this.putOrder(order);
        }
    }

    @Override
    public void consumeOrder() {
        try {
            Thread.sleep(
                Utils.getRandomNumberFromRange(1, this.dayDuration) / this.efficiency
            );
        } catch (InterruptedException e) {
            System.out.println("Deliverer thread was interrupted!");
            e.printStackTrace();
        }
    }
}
