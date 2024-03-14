package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.Utils;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueue;
import ru.nsu.dolgov.pizzeria.service.interfaces.EmployeeI;

import java.util.UUID;

import static ru.nsu.dolgov.pizzeria.service.Utils.getUUID;

public abstract class Employee implements EmployeeI {
    private final int efficiency;
    private final int dayDuration;
    public final UUID employeeUUID;
    private final BlockingQueue<Order> sourceQueue;
    private final BlockingQueue<Order> destinationQueue;
    private final BlockingQueue<Order> pendingSourceQueue;

    public Employee(
            int efficiency,
            int dayDuration,
            BlockingQueue<Order> sourceQueue,
            BlockingQueue<Order> destinationQueue,
            BlockingQueue<Order> pendingSourceQueue
    ) {
        this.employeeUUID = getUUID();
        this.efficiency = efficiency;
        this.sourceQueue = sourceQueue;
        this.destinationQueue = destinationQueue;
        this.dayDuration = dayDuration;
        this.pendingSourceQueue = pendingSourceQueue;
    }

    @Override
    public UUID getEmployeeUUID() {
        return this.employeeUUID;
    }

    @Override
    public Order getOrder() throws InterruptedException {
        return this.sourceQueue.get();
    }

    @Override
    public void putOrder(Order order) throws InterruptedException {
        this.destinationQueue.put(order);
    }

    public void putOrderBack(Order order) {
        try {
            this.pendingSourceQueue.put(order);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consumeOrder() throws InterruptedException {
        long sleepDuration = (Utils.getRandomNumberFromRange(1, this.dayDuration) / this.efficiency) * 1000L;
        Thread.sleep(sleepDuration);
    }
}
