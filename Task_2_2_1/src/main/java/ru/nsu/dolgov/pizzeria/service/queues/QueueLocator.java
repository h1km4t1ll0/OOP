package ru.nsu.dolgov.pizzeria.service.queues;

/**
 * A locator for the queues.
 *
 * @param waitingQueue waiting queue.
 * @param deliveryQueue delivery queue.
 * @param doneQueue done queue.
 * @param pendingDeliveryQueue dump for the delivery queue.
 * @param pendingWaitingQueue dump for the waiting queue.
 */
public record QueueLocator(
        BaseQueue waitingQueue,
        BaseQueue deliveryQueue,
        UnlimitedQueue doneQueue,
        UnlimitedQueue pendingDeliveryQueue,
        UnlimitedQueue pendingWaitingQueue
) {
}
