package ru.nsu.dolgov.notprimefinder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Class for NonPrimeStreamChecker class test.
 */
public class NonPrimeStreamTest {
    @Test
    void testSmallSequenceWithOneNotPrimeNumber() {
        int[] numbersToTest = new int[1000];
        java.util.Arrays.fill(numbersToTest, 0, 999, 11);
        NonPrimeStreamChecker streamChecker = new NonPrimeStreamChecker();
        numbersToTest[999] = 4;
        assertTrue(streamChecker.check(numbersToTest));
    }

    @Test
    void testSmallSequenceWithoutNotPrimeNumbers() {
        int[] numbersToTest = new int[1000];
        java.util.Arrays.fill(numbersToTest, 0, 1000, 11);
        NonPrimeStreamChecker streamChecker = new NonPrimeStreamChecker();
        assertFalse(streamChecker.check(numbersToTest));
    }

    @Test
    void testBigSequenceWithOneNotPrimeNumber() {
        int[] numbersToTest = new int[21474836];
        java.util.Arrays.fill(numbersToTest, 0, 21474835, 11);
        numbersToTest[21474835] = 4;
        NonPrimeStreamChecker streamChecker = new NonPrimeStreamChecker();
        assertTrue(streamChecker.check(numbersToTest));
    }

    @Test
    void testBigSequenceWithoutNotPrimeNumbers() {
        int[] numbersToTest = new int[21474836];
        java.util.Arrays.fill(numbersToTest, 0, 21474836, 11);
        NonPrimeStreamChecker streamChecker = new NonPrimeStreamChecker();
        assertFalse(streamChecker.check(numbersToTest));
    }
}
