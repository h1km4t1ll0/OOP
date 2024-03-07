package ru.nsu.dolgov.pizzeria.service.interfaces;

import ru.nsu.dolgov.pizzeria.service.entities.Order;
import ru.nsu.dolgov.pizzeria.service.entities.OrderState;

public interface Employee {
    public Order getOrder();
    public void putOrder(Order order);
    public void startConsuming();
    public void changeStateOfOrder(Order order, OrderState newState);
    public void consumeOrder();
}
