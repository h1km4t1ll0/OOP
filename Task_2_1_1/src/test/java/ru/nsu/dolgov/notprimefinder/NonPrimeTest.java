package ru.nsu.dolgov.notprimefinder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * Class for NonPrime class test.
 */
public class NonPrimeTest {
    @Test
    void testSmallNumberTrue() {
        assertTrue(NonPrimeChecker.isPrime(11));
    }

    @Test
    void testSmallNumberFalse() {
        assertFalse(NonPrimeChecker.isPrime(9));
    }

    @Test
    void testBigNumberFalse() {
        assertTrue(NonPrimeChecker.isPrime(568713463));
    }

    @Test
    void testBigNumberTrue() {
        assertFalse(NonPrimeChecker.isPrime(568713467));
    }
}
