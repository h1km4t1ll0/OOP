package ru.nsu.dolgov.pizzeria.service.init;

import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Baker;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Deliverer;
import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.Bakers;
import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.Config;
import ru.nsu.dolgov.pizzeria.service.entities.JSONEntites.Deliverers;
import ru.nsu.dolgov.pizzeria.service.queues.BaseQueue;
import ru.nsu.dolgov.pizzeria.service.queues.QueueLocator;

import java.util.ArrayList;
import java.util.List;

import static ru.nsu.dolgov.pizzeria.service.Utils.getRandomNumberFromRange;

public class Init {
    private final Config configuration;
    private final QueueLocator queueLocator;
    public Init(Parser parser) {
        this.configuration = parser.parse();
        this.queueLocator = this.declareQueues();
    }

    public List<Baker> initBakers() {
        Bakers bakersConfiguration = this.configuration.bakers;
        List<Baker> bakerList = new ArrayList<>();
        int randomEfficiency = getRandomNumberFromRange(1, 10);
        for (int i = 0; i < bakersConfiguration.quantity; i ++) {
            bakerList.add(new Baker(
                randomEfficiency,
                this.queueLocator.deliveryQueue(),
                this.queueLocator.waitingQueue(),
                this.configuration.bakery.durationOfTheDayInSeconds
            ));
        }
        return bakerList;
    }

    public List<Deliverer> initDeliverers() {
        Deliverers deliverersConfiguration = this.configuration.deliverers;
        List<Deliverer> delivererList = new ArrayList<>();
        int randomEfficiency = getRandomNumberFromRange(1, 10);
        for (int i = 0; i < deliverersConfiguration.quantity; i ++) {
            delivererList.add(new Deliverer(
                deliverersConfiguration.capacity,
                randomEfficiency,
                this.queueLocator.deliveryQueue(),
                this.queueLocator.doneQueue(),
                this.configuration.bakery.durationOfTheDayInSeconds
            ));
        }
        return delivererList;
    }

    private QueueLocator declareQueues() {
        return new QueueLocator(
                new BaseQueue(this.configuration.bakery.waitingQueueCapacity),
                new BaseQueue(this.configuration.bakery.deliveryQueueCapacity),
                new BaseQueue(
                this.configuration.bakery.deliveryQueueCapacity +
                        this.configuration.bakery.waitingQueueCapacity
                )
        );
    }
    public QueueLocator getQueueLocator() {
        return this.queueLocator;
    }

    public Config getConfiguration() {
        return configuration;
    }
}
