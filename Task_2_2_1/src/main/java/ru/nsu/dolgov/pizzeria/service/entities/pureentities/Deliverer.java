package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.Utils;
import ru.nsu.dolgov.pizzeria.service.Utils.LogLevel;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueueI;
import ru.nsu.dolgov.pizzeria.service.interfaces.EmployeeI;

import java.util.ArrayList;
import java.util.List;

import static ru.nsu.dolgov.pizzeria.service.Utils.Colors.BLUE;
import static ru.nsu.dolgov.pizzeria.service.Utils.Colors.RESET;
import static ru.nsu.dolgov.pizzeria.service.Utils.log;

public class Deliverer extends Employee implements EmployeeI {
    private final int capacity;

    public Deliverer(
            int capacity,
            int efficiency,
            int dayDuration,
            BlockingQueueI<Order> sourceQueue,
            BlockingQueueI<Order> destinationQueue,
            BlockingQueueI<Order> pendingSourceQueue
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

    private void putOrders(List<Order> orders) throws InterruptedException {
        for (Order order : orders) {
            this.putOrder(order);
        }
    }

    private void dumpOrders(List<Order> orders) {
        orders.forEach(this::putOrderBack);
    }

    private List<Order> getOrders(int quantity) throws InterruptedException {
        int counterOfOrders = 0;
        List<Order> orders = new ArrayList<>();
        while (counterOfOrders != quantity) {
            orders.add(this.getOrder());
            counterOfOrders++;
        }
        return orders;
    }

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
