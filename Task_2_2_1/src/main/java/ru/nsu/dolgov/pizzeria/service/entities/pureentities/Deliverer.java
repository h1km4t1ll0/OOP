package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.Utils.LogLevel;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueueI;
import ru.nsu.dolgov.pizzeria.service.interfaces.EmployeeI;
import ru.nsu.dolgov.pizzeria.service.queues.UnlimitedQueue;

import java.util.ArrayList;
import java.util.List;

import static ru.nsu.dolgov.pizzeria.service.Utils.log;

/**
 * Class to implement Deliverer (in a separate thread).
 */
public class Deliverer extends Employee implements EmployeeI {
    private final int capacity;

    public Deliverer(
            int capacity,
            int efficiency,
            int dayDuration,
            BlockingQueueI<Order> sourceQueue,
            BlockingQueueI<Order> destinationQueue,
            UnlimitedQueue pendingSourceQueue
    ) {
        super(
                efficiency,
                dayDuration,
                sourceQueue,
                destinationQueue,
                pendingSourceQueue
        );
        this.capacity = capacity;
    }

    /**
     * Method to put a bunch of orders to the queue.
     *
     * @param orders orders to put.
     * @throws InterruptedException when the bakery is closed.
     */
    private void putOrders(List<Order> orders) throws InterruptedException {
        for (Order order : orders) {
            this.putOrder(order);
        }
    }

    /**
     * Method to dump orders when the bakery os closed.
     *
     * @param orders orders to make dump of.
     */
    private void dumpOrders(List<Order> orders) {
        orders.forEach(this::dumpOrder);
    }

    /**
     * Method to take orders from the queue according to the capacity prop.
     *
     * @param quantity quantity of orders to take.
     * @return list of taken orders.
     * @throws InterruptedException when the bakery is closed.
     */
    private List<Order> getOrders(int quantity) throws InterruptedException {
        int counterOfOrders = 0;
        List<Order> orders = new ArrayList<>();
        while (counterOfOrders != quantity) {
            orders.add(this.getOrder());
            counterOfOrders++;
        }
        return orders;
    }

    /**
     * An entrypoint, it holds getting orders,
     * consuming orders and propagation of order.
     */
    @Override
    public void consume() {
        log(
                LogLevel.INFO,
                "Deliverer with id " + this.getEmployeeUUID() + " is ready to consume."
        );
        List<Order> orders = new ArrayList<>();
        try {
            while (true) {
                orders = this.getOrders(this.capacity);
                orders.forEach((order -> EmployeeI.changeStateOfOrder(order, OrderState.DELIVERING)));
                this.consumeOrder();
                orders.forEach((order -> EmployeeI.changeStateOfOrder(order, OrderState.DONE)));
                this.putOrders(orders);
                orders.clear();
            }
        } catch (InterruptedException e) {
            this.dumpOrders(orders);
        }
        log(
                LogLevel.INFO,
                "Deliverer with id " + this.getEmployeeUUID() + " ended up its' work."
        );
    }
}
