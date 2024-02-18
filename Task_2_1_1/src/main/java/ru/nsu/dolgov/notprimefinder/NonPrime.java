package ru.nsu.dolgov.notprimefinder;

/**
 * Simple finder of not prime numbers.
 */
public abstract class NonPrime implements Checker {
    /**
     * Method to check whether number is prime or not.
     *
     * @param number number to check on primeness.
     * @return true if number is prime false otherwise.
     */
    static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        if (number == 2 || number == 3){
            return true;
        }

        if (number % 2 == 0 || number % 3 == 0){
            return false;
        }

        for (int i = 5; i * i < number; i = i + 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}
