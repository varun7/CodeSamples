import edu.code.samples.ds.Heap;
import edu.code.samples.sort.Sort;

import static edu.code.samples.generic.Utils.createRandomIntArray;
import static edu.code.samples.generic.Utils.printArray;

public class Main {

    public static void main(String []args) {
//        quickSortDemo();
//        mergeSortDemo();
//        kthSelectionDemo();
//        heapDemo();
        heapSortDemo();
    }

    private static void mergeSortDemo() {
        System.out.println("\n Merge sort demo");
        int arraySize = 20;
        int [] input = createRandomIntArray(arraySize);
        Sort.mergeSort(input);
        printArray(input);
    }

    private static void quickSortDemo() {
        System.out.println("\n Quick sort demo");
        int arraySize = 20;
        int [] input = createRandomIntArray(arraySize);
        Sort.quickSort(input);
        printArray(input);
    }

    private static void heapSortDemo() {
        System.out.println("\n Quick sort demo");
        int arraySize = 20;
        int [] input = createRandomIntArray(arraySize);
        Sort.heapSort(input);
        printArray(input);
    }

    private static void kthSelectionDemo() {
        System.out.println(" \n Find kth element demo");
        int size = 10;
        int [] array = createRandomIntArray(size);
        int element = Sort.findKthSmallestElement(array, 3);
        Sort.quickSort(array);
        printArray(array);
        System.out.println("3rd smallest element = " + element);
    }

    private static void heapDemo() {
        System.out.println("\n Heap demo");
        int size = 10;
        Heap.ArrayHeap  heap = new Heap.ArrayHeap(size);
        heap.insert(2);
        heap.insert(10);
        heap.insert(20);
        heap.insert(30);
        System.out.println("Minimum element = " + heap.peek());
        heap.insert(1);
        System.out.println("Minimum element = " + heap.peek());
        heap.insert(15);
        heap.insert(3);
        heap.delete();
        heap.delete();
        System.out.println("Minimum element = " + heap.peek());
    }

}