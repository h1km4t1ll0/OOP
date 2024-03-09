package ru.nsu.dolgov.pizzeria.service.interfaces;

import org.jetbrains.annotations.NotNull;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.Order;
import ru.nsu.dolgov.pizzeria.service.entities.pureentities.OrderState;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static ru.nsu.dolgov.pizzeria.service.Utils.Colors.*;
import static ru.nsu.dolgov.pizzeria.service.Utils.Colors.RESET;

public interface Employee {
    UUID getEmployeeUUID();
    Order getOrder();
    void putOrder(Order order);
    void startConsuming();
    static void changeStateOfOrder(@NotNull Order order, @NotNull OrderState newState) {
        String currentTime = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
        System.out.printf(
            "%s[%s][%s]%s Order state changed %s%s%s -> %s",
            YELLOW_BOLD_BRIGHT, currentTime, order.getId(), RESET,
            CYAN_BOLD_BRIGHT, order.getState().name(), RESET,
            newState.name());
        order.setState(newState);
    }
    void consumeOrder();
}
