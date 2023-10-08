package ru.nsu.dolgov;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class HeapSortTest {
    @Test
    void testPositiveArray() {
        assertArrayEquals(new int[] {
            1, 2, 3, 6, 7, 8, 9
        }, HeapSort.sort(
                new int[]{1, 6, 7, 3, 2, 9, 8}
        ));
    }

    @Test
    void testNegativeArray() {
        assertArrayEquals(new int[]{
            -2324, -90, -89, -10, 1, 1, 1, 2, 3, 6, 7, 8, 9
        }, HeapSort.sort(new int[]{
            1, 1, 1, 6, 7, 3, 2, 9, 8, -10, -90, -89, -2324
        }));
    }

    @Test
    void testZeroArray() {
        assertArrayEquals(new int[]{}, HeapSort.sort(new int[]{}));
    }

    @Test
    void testArrayOfZeros() {
        int[] array = new int[100];
        int[] arrayCopy = new int[100];

        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
            arrayCopy[i] = 0;
        }
        assertArrayEquals(arrayCopy, HeapSort.sort(array));
    }

    @Test
    void testBigArray() {
        int[] array = new int[1000000];
        int[] arrayCopy = new int[1000000];
        Random random = new Random();

        for (int i = array.length - 1; i >= 0; i--) {
            array[i] = random.nextInt();
            arrayCopy[i] = array[i];
        }

        Arrays.sort(arrayCopy);
        assertArrayEquals(arrayCopy, HeapSort.sort(array));
    }

    @Test
    void testRepeatedNumbers() {
        assertArrayEquals(new int[]{
            1, 1, 1, 2, 3, 6, 7, 8, 9
        }, HeapSort.sort( new int[]{1, 1, 1, 6, 7, 3, 2, 9, 8}));
    }

    @Test
    void checkEntrypoint() {
        String[] testArguments = new String[]{"tetsArg"};
        HeapSort.main(testArguments);
        assertTrue(true);
    }
}
