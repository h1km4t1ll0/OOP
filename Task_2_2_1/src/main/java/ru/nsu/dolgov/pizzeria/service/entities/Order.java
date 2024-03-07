package ru.nsu.dolgov.pizzeria.service.entities;

import java.util.UUID;

public class Order {
    private UUID id;
    private OrderState state;

    public Order() {
        this.id = UUID.randomUUID();
        this.state = OrderState.WAITING;
    }

    public void setState(OrderState newState) {
        this.state = newState;
    }
    public OrderState getState() {
        return this.state;
    }
}
