package edu.code.samples.sort;

import static edu.code.samples.generic.Utils.swap;

import java.util.Random;

public class Sort {

    public static void quickSort(int [] array, int size) {
        _quickSort(array, 0, size-1);
    }

    public static void mergeSort(int [] array, int size) {
        _mergeSort(array, 0, size-1);
    }

    private static void _quickSort(int [] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int partitionIndex = randomPivotSelect(array, left, right);
        _quickSort(array, left, partitionIndex -1);
        _quickSort(array, partitionIndex + 1, right);
    }

    private static int randomPivotSelect(int [] array, int left, int right) {
        Random random = new Random();
        int pivotIndex = Math.abs(random.nextInt() % (right - left + 1)) + left;

        for (int i= pivotIndex-1; i >= left; i--) {
            if (array[i] > array[pivotIndex]) {
                swap(array, pivotIndex-1, i);
                swap(array, pivotIndex, pivotIndex-1);
                pivotIndex--;
            }
        }

        for (int i = pivotIndex + 1; i <= right; i++) {
            if (array[i] < array[pivotIndex]) {
                swap(array, pivotIndex+1, i);
                swap(array, pivotIndex, pivotIndex+1);
                pivotIndex++;
            }
        }

        return pivotIndex;
    }

    private static void _mergeSort(int [] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = (left+right) /2;
        _mergeSort(array, left, mid);
        _mergeSort(array, mid+1, right);
        merge(array, left, mid, right);
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int [] copy = new int[right - left + 1];
        int i = left, j = mid+1, k = 0;

        while (i <= mid && j <= right) {
            if (array[i] < array[j]) {
                copy[k++] = array[i++];
            } else {
                copy[k++] = array[j++];
            }
        }

        while (i <= mid) {
            copy[k++] = array[i++];
        }

        while (j <= right) {
            copy[k++] = array[j++];
        }

        // copy it back
        int s = left;
        while (s <= right) {
            array[s] = copy[s-left];
            s++;
        }
    }
}
