package ru.nsu.dolgov.pizzeria.service.queues;

public record QueueLocator(
    BaseQueue waitingQueue,
    BaseQueue deliveryQueue,
    BaseQueue doneQueue
) {}
