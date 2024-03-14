package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueue;
import ru.nsu.dolgov.pizzeria.service.interfaces.EmployeeI;

import static ru.nsu.dolgov.pizzeria.service.Utils.Colors.BLUE;
import static ru.nsu.dolgov.pizzeria.service.Utils.Colors.RESET;

public class Baker extends Employee implements EmployeeI {
    public Baker(
            int efficiency,
            int dayDuration,
            BlockingQueue<Order> sourceQueue,
            BlockingQueue<Order> destinationQueue,
            BlockingQueue<Order> pendingSourceQueue
    ) {
        super(
            efficiency,
            dayDuration,
            sourceQueue,
            destinationQueue,
            pendingSourceQueue
        );
    }

    @Override
    public void consume() {
        System.out.printf("Baker with id %s%s%s is ready to consume!\n", BLUE, this.getEmployeeUUID().toString(), RESET);
        Order order = null;
        try {
            while (true) {
                order = this.getOrder();
                EmployeeI.changeStateOfOrder(order, OrderState.PREPARING);
                this.consumeOrder();
                EmployeeI.changeStateOfOrder(order, OrderState.WAREHOUSE);
                this.putOrder(order);
            }
        } catch (InterruptedException e) {
            this.putOrderBack(order);
        }
    }
}
