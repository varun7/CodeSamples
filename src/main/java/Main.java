import edu.code.samples.sort.Sort;

import static edu.code.samples.generic.Utils.createRandomIntArray;
import static edu.code.samples.generic.Utils.printArray;

public class Main {

    public static void main(String []args) {
//        quickSortDemo();
//        mergeSortDemo();
        kthSelectionDemo();
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

    private static void kthSelectionDemo() {
        System.out.println(" \n Find kth element demo");
        int size = 10;
        int [] array = createRandomIntArray(size);
        int element = Sort.findKthSmallestElement(array, 3);
        Sort.quickSort(array);
        printArray(array);
        System.out.println("3rd smallest element = " + element);
    }

}