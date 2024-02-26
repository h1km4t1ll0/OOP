package ru.nsu.dolgov.notprimefinder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Class for NonPrimeLinear class test.
 */
public class NonPrimeLinearTest {
    @Test
    void testSmallSequenceWithOneNotPrimeNumber() {
        int[] numbersToTest = new int[1000];
        Arrays.fill(numbersToTest, 0, 999, 11);
        NonPrimeLinearChecker linearChecker = new NonPrimeLinearChecker();
        numbersToTest[999] = 4;
        assertTrue(linearChecker.check(numbersToTest));
    }

    @Test
    void testSmallSequenceWithoutNotPrimeNumbers() {
        int[] numbersToTest = new int[1000];
        Arrays.fill(numbersToTest, 0, 1000, 11);
        NonPrimeLinearChecker linearChecker = new NonPrimeLinearChecker();
        assertFalse(linearChecker.check(numbersToTest));
    }

    @Test
    void testBigSequenceWithOneNotPrimeNumber() {
        int[] numbersToTest = new int[21474836];
        Arrays.fill(numbersToTest, 0, 21474835, 11);
        numbersToTest[21474835] = 4;
        NonPrimeLinearChecker linearChecker = new NonPrimeLinearChecker();
        assertTrue(linearChecker.check(numbersToTest));
    }

    @Test
    void testBigSequenceWithoutNotPrimeNumbers() {
        int[] numbersToTest = new int[21474836];
        Arrays.fill(numbersToTest, 0, 21474836, 11);
        NonPrimeLinearChecker linearChecker = new NonPrimeLinearChecker();
        assertFalse(linearChecker.check(numbersToTest));
    }
}