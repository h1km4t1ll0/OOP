package ru.nsu.dolgov.notprimefinder;

/**
 * Interface describing check method.
 */
public interface Checker {
    /**
     * Checks whether at least one not prime number exists in the given array.
     *
     * @param array array to check.
     * @return true if at least one not prime number exists in the given array.
     * @throws InterruptedException if interrupted.
     */
    boolean check(int[] array) throws InterruptedException;
}
