package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.Utils;
import ru.nsu.dolgov.pizzeria.service.Utils.LogLevel;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueueI;
import ru.nsu.dolgov.pizzeria.service.interfaces.EmployeeI;

import static ru.nsu.dolgov.pizzeria.service.Utils.Colors.BLUE;
import static ru.nsu.dolgov.pizzeria.service.Utils.Colors.RESET;
import static ru.nsu.dolgov.pizzeria.service.Utils.log;

public class Baker extends Employee implements EmployeeI {
    public Baker(
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
    }

    @Override
    public void consume() {
        log(
            LogLevel.INFO,
            "Baker with id " + this.getEmployeeUUID() + " is ready to consume."
        );
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
        log(
            LogLevel.INFO,
            "Baker with id " + this.getEmployeeUUID() + " ended up its' work."
        );
    }
}
