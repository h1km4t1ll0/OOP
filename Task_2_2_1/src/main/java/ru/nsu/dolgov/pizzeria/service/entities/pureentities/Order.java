package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import java.util.UUID;

import static ru.nsu.dolgov.pizzeria.service.Utils.getUUID;

public class Order {
    private final UUID id;
    private OrderState state;

    public Order() {
        this.id = getUUID();
        this.state = OrderState.WAITING;
    }

    public void setState(OrderState newState) {
        this.state = newState;
    }
    public OrderState getState() {
        return this.state;
    }

    public UUID getId() {
        return id;
    }
}
