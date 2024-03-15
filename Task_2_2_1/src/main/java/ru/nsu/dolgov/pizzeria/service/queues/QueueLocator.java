package ru.nsu.dolgov.pizzeria.service.queues;

public record QueueLocator(
    BaseQueue waitingQueue,
    BaseQueue deliveryQueue,
    UnlimitedQueue doneQueue,
    UnlimitedQueue pendingDeliveryQueue,
    UnlimitedQueue pendingWaitingQueue
) {}
