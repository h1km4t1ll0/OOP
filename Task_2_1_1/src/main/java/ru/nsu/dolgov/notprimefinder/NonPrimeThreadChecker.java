package ru.nsu.dolgov.notprimefinder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Not prime numbers finder implementation with thread-pool.
 */
public class NonPrimeThreadChecker extends NonPrimeChecker implements Checker {
    private final int threadQuantity;
    private ExecutorService threadPool;
    private List<Future<Boolean>> futures;
    // Шатдаун флаг, чтобы потоки знали, что пора сворачиваться.
    private volatile Boolean shutdownFlag = false;

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
    private Boolean taskFactory(int[] numbers) {
        for (var each : numbers) {
            if (Thread.currentThread().isInterrupted()) {
                return false;
            }
            if (this.shutdownFlag) {
                return false;
            }
            if (!isPrime(each)) {
                this.shutdownFlag = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Gets partial array of integers to proceed.
     *
     * @param origin original array.
     * @param from start of new array.
     * @param length length of result array.
     * @return partial array.
     */
    private int[] getPartialArray(int[] origin, int from, int length) {
        int[] partialArray = new int[length];
        System.arraycopy(origin, from, partialArray, 0, length);
        return partialArray;
    }

    /**
     * Method to init tasks to execute.
     *
     * @param array array of numbers to proceed.
     */
    private void prepare(int[] array) {
        this.threadPool = Executors.newFixedThreadPool(this.threadQuantity);
        int batchSize = array.length / this.threadQuantity;
        this.futures = new ArrayList<>();
        for (int i = 0; i < this.threadQuantity; i++) {
            int length = (i + 1) * batchSize;
            if (i + 1 == this.threadQuantity) {
                length = array.length;
            }
            int from = i * batchSize;
            int finalLength = length - from;
            this.futures.add(
                this.threadPool.submit(
                    () -> this.taskFactory(
                        this.getPartialArray(
                            array,
                            from,
                            finalLength
                        )
                    )
                )
            );
        }
    }

    /**
     * An override for check method in the Checker interface.
     *
     * @param array array of numbers to proceed.
     * @return true if array includes at least one not prime number.
     */
    @Override
    public boolean check(int[] array) {
        try {
            this.prepare(array);
            for (Future<Boolean> result : this.futures) {
                if (result.get()) {
                    this.shutdownFlag = true;
                    return true;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            this.threadPool.shutdownNow();
        }
        return false;
    }
}
