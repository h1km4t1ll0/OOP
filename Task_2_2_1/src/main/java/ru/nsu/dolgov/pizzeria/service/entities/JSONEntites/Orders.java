package ru.nsu.dolgov.pizzeria.service.entities.JSONEntites;

import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;

import java.util.Deque;

/**
 * Class to parse orders from dump.json file.
 */
public class Orders {
    public Deque<Order> waitingQueue;
    public Deque<Order> deliveryQueue;
    public Deque<Order> pendingDeliveryQueue;
    public Deque<Order> pendingWaitingQueue;
}
