package ru.nsu.dolgov.heapsort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HeapSortTest {
    @Test
    void testPositiveArray() {
        final HeapSort sampleInstance = new HeapSort(new int[]{1, 6, 7, 3, 2, 9, 8});
        assertArrayEquals(new int[]{1, 2, 3, 6, 7, 8, 9}, sampleInstance.sort());
    }

    @Test
    void testNegativeArray() {
        final HeapSort sampleInstance = new HeapSort(new int[]{1, 1, 1, 6, 7, 3, 2, 9, 8, -10, -90, -89, -2324});
        assertArrayEquals(new int[]{-2324, -90, -89, -10, 1, 1, 1, 2, 3, 6, 7, 8, 9}, sampleInstance.sort());
    }

    @Test
    void testZeroArray() {
        final HeapSort sampleInstance = new HeapSort(new int[]{});
        assertArrayEquals(new int[]{}, sampleInstance.sort());
    }

    @Test
    void testArrayOfZeros() {
        int[] array = new int[100];
        int[] arrayCopy = new int[100];

        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
            arrayCopy[i] = 0;
        }

        final HeapSort sampleInstance = new HeapSort(array);
        assertArrayEquals(arrayCopy, sampleInstance.sort());
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
        final HeapSort sampleInstance = new HeapSort(array);
        assertArrayEquals(arrayCopy, sampleInstance.sort());
    }

    @Test
    void testRepeatedNumbers() {
        final HeapSort sampleInstance = new HeapSort(new int[]{1, 1, 1, 6, 7, 3, 2, 9, 8});
        assertArrayEquals(new int[]{1, 1, 1, 2, 3, 6, 7, 8, 9}, sampleInstance.sort());
    }
}