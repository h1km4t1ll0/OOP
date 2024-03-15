package ru.nsu.dolgov.pizzeria.service.interfaces;

import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;

public interface BaseConsumerI {
    void consume();
    void putOrder(Order order) throws InterruptedException;
}
