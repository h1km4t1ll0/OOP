package ru.nsu.dolgov.notprimefinder;


/**
 * Not prime numbers finder implementation with simple cycle.
 */
public class NonPrimeLinearChecker extends NonPrimeChecker implements Checker {
    /**
     * An override for check method in the Checker interface.
     *
     * @param array array of numbers to proceed.
     * @return true if array includes at least one not prime number.
     */
    @Override
    public boolean check(int[] array) {
        for (int number : array) {
            if (!isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}
