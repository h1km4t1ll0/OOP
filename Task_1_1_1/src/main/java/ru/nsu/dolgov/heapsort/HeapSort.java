package ru.nsu.dolgov.heapsort;

import java.util.Arrays;

/**
 * Class for heap sort.
 *
 * @author Долгов Даниил
 * @version 1.0
 */
public class HeapSort {
    /**
     * A helper for sort function.
     *
     * @param lastIndexInArray последний индекс в массиве
     * @param index индекс наибольшего элемента
     */
    private static void heapify(int lastIndexInArray, int index, int[] array) {
        int largestElementIndex = index; // Инициализируем наибольший элемент как корень
        int leftElementIndex = 2 * index + 1;
        int rightElementIndex = 2 * index + 2;

        // If left child element is greater than the greatest element
        if (leftElementIndex < lastIndexInArray
                && array[leftElementIndex] > array[largestElementIndex]) {
            largestElementIndex = leftElementIndex;
        }

        // If right child element is greater than the greatest element
        if (rightElementIndex < lastIndexInArray
                && array[rightElementIndex] > array[largestElementIndex]) {
            largestElementIndex = rightElementIndex;
        }
        // If the largest element is not a root element
        if (largestElementIndex != index) {
            int swap = array[index];
            array[index] = array[largestElementIndex];
            array[largestElementIndex] = swap;

            heapify(lastIndexInArray, largestElementIndex, array);
        }
    }

    /**
     * Main function in HeapSort class.
     *
     * @return возвращает вывод ф-ии heapify
     */
    public static int[] sort(int[] array) {
        int lastIndexInArray = array.length;

        for (int i = lastIndexInArray / 2 - 1; i >= 0; i--) {
            heapify(lastIndexInArray, i, array);
        }

        for (int i = lastIndexInArray - 1; i >= 0; i--) {
            // Move current root in the end of the array
            int buffer = array[0];
            array[0] = array[i];
            array[i] = buffer;

            heapify(i, 0, array);
        }

        return array;
    }

    /**
     * An entrypoint function in HeapSort class.
     *
     * @param args параметры из командной строки
     */
    public static void main(String[] args) {
        int[] arr = new int[]{19, 11, 13, 5, 6, 7, -190, 100, 34, 6, 6, 6};
        System.out.println("Input: " + Arrays.toString(arr));
        System.out.println("Output: " + Arrays.toString(HeapSort.sort(arr)));
    }
}