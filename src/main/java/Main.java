import edu.code.samples.ds.Graph;
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
//        bstDemo();
//        binarySearchDemo();
//        graphDemo();
//        directedGraphDemo();
        weightedGraphDemo();
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

    private static void binarySearchDemo() {
        System.out.println("\n\n Performing binary search in rotated sorted array: ");
        int array[] = {9,1,2,3,4,5,6,7,8};
        int element = 8;
        System.out.print(" element " + element + " is present at index = " + Sort.binarySearchRotatedArray(array, element));
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

    private static void graphDemo() {
        System.out.println("\n \nGraph demo");
        Graph<Integer> graph = new Graph.AdjacencyListGraph<>();
        graph.insert(1);
        graph.insert(2);
        graph.insert(3);
        graph.insert(4);
        graph.insert(5);
        graph.connect(1,2);
        graph.connect(1,3);
        graph.connect(2,4);
        graph.connect(3,4);
        graph.connect(4,5);

        System.out.print("\nAll vertices in graph: ");
        graph.vertices().stream().forEach(v -> System.out.print(v + " "));

        System.out.print("\nSuccessors of node 5 are : ");
        graph.successors(5).forEach(v -> System.out.print(v + " "));

        System.out.print("\nPredecessors of node 1 are : ");
        graph.predecessors(1).forEach(v -> System.out.print(v + " "));

        System.out.print("\nEdge weight of node between 1 and 2 : " + graph.edgeWeight(1,2));

        System.out.print("\nBFS element " + 1 + " in graph : " + graph.bfs(1, 1));

        System.out.print("\nDFS element " + 1 + " in graph : " + graph.bfs(1, 5));

        System.out.println("\n\n");
    }

    private static void directedGraphDemo() {
        Graph<Integer> directedGraph = new Graph.AdjacencyListGraph<>(true);
        directedGraph.insert(1);
        directedGraph.insert(2);
        directedGraph.insert(3);
        directedGraph.insert(4);
        directedGraph.connect(1,2);
        directedGraph.connect(1,4);
        directedGraph.connect(2,3);
        directedGraph.connect(3,1);
        directedGraph.connect(4,5);
        directedGraph.connect(5,6);
        directedGraph.connect(6,7);
        directedGraph.connect(6,8);
        directedGraph.connect(7,4);
        directedGraph.connect(8,9);

        System.out.print("\n[Directed] Successors of node 5 are : ");
        directedGraph.successors(5).forEach(v -> System.out.print(v + " "));

        System.out.print("\n[Directed] Predecessors of node 1 are : ");
        directedGraph.predecessors(1).forEach(v -> System.out.print(v + " "));

        System.out.print("\n[Directed] Topological sort of graph : ");
        directedGraph.topologicalSort();

        System.out.print("\n[Directed] Is graph cyclic: " + directedGraph.isCyclic());

        System.out.println("\n[Directed] Strongly connected commponents are:");
        directedGraph.stronglyConnectedComponents();

        System.out.println("\n\n");
    }

    private static void weightedGraphDemo() {
        Graph<Integer> weightedGraph = new Graph.AdjacencyListGraph<>(true);
        weightedGraph.insert(1);
        weightedGraph.insert(2);
        weightedGraph.insert(3);
        weightedGraph.insert(4);
        weightedGraph.connect(1,2, 1.0);
        weightedGraph.connect(1,3, 4.0);
        weightedGraph.connect(2,3, 2.0);
        weightedGraph.connect(2,4, 5.0);
        weightedGraph.connect(3,4, 4.0);

        System.out.println("\n[Weighted] Dijkstra single source shortest path:");
        int source = 1;
        weightedGraph.singleSourceShortestPathDijkstra(source).entrySet().stream().forEach(e -> {
            System.out.println("Distance from " +  source + " to " + e.getKey() + " is : " + e.getValue());
        });

        System.out.println("\n\n");
    }

}