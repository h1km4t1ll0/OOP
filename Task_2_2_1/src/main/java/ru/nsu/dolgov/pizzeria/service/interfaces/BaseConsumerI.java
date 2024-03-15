package ru.nsu.dolgov.pizzeria.service.interfaces;

import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;

/**
 * An interface for all entities.
 */
public interface BaseConsumerI {
    void consume();

    void putOrder(Order order) throws InterruptedException;
}
