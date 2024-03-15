package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.Utils;
import ru.nsu.dolgov.pizzeria.service.Utils.LogLevel;
import ru.nsu.dolgov.pizzeria.service.interfaces.BaseConsumerI;
import ru.nsu.dolgov.pizzeria.service.queues.BaseQueue;

import java.util.UUID;

import static ru.nsu.dolgov.pizzeria.service.Utils.getUUID;
import static ru.nsu.dolgov.pizzeria.service.Utils.log;

public class Customer implements BaseConsumerI {
    private final BaseQueue waitingQueue;
    private final int quantityOfOrders;
    private final int dayDuration;
    private final UUID id;

    public Customer(
            int quantityOfOrders,
            int dayDuration,
            BaseQueue waitingQueue
    ) {
        this.waitingQueue = waitingQueue;
        this.quantityOfOrders = quantityOfOrders;
        this.dayDuration = dayDuration;
        this.id = getUUID();
    }

    @Override
    public void consume() {
        log(
            LogLevel.INFO,
            "Customer with id " + this.id + " is ready to send orders."
        );
        try {
            for (int i = 0; i < this.quantityOfOrders; i++) {
                this.makeOrder();
                this.putOrder(new Order());
            }
        } catch (InterruptedException e) {
            log(
                LogLevel.INFO,
                "Customer with id " + this.id + " ended up its' work."
            );
        }
    }

    public void makeOrder() throws InterruptedException {
        long sleepDuration = (Utils.getRandomNumberFromRange(1, this.dayDuration)) * 1000L;
        Thread.sleep(sleepDuration);
    }

    @Override
    public void putOrder(Order order) throws InterruptedException {
        this.waitingQueue.put(order);
    }
}
