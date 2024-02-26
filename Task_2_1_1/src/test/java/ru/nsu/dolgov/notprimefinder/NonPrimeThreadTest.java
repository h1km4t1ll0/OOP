package ru.nsu.dolgov.notprimefinder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Class for NonPrimeThreadChecker class test.
 */
public class NonPrimeThreadTest {
    @Test
    void testSmallSequenceWithOneNotPrimeNumber() throws InterruptedException {
        int[] numbersToTest = new int[1000];
        Arrays.fill(numbersToTest, 0, 999, 11);
        NonPrimeThreadChecker streamChecker = new NonPrimeThreadChecker();
        numbersToTest[999] = 4;
        assertTrue(streamChecker.check(numbersToTest));
    }

    @Test
    void testSmallSequenceWithoutNotPrimeNumbers() throws InterruptedException {
        int[] numbersToTest = new int[1000];
        Arrays.fill(numbersToTest, 0, 1000, 11);
        NonPrimeThreadChecker streamChecker = new NonPrimeThreadChecker();
        assertFalse(streamChecker.check(numbersToTest));
    }

    @Test
    void testBigSequenceWithOneNotPrimeNumber() throws InterruptedException {
        int[] numbersToTest = new int[21474836];
        Arrays.fill(numbersToTest, 0, 21474835, 11);
        numbersToTest[21474835] = 4;
        NonPrimeThreadChecker streamChecker = new NonPrimeThreadChecker();
        assertTrue(streamChecker.check(numbersToTest));
    }

    @Test
    void testBigSequenceWithoutNotPrimeNumbers() throws InterruptedException {
        int[] numbersToTest = new int[21474836];
        Arrays.fill(numbersToTest, 0, 21474836, 11);
        NonPrimeThreadChecker streamChecker = new NonPrimeThreadChecker();
        assertFalse(streamChecker.check(numbersToTest));
    }
}
