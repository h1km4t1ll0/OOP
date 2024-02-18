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
        assertTrue(NonPrime.isPrime(11));
    }

    @Test
    void testSmallNumberFalse() {
        assertFalse(NonPrime.isPrime(9));
    }

    @Test
    void testBigNumberFalse() {
        assertTrue(NonPrime.isPrime(568713463));
    }

    @Test
    void testBigNumberTrue() {
        assertFalse(NonPrime.isPrime(568713467));
    }
}
