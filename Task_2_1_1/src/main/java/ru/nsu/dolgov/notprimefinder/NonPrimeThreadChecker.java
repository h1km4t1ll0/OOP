package ru.nsu.dolgov.notprimefinder;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Not prime numbers finder implementation with thread-pool.
 */
public class NonPrimeThreadChecker extends NonPrime implements Checker {
    private final int threadQuantity;
    private BlockingQueue<Integer> numbers;
    private ExecutorService threadPool;
    private List<Callable<Boolean>> tasksToExecute;

    /**
     * NonPrimeThread will use the number of threads equal
     * to the number of available logical cores.
     */
    NonPrimeThreadChecker() {
        this.threadQuantity = Runtime.getRuntime().availableProcessors();
    }

    /**
     * NonPrimeThread will use given number of threads equal.
     *
     * @param threadQuantity number of threads.
     */
    NonPrimeThreadChecker(int threadQuantity) {
        this.threadQuantity = threadQuantity;
    }

    /**
     * Factory for tasks.
     *
     * @return task to execute.
     */
    private Callable<Boolean> taskFactory() {
        return () -> {
            Integer nextNumber = this.numbers.poll();
            while (nextNumber != null) {
                if (!isPrime(nextNumber)) {
                    return true;
                }
                nextNumber = this.numbers.poll();
            }
            return false;
        };
    }

    /**
     * Method to init blocking queue to store numbers
     * and to create a thread-pool.
     *
     * @param array array of numbers to proceed.
     */
    private void prepare(int[] array) {
        this.numbers = new ArrayBlockingQueue<>(array.length);

        for (int each : array) {
            this.numbers.add(each);
        }

        this.threadPool = Executors.newFixedThreadPool(this.threadQuantity);

        this.tasksToExecute = new java.util.ArrayList<>();
        for (int i = 0; i < this.threadQuantity; i++) {
            tasksToExecute.add(this.taskFactory());
        }
    }

    /**
     * An override for check method in the Checker interface.
     *
     * @param array array of numbers to proceed.
     * @return true if array includes at least one not prime number.
     */
    @Override
    public boolean check(int[] array) throws InterruptedException {
        this.prepare(array);
        return this.threadPool.invokeAll(
            this.tasksToExecute
        ).stream().anyMatch(
            executedTaskValue -> {
                try {
                    return executedTaskValue.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                } finally {
                    this.threadPool.shutdownNow();
                }
            }
        );
    }
}
