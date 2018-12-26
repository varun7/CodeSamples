import edu.code.samples.ds.Heap;
import edu.code.samples.ds.Tree;
import edu.code.samples.sort.Sort;

import static edu.code.samples.generic.Utils.createRandomIntArray;
import static edu.code.samples.generic.Utils.printArray;

public class Main {

    public static void main(String []args) {
//        quickSortDemo();
//        mergeSortDemo();
//        kthSelectionDemo();
//        heapDemo();
//        heapSortDemo();
        bstDemo();
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

    private static void bstDemo() {
        Tree.BinarySearchTree bst = new Tree.BinarySearchTree();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(10);
        bst.insert(40);
        bst.insert(45);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);

        bst.rootToLeafPath(10);

        bst.lowestCommonAncestor(30, 40);

        System.out.println("\n \n Recursive preorder");
        bst.inorderTraversal();

        System.out.println("\n \n Iterative preorder");
        bst.iterativeInorderTraversal(bst.root);

        bst.lengthOfLongestLeafToLeafPath();

        System.out.println("\n \n Is 180 present " + bst.search(bst.root, 180));
        System.out.println("\n \n Is 30 present" + bst.search(bst.root, 30));

        int [] inorder = {20,30,35,40,45,50,60,70,80};
        int [] preorder = {50,30,20,40,35,45,70,60,80};
        System.out.println("\n\n Constructed binary tree from traversals: ");
        Tree.BinarySearchTree tree = new Tree.BinarySearchTree();
        tree.constructBSTFromInAndPreOrder(inorder, preorder, inorder.length);
        tree.inorderTraversal();

        int array[] = {50,30,20,40,35,45,70,60,80};
        bst.bstEncoding(array, array.length, 35);

        int [] preorderTraversal = {50,30,20,40,35,45,70,60,80};
        Tree.BinarySearchTree fromPreOrderTree = new Tree.BinarySearchTree();
        fromPreOrderTree.constructBSTFromPreorder(preorderTraversal);
        fromPreOrderTree.inorderTraversal();
        fromPreOrderTree.postOrderTraversal();
    }

    private static void printList(Tree.Node head) {
        System.out.println("Printing linked list");
        while (head != null) {
            System.out.print(" " + head.data);
        }
    }

}