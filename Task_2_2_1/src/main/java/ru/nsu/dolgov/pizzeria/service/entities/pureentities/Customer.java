package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.Utils;
import ru.nsu.dolgov.pizzeria.service.Utils.LogLevel;
import ru.nsu.dolgov.pizzeria.service.interfaces.BaseConsumerI;
import ru.nsu.dolgov.pizzeria.service.queues.BaseQueue;

import java.util.UUID;

import static ru.nsu.dolgov.pizzeria.service.Utils.getUUID;
import static ru.nsu.dolgov.pizzeria.service.Utils.log;

/**
 * Class to implement Customer (in a separate thread).
 */
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

    /**
     * An entrypoint to the Customer thread. It holds creating the
     * order and putting it into the provided queue.
     */
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

    /**
     * Method to mock the making order procedure.
     *
     * @throws InterruptedException when bakery is closed.
     */
    public void makeOrder() throws InterruptedException {
        long sleepDuration = (Utils.getRandomNumberFromRange(1, this.dayDuration)) * 1000L;
        Thread.sleep(sleepDuration);
    }

    /**
     * Method to put order into the provided queue.
     *
     * @param order order to put.
     * @throws InterruptedException when bakery is closed.
     */
    @Override
    public void putOrder(Order order) throws InterruptedException {
        this.waitingQueue.put(order);
    }
}
