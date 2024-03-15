package ru.nsu.dolgov.pizzeria.service.init;

import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.Customers;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Baker;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Customer;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Deliverer;
import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.Bakers;
import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.Config;
import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.Deliverers;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;
import ru.nsu.dolgov.pizzeria.service.interfaces.BaseConsumerI;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueueI;
import ru.nsu.dolgov.pizzeria.service.interfaces.EmployeeI;
import ru.nsu.dolgov.pizzeria.service.queues.BaseQueue;
import ru.nsu.dolgov.pizzeria.service.queues.QueueLocator;
import ru.nsu.dolgov.pizzeria.service.queues.UnlimitedQueue;

import java.util.ArrayList;
import java.util.List;

import static ru.nsu.dolgov.pizzeria.service.Utils.getRandomNumberFromRange;

public class Init {
    private final Config configuration;
    private final QueueLocator queueLocator;
    public Init(String configurationFile) {
        this.configuration = new FileAPI(configurationFile).parse();
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
        return new QueueLocator(
            new BaseQueue(this.configuration.bakery.waitingQueueCapacity),
            new BaseQueue(this.configuration.bakery.deliveryQueueCapacity),
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
}
