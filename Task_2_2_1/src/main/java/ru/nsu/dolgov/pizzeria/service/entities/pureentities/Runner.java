package ru.nsu.dolgov.pizzeria.service.entities.pureentities;

import ru.nsu.dolgov.pizzeria.service.Utils.LogLevel;
import ru.nsu.dolgov.pizzeria.service.init.Init;
import ru.nsu.dolgov.pizzeria.service.interfaces.BaseConsumerI;
import ru.nsu.dolgov.pizzeria.service.interfaces.BlockingQueueI;
import ru.nsu.dolgov.pizzeria.service.interfaces.EmployeeI;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ru.nsu.dolgov.pizzeria.service.Utils.log;

public class Runner extends Thread {
    private final List<Future<?>> employeeFuture = new ArrayList<>();
    private List<BaseConsumerI> bakers;
    private List<BaseConsumerI> deliverers;
    private List<BaseConsumerI> customers;
    private ExecutorService bakerExecutor;
    private ExecutorService delivererExecutor;
    private ExecutorService customerExecutor;
    private final Init initializer;
    private final long delay;

    public Runner(Init initializer, long delay) {
        this.initializer = initializer;
        this.delay = delay;
    }

    private void initExecutors() {
        this.bakers = this.initializer.initBakers();
        this.deliverers = this.initializer.initDeliverers();
        this.customers = this.initializer.initCustomers();
        this.bakerExecutor = Executors.newFixedThreadPool(this.bakers.size());
        this.delivererExecutor = Executors.newFixedThreadPool(this.deliverers.size());
        this.customerExecutor = Executors.newFixedThreadPool(this.deliverers.size());
    }

    private void runTask(List<BaseConsumerI> consumers, ExecutorService executorService) {
        for (BaseConsumerI consumer : consumers) {
            employeeFuture.add(executorService.submit(
                consumer::consume
            ));
        }
    }

    private void runTasks() {
        this.initExecutors();
        this.runTask(this.bakers, this.bakerExecutor);
        this.runTask(this.deliverers, this.delivererExecutor);
        this.runTask(this.customers, this.customerExecutor);
    }

    private void shutdown() {
        this.delivererExecutor.shutdownNow();
        this.bakerExecutor.shutdownNow();
        this.customerExecutor.shutdownNow();
    }

    private void waitForCleanup() {
        for (Future<?> employeeFuture : this.employeeFuture) {
            try {
                employeeFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        this.runTasks();
        try {
            sleep(this.delay);
        } catch (InterruptedException e) {
            log(
                LogLevel.ERROR,
                "Unexpected InterruptedException!",
                "runner"
            );
        }
        log(
            LogLevel.INFO,
            "Pizzeria is ended its' work. Waiting till all threads do the cleanup...",
            "runner"
        );
        this.shutdown();
        this.waitForCleanup();
        log(
            LogLevel.INFO,
            "All threads have done the cleanup. Exiting.",
            "runner"
        );
        BlockingQueueI<Order> waitingQueue = this.initializer.getQueueLocator().waitingQueue();
        BlockingQueueI<Order> deliveryQueue = this.initializer.getQueueLocator().deliveryQueue();
        BlockingQueueI<Order> doneQueue = this.initializer.getQueueLocator().doneQueue();
        BlockingQueueI<Order> pendingWaitingQueue = initializer.getQueueLocator().pendingWaitingQueue();
        BlockingQueueI<Order> pendingDeliveryQueue = this.initializer.getQueueLocator().pendingDeliveryQueue();
        System.out.println(waitingQueue.getDump().size());
        System.out.println(deliveryQueue.getDump().size());
        System.out.println(pendingWaitingQueue.getDump().size());
        System.out.println(pendingDeliveryQueue.getDump().size());
        System.out.println(doneQueue.getDump().size());
    }
}
