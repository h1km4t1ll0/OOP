package ru.nsu.dolgov.pizzeria.service.interfaces;


import java.util.Deque;

public interface BlockingQueueI<T> {
    public T get() throws InterruptedException;
    public void put(T item) throws InterruptedException;
    public boolean isEmpty();
    public Deque<T> getDump();
}
