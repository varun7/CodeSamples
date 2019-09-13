package edu.code.samples.sort;

import static edu.code.samples.generic.Utils.swap;

import java.util.Random;

public class Sort {

    public static int binarySearchRotatedArray(int[] array, int element) {
        int low = 0;
        int end = array.length - 1;
        int mid;
        while (low <= end) {
            mid = (low + end)/2;
            if (array[mid] == element) {
                return mid;
            }

            if (isPresentInRightSubarray(array, low, mid, end, element)) {
                low = mid+1;
            } else {
                end = mid-1;
            }
        }
        return -1;
    }

    private static boolean isPresentInRightSubarray(int [] array, int low, int mid, int end, int element) {
        if (array[mid] < array[end] && element >= array[mid] && element <= array[end]) {
            // Right sub-array is sorted.
            return true;
        } else {
            // Left array is sorted, check if element is not present in that then it should be in right half.
            if (!(element >= array[low] && element <= array[mid])) {
                return true;
            }
        }
        return false;
    }

    public static void quickSort(int [] array) {
        _quickSort(array, 0, array.length-1);
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
        Random random = new Random(System.currentTimeMillis());
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

    public static void mergeSort(int [] array) {
        _mergeSort(array, 0, array.length-1);
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

    public static void heapSort(int [] array) {
        createHeap(array);
        _heapSort(array, array.length - 1);
    }

    private static void _heapSort(int [] array, int n) {
        for (int i = n ; i >= 0 ; i--) {
            swap(array, 0, i);
            maxHeapify(array, 0, i);
        }
    }

    private static void createHeap(int [] array) {
        for (int i = array.length/2; i >= 0; i--) {
            maxHeapify(array, i, array.length);
        }
    }

    private static void maxHeapify(int [] array, int index, int heapSize) {
        int lChildIndex = 2 * index + 1;
        int rChildIndex = 2 * index + 2;
        int smaller = index;

        if (lChildIndex < heapSize && array[lChildIndex] > array[smaller]) {
            smaller = lChildIndex;
        }

        if (rChildIndex < heapSize && array[rChildIndex] > array[smaller]) {
            smaller = rChildIndex;
        }

        if (smaller != index) {
            swap(array, smaller, index);
            maxHeapify(array, smaller, heapSize);
        }
    }

    public static int findKthSmallestElement(int [] array, int k) {
        return _findKthSmallestElement(array, 0, array.length -1, k);
    }

    private static int _findKthSmallestElement(int [] array, int left, int right, int k) {
        int absolutePartitionIndex = randomPivotSelect(array, left, right);
        if (absolutePartitionIndex == k) {
            return array[absolutePartitionIndex];
        } else if (absolutePartitionIndex < k) {
            return _findKthSmallestElement(array, absolutePartitionIndex + 1, right, k);
        } else {
            return _findKthSmallestElement(array, left, absolutePartitionIndex - 1, k);
        }
    }

    public void insertionSort(int[] array) {
        for (int i=0; i<array.length; i++) {
            int val = array[i], j = i-1;
            for (; i>=0 && val < array[j]; j--) {
                array[j+1] = array[i];
            }
            array[j+1] = val;
        }
    }

}
