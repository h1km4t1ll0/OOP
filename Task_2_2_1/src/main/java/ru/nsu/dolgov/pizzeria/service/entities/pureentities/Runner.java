package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.init.Init;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueue;
import ru.nsu.dolgov.pizzeria.service.interfaces.EmployeeI;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner extends Thread {
    private final List<Future<?>> employeeFuture = new ArrayList<>();
    private List<EmployeeI> bakers;
    private List<EmployeeI> deliverers;
    private ExecutorService bakerExecutor;
    private ExecutorService delivererExecutor;
    private final Init initializer;
    private final long delay;

    public Runner(Init initializer, long delay) {
        this.initializer = initializer;
        this.delay = delay;
    }

    private void initExecutors() {
        this.bakers = this.initializer.initBakers();
        this.deliverers = this.initializer.initDeliverers();
        this.bakerExecutor = Executors.newFixedThreadPool(this.bakers.size());
        this.delivererExecutor = Executors.newFixedThreadPool(this.deliverers.size());
    }

    private void runTask(List<EmployeeI> employees, ExecutorService executorService) {
        for (EmployeeI employee : employees) {
            employeeFuture.add(executorService.submit(
                    employee::consume
            ));
        }
    }

    private void runTasks() {
        this.initExecutors();
        this.runTask(this.bakers, this.bakerExecutor);
        this.runTask(this.deliverers, this.delivererExecutor);
    }

    @Override
    public void run() {
        this.runTasks();
        try {
            sleep(this.delay);
        } catch (InterruptedException e) {
            System.out.println("Unexpected interruption at runner!");
        }
        System.out.println("STOP!!!");

        this.delivererExecutor.shutdownNow();
        this.bakerExecutor.shutdownNow();

        for (Future<?> employeeFuture : this.employeeFuture) {
            try {
                employeeFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.println(employeeFuture.isDone());
        }


        BlockingQueue<Order> waitingQueue = this.initializer.getQueueLocator().waitingQueue();
        BlockingQueue<Order> deliveryQueue = this.initializer.getQueueLocator().deliveryQueue();
        BlockingQueue<Order> doneQueue = this.initializer.getQueueLocator().doneQueue();
        BlockingQueue<Order> pendingWaitingQueue = initializer.getQueueLocator().pendingWaitingQueue();
        BlockingQueue<Order> pendingDeliveryQueue = this.initializer.getQueueLocator().pendingDeliveryQueue();
        System.out.println(waitingQueue.getDump());
        System.out.println(deliveryQueue.getDump());
        System.out.println(pendingWaitingQueue.getDump());
        System.out.println(pendingDeliveryQueue.getDump());
        System.out.println(doneQueue.getDump());
    }
}
