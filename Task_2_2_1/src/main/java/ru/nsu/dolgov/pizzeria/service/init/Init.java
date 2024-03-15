package ru.nsu.dolgov.pizzeria.service.init;

import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.*;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Baker;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Customer;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Deliverer;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;
import ru.nsu.dolgov.pizzeria.service.interfaces.BaseConsumerI;
import ru.nsu.dolgov.pizzeria.service.queues.BaseQueue;
import ru.nsu.dolgov.pizzeria.service.queues.QueueLocator;
import ru.nsu.dolgov.pizzeria.service.queues.UnlimitedQueue;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static ru.nsu.dolgov.pizzeria.service.Utils.getRandomNumberFromRange;

public class Init {
    private final Config configuration;
    private final FileAPI dumpfileAPI;
    private final QueueLocator queueLocator;
    private final Orders previousOrders;
    public Init(String configurationFile, String dumpFile) {
        this.configuration = new FileAPI(configurationFile).parse();
        this.dumpfileAPI = new FileAPI(dumpFile);
        this.previousOrders = this.dumpfileAPI.getPreviousOrders();
        this.queueLocator = this.declareQueues();
    }

    public List<BaseConsumerI> initBakers() {
        Bakers bakersConfiguration = this.configuration.bakers;
        List<BaseConsumerI> bakerList = new ArrayList<>();
        int randomEfficiency = getRandomNumberFromRange(1, 10);
        for (int i = 0; i < bakersConfiguration.quantity; i ++) {
            bakerList.add(new Baker(
                randomEfficiency,
                this.configuration.bakery.durationOfTheDayInSeconds,
                this.queueLocator.waitingQueue(),
                this.queueLocator.deliveryQueue(),
                this.queueLocator.pendingWaitingQueue()
            ));
        }
        return bakerList;
    }

    public List<BaseConsumerI> initDeliverers() {
        Deliverers deliverersConfiguration = this.configuration.deliverers;
        List<BaseConsumerI> delivererList = new ArrayList<>();
        int randomEfficiency = getRandomNumberFromRange(1, 10);
        for (int i = 0; i < deliverersConfiguration.quantity; i ++) {
            delivererList.add(new Deliverer(
                deliverersConfiguration.capacity,
                randomEfficiency,
                this.configuration.bakery.durationOfTheDayInSeconds,
                this.queueLocator.deliveryQueue(),
                this.queueLocator.doneQueue(),
                this.queueLocator.pendingDeliveryQueue()
            ));
        }
        return delivererList;
    }

    public List<BaseConsumerI> initCustomers() {
        Customers customersConfiguration = this.configuration.customers;
        List<BaseConsumerI> costumersList = new ArrayList<>();
        for (int i = 0; i < customersConfiguration.quantity; i ++) {
            costumersList.add(new Customer(
                customersConfiguration.quantityOfOrders,
                this.configuration.bakery.durationOfTheDayInSeconds,
                this.queueLocator.waitingQueue()
            ));
        }
        return costumersList;
    }

    private QueueLocator declareQueues() {
        Deque<Order> previousOrderToPrepare = this.previousOrders.waitingQueue;
        if (previousOrderToPrepare != null) {
            previousOrderToPrepare.addAll(this.previousOrders.pendingWaitingQueue);
        }

        Deque<Order> previousOrderToDeliver = this.previousOrders.waitingQueue;
        if (previousOrderToDeliver != null) {
            previousOrderToDeliver.addAll(this.previousOrders.pendingWaitingQueue);
        }

        return new QueueLocator(
            new BaseQueue(
                this.configuration.bakery.waitingQueueCapacity,
                previousOrderToPrepare
            ),
            new BaseQueue(
                this.configuration.bakery.deliveryQueueCapacity,
                previousOrderToDeliver
            ),
            new UnlimitedQueue(),
            new UnlimitedQueue(),
            new UnlimitedQueue()
        );
    }

    public QueueLocator getQueueLocator() {
        return this.queueLocator;
    }

    public Config getConfiguration() {
        return configuration;
    }

    public FileAPI getDumpfileAPI() {
        return dumpfileAPI;
    }
}
