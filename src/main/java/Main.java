import edu.code.samples.sort.Sort;

import static edu.code.samples.generic.Utils.createRandomIntArray;
import static edu.code.samples.generic.Utils.printArray;

public class Main {

    public static void main(String []args) {
        quickSortDemo();
        mergeSortDemo();
    }

    private static void mergeSortDemo() {
        System.out.println("\n Merge sort demo");
        int arraySize = 20;
        int [] input = createRandomIntArray(arraySize);
        Sort.mergeSort(input, arraySize);
        printArray(input);
    }

    private static void quickSortDemo() {
        System.out.println("\n Quick sort demo");
        int arraySize = 20;
        int [] input = createRandomIntArray(arraySize);
        Sort.quickSort(input, arraySize);
        printArray(input);
    }

}