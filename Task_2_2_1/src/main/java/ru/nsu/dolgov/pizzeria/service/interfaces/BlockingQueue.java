package ru.nsu.dolgov.pizzeria.service.interfaces;


public interface BlockingQueue<T> {
    public T get();
    public void put(T item);
    public int getCapacity();
    public boolean isEmpty();
}
