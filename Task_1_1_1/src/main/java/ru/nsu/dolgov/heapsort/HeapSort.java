package ru.nsu.dolgov.heapsort;

/**
 * Class for heap sort.
 */
public class HeapSort {
    private final int[] array;

    /**
     *
     * @param array
     */
    public HeapSort(int[] array) {
        this.array = array;
    }

    /**
     *
     * @param lastIndexInArray
     * @param index
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
     *
     * @return
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
}