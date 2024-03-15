package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import java.util.UUID;

import static ru.nsu.dolgov.pizzeria.service.Utils.getUUID;

/**
 * Class to implement a single order.
 */
public class Order {
    private final UUID id;
    private OrderState state;

    public Order() {
        this.id = getUUID();
        this.state = OrderState.WAITING;
    }

    public Order(UUID id, OrderState state) {
        this.id = id;
        this.state = state;
    }

    /**
     * A setter for the state.
     *
     * @param newState new state of an order.
     */
    public void setState(OrderState newState) {
        this.state = newState;
    }

    /**
     * A getter for the state.
     *
     * @return current state of order.
     */
    public OrderState getState() {
        return this.state;
    }

    /**
     * A getter for an ID.
     *
     * @return id of the current order.
     */
    public UUID getId() {
        return this.id;
    }
}
