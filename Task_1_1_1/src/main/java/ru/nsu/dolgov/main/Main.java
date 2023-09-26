package ru.nsu.dolgov.main;

import java.util.Arrays;
import ru.nsu.dolgov.heapsort.HeapSort;

/**
 * Entrypoint for heap sort.
 */
public class Main {
    /**
     * An entrypoint to the heap sort.
     *
     * @param args массив аргументов на вход
     */
    public static void main(String[] args) {
        int[] arr = new int[]{19, 11, 13, 5, 6, 7, -190, 100, 34, 6, 6, 6};
        HeapSort instance = new HeapSort(arr);
        System.out.println("Input: " + Arrays.toString(arr));
        System.out.println("Output: " + Arrays.toString(instance.sort()));
    }
}
