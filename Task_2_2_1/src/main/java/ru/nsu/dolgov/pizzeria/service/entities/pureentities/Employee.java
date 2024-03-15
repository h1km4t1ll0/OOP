package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.Utils;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueueI;
import ru.nsu.dolgov.pizzeria.service.interfaces.EmployeeI;
import ru.nsu.dolgov.pizzeria.service.queues.UnlimitedQueue;

import java.util.UUID;

import static ru.nsu.dolgov.pizzeria.service.Utils.getUUID;

/**
 * A class to implement base methods of Employee entity.
 */
public abstract class Employee implements EmployeeI {
    private final int efficiency;
    private final int dayDuration;
    public final UUID employeeUUID;
    private final BlockingQueueI<Order> sourceQueue;
    private final BlockingQueueI<Order> destinationQueue;
    private final UnlimitedQueue pendingSourceQueue;

    public Employee(
            int efficiency,
            int dayDuration,
            BlockingQueueI<Order> sourceQueue,
            BlockingQueueI<Order> destinationQueue,
            UnlimitedQueue pendingSourceQueue
    ) {
        this.employeeUUID = getUUID();
        this.efficiency = efficiency;
        this.sourceQueue = sourceQueue;
        this.destinationQueue = destinationQueue;
        this.dayDuration = dayDuration;
        this.pendingSourceQueue = pendingSourceQueue;
    }

    /**
     * An UUID getter.
     *
     * @return current employees' id.
     */
    @Override
    public UUID getEmployeeUUID() {
        return this.employeeUUID;
    }

    /**
     * Method to get order to proceed.
     *
     * @return taken order.
     * @throws InterruptedException when the bakery is closed.
     */
    @Override
    public Order getOrder() throws InterruptedException {
        return this.sourceQueue.get();
    }

    /**
     * Method to propagate the order.
     *
     * @param order order to propagate.
     * @throws InterruptedException when the bakery is closed.
     */
    @Override
    public void putOrder(Order order) throws InterruptedException {
        this.destinationQueue.put(order);
    }

    /**
     * Method to dump the order when the bakery is closed.
     *
     * @param order order to dump.
     */
    public void dumpOrder(Order order) {
        if (order != null) {
            this.pendingSourceQueue.put(order);
        }
    }

    /**
     * Implementation of the employees' work.
     *
     * @throws InterruptedException when the bakery is closed.
     */
    @Override
    public void consumeOrder() throws InterruptedException {
        long sleepDuration = (Utils.getRandomNumberFromRange(1, this.dayDuration) / this.efficiency) * 1000L;
        Thread.sleep(sleepDuration);
    }
}
