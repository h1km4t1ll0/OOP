package ru.nsu.dolgov.notprimefinder;

import java.util.Arrays;

/**
 * Not prime numbers finder implementation with stream API.
 */
public class NonPrimeStreamChecker extends NonPrimeChecker implements Checker {
    /**
     * An override for check method in the Checker interface.
     *
     * @param array array of numbers to proceed.
     * @return true if array includes at least one not prime number.
     */
    @Override
    public boolean check(int[] array) {
        return Arrays.stream(array).parallel().anyMatch(number -> !isPrime(number));
    }
}
