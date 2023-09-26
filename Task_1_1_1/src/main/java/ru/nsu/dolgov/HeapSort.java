package ru.nsu.dolgov;

import java.util.Arrays;

/**
 * Class for heap sort.
 *
 * @author Долгов Даниил
 * @version 1.0
 */
public class HeapSort {
    private final int[] array;

    /**
     * Constructor of HeapSort class.
     *
     * @param array массив интов на вход
     */
    public HeapSort(int[] array) {
        this.array = array;
    }

    /**
     * A helper for sort function.
     *
     * @param lastIndexInArray последний индекс в массиве
     * @param index индекс наибольшего элемента
     */
    void heapify(int lastIndexInArray, int index) {
        int largestElementIndex = index; // Инициализируем наибольший элемент как корень
        int leftElementIndex = 2 * index + 1;
        int rightElementIndex = 2 * index + 2;

        // If left child element is greater than the greatest element
        if (leftElementIndex < lastIndexInArray
                && this.array[leftElementIndex] > this.array[largestElementIndex]) {
            largestElementIndex = leftElementIndex;
        }

        // If right child element is greater than the greatest element
        if (rightElementIndex < lastIndexInArray
                && this.array[rightElementIndex] > this.array[largestElementIndex]) {
            largestElementIndex = rightElementIndex;
        }
        // If the largest element is not a root element
        if (largestElementIndex != index) {
            int swap = this.array[index];
            this.array[index] = this.array[largestElementIndex];
            this.array[largestElementIndex] = swap;

            heapify(lastIndexInArray, largestElementIndex);
        }
    }

    /**
     * Main function in HeapSort class.
     *
     * @return возвращает вывод ф-ии heapify
     */
    public int[] sort() {
        int lastIndexInArray = this.array.length;

        for (int i = lastIndexInArray / 2 - 1; i >= 0; i--) {
            heapify(lastIndexInArray, i);
        }

        for (int i = lastIndexInArray - 1; i >= 0; i--) {
            // Move current root in the end of the array
            int buffer = this.array[0];
            this.array[0] = this.array[i];
            this.array[i] = buffer;

            heapify(i, 0);
        }

        return this.array;
    }

    /**
     * An entrypoint function in HeapSort class.
     *
     * @param args параметры из командной строки
     */
    public static void main(String[] args) {
        int[] arr = new int[]{19, 11, 13, 5, 6, 7, -190, 100, 34, 6, 6, 6};
        HeapSort instance = new HeapSort(arr);
        System.out.println("Input: " + Arrays.toString(arr));
        System.out.println("Output: " + Arrays.toString(instance.sort()));
    }
}