import edu.code.samples.bits.BitsQuestions;
import edu.code.samples.dp.Knapsack;
import edu.code.samples.dp.MatrixPath;
import edu.code.samples.dp.Sequences;
import edu.code.samples.dp.UnclassifiedDP;
import edu.code.samples.ds.BinaryIndexedTree;
import edu.code.samples.ds.BinaryIndexedTree2D;
import edu.code.samples.ds.Graph;
import edu.code.samples.ds.Heap;
import edu.code.samples.ds.IntervalSearchTree;
import edu.code.samples.ds.SegmentTree;
import edu.code.samples.ds.Tree;
import edu.code.samples.ds.UnionFind;
import edu.code.samples.generic.InversionPair;
import edu.code.samples.generic.KMP;
import edu.code.samples.sort.Sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
//        weightedGraphDemo();
//        unionFindDemo();
//        dpDemo();
//        kmpDemo();
//        bitsDemo();
//        segmentTreeDemo();
//        binaryIndexedTreeDemo();
//        intervalSearchTreeDemo();
//        demoInversionPair();
//        test();
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
        bst.preorderTraversal();

        System.out.println("\n \n Iterative preorder");
        bst.iterativePreorderTraversal(bst.root);

        System.out.println("\n \n Recursive inorder");
        bst.inorderTraversal();

        System.out.println("\n \n Iterative inorder");
        bst.iterativeInorderTraversal(bst.root);


        System.out.println("\n \n Recursive postOrder");
        bst.postOrderTraversal();

        System.out.println("\n \n Iterative postOrder");
        bst.iterativePostorderTraversal(bst.root);


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
        bst.bstEncoding(array, array.length, 60);

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
        graph.connect(1,2, 1.0d);
        graph.connect(1,3, 7.0d);
        graph.connect(2,3, 5.0d);
        graph.connect(2,4, 4.0d);
        graph.connect(2,5, 3.0d);
        graph.connect(3,5, 6.0d);
        graph.connect(4,5, 2.0d);


        System.out.print("\nAll vertices in graph: ");
        graph.vertices().stream().forEach(v -> System.out.print(v + " "));

        System.out.print("\nSuccessors of node 5 are : ");
        graph.successors(5).forEach(v -> System.out.print(v + " "));

        System.out.print("\nPredecessors of node 1 are : ");
        graph.predecessors(1).forEach(v -> System.out.print(v + " "));

        System.out.print("\nEdge weight of node between 1 and 2 : " + graph.edgeWeight(1,2));

        System.out.print("\nBFS element " + 1 + " in graph : " + graph.bfs(1, 1));

        System.out.print("\nDFS element " + 1 + " in graph : " + graph.bfs(1, 5));

        System.out.println("\n[Weighted] Minimum spanning tree, prims");
        graph.mstPrims();

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

    private static void unionFindDemo() {
        UnionFind<Integer> rankedUnion = new UnionFind.RankedUnion<>(Arrays.asList(1,2,3,4,5,6,7));
        System.out.println("Leader of 1 = " + rankedUnion.find(1).leader + " rank = " + rankedUnion.find(1).rank);
        System.out.println("Leader of 2 = " + rankedUnion.find(2).leader + " rank = " + rankedUnion.find(2).rank);

        rankedUnion.union(1,2);
        System.out.println("\n\nMerging 1 and 2");
        System.out.println("Leader of 1 = " + rankedUnion.find(1).leader + " rank = " + rankedUnion.find(1).rank);
        System.out.println("Leader of 2 = " + rankedUnion.find(2).leader + " rank = " + rankedUnion.find(2).rank);

        rankedUnion.union(2,3);
        System.out.println("\n\nMerging 2 and 3");
        System.out.println("Leader of 2 = " + rankedUnion.find(2).leader + " rank = " + rankedUnion.find(2).rank);
        System.out.println("Leader of 3 = " + rankedUnion.find(3).leader + " rank = " + rankedUnion.find(3).rank);

        rankedUnion.union(4,5);
        System.out.println("\n\nMerging 4 and 5");
        System.out.println("Leader of 4 = " + rankedUnion.find(4).leader + " rank = " + rankedUnion.find(4).rank);
        System.out.println("Leader of 5 = " + rankedUnion.find(5).leader + " rank = " + rankedUnion.find(5).rank);

        rankedUnion.union(2,5);
        System.out.println("\n\nMerging 4 and 5");
        System.out.println("Leader of 2 = " + rankedUnion.find(2).leader + " rank = " + rankedUnion.find(2).rank);
        System.out.println("Leader of 5 = " + rankedUnion.find(5).leader + " rank = " + rankedUnion.find(5).rank);

        System.out.println("\n\nMerging 1 and 5");
        rankedUnion.union(1,5);
    }

    private static void dpDemo() {
        int [] weights = {10, 20, 30};
        int [] values = {60, 100, 120};
        int W = 50;
        System.out.println("\nKnapsack problem bottom-up solution = " + Knapsack.tabulatedMaximumValue(values, weights, W));
        System.out.println("\nKnapsack problem top-down solution = " + Knapsack.memoizedMaximumValue(values, weights, W));

        int cost[][] = {{10,5,15}, {20,40,10}, {30,10,2}};
        int endx = 1;
        int endy = 2;
        System.out.println("\n\nMinimum cost path from (0,0) to (" + endx + " ," + endy + ") is " + MatrixPath.cheapestPath(endx, endy, cost));
        System.out.println("\n\nWays to reach (" + endx + " ," + endy + ") from (0,0) is " + MatrixPath.numberOfPaths(endx, endy));

        int matrix[][] = {{1,1,1}, {1,0,1},{1,0,0}};
        System.out.println("\n\nWays to reach (" + endx + " ," + endy + ") from (0,0) is " + MatrixPath.numberOfPathsWithObstacles(endx, endy, matrix));

        String s1 = "Sunday", s2 = "Saturday";
        System.out.println("\n\nMinimum lenenshtein distance between " + s1 + " and " + s2 + " is : " + Sequences.levenshteinDistances(s1,s2));

        String s = "abcda";
        System.out.println("\n\nMinimum insertion for converting " + s + " to palindrome is : " + Sequences.minimumInsertionForPalindrom(s));
        System.out.println("\n\nMinimum insertion for converting " + s + " to palindrome is : " + Sequences.tabulatedLongestPalindromicSubsequence(s));

        System.out.println("\n\nLength of maximum chain is : " + UnclassifiedDP.maxChainLength());

        System.out.println("\n\nMaximum sum of non-consecutive elements in array is : " + UnclassifiedDP.maximumNonConsecutiveSum());

        System.out.println("\n\nLength longest increasing subsequence : " + Sequences.longestIncreasingSubsequence());

        System.out.println("\n\nLength of longest common subsequence : " + Sequences.longestCommonSubsequence("BANANA", "ATANA"));

        String a = "BANANA";
        String b = "ATANA";
        Sequences.printLongestCommonSubsequence(a,b);
        System.out.print(" is the Longest common subsequence between " + a + " and " + b + "\n\n");

        String lps = "BANANA";
        Sequences.printLongestPalindromicSequence(lps);
        System.out.print(" is the longest palindromic subsequence in " + lps);

        int [] array = {1,3,5,8,9,2,6,7,6,8,9};
        System.out.print("\n\nMinimum jumps to reach array end is: " + UnclassifiedDP.minimumJumps(array));

        int [][] squareMatrixInput = {{0,1,1,0,1}, {1,1,0,1,0}, {0,1,1,1,0}, {1,1,1,1,0}, {1,1,1,1,1}, {0,0,0,0,0}};
        System.out.print("\n\nSize of maximum square sub matrix is: " + UnclassifiedDP.maximumSquareMatrix(squareMatrixInput));

        System.out.println("\n\n");
    }

    private static void kmpDemo() {
        String text = "varun sharma", pattern = "arn";
        System.out.println("\n\n" + text + " contains " + pattern + " = " + KMP.contains(text, pattern));
    }

    private static void bitsDemo() {
        int n1 = -2, n2 = 15;
        System.out.println("\n\n" + n1 + " + " + n2 + " = " + BitsQuestions.add(n1, n2));
        System.out.println("Max of " + n1 + " , " + n2 + " is : " + BitsQuestions.max(n1, n2));
    }

    private static void segmentTreeDemo() {
        int [] array = {1,3,5,8,9,2};
        SegmentTree tree = new SegmentTree.SumSegTree(array);
        int i = 1, j = 5;
        System.out.println("\n\nSum of interval " + i + " to " + j + " = " + tree.query(i,j));
        tree.update(3,10);
        System.out.println("\n\nSum of interval " + i + " to " + j + " = " + tree.query(i,j));

        int [] input = {1,3,5,8,9,2};
        int l = 0, r = 5;
        SegmentTree rangeSegTree = new SegmentTree.MaxSegTree(array);
        System.out.println("\n\nMax in range" + l + " to " + r + " = " + rangeSegTree.query(l,r));
        rangeSegTree.update(1,20);
        System.out.println("\n\nMax in range" + l + " to " + r + " = " + rangeSegTree.query(l,r));
    }

    private static void binaryIndexedTreeDemo() {
        int [] array = {1,0,2,1,1,3,0,4,2,5,2,2,3,1,0,2};
        BinaryIndexedTree tree = new BinaryIndexedTree(array);
        int i = 5;
        System.out.println("\n\nSum of first " + i + " elements = " + tree.query(i));

        int [][] matrix = {{1, 2, 3, 4},
                           {5, 6, 7, 8},
                           {9, 10, 11, 12},
                           {13, 14, 15, 16}};
        BinaryIndexedTree2D twoDTree = new BinaryIndexedTree2D(matrix);
        int x = 1, y = 2;
        // Treat as array starts from 1,1
        System.out.println("\n\nSum of matrix (1,1) to (" +  x + " , " + y + ") = " + twoDTree.query(x, y));
    }

    private static void intervalSearchTreeDemo() {
        IntervalSearchTree tree = new IntervalSearchTree();
        tree.put(2,10, 4);
        tree.put(3,5, 5);
        tree.put(0,2, 4);
        tree.put(7,12, 4);

        int p = 5, q=7;
        List<IntervalSearchTree.IntervalNode> nodes = tree.overlappingIntervals(p,q);

        System.out.println("\n\nPrinting intervals overlapping with (" + p + " , " + q + ") is/are: ");
        printIntervals(nodes);
    }

    private static void printIntervals(List<IntervalSearchTree.IntervalNode> nodes) {
        nodes.forEach(n -> {
            System.out.print(" (" + n.low + " , " + n.high + ")  ");
        });
    }

    private static void demoInversionPair() {
        InversionPair pairs = new InversionPair();
        int[] input = {50,20,10,10};
        System.out.println("Number of inversion pairs = " + pairs.countInversions(input));
    }

    class Pair {
        char ch;
        int count;
    }
    private static void test() {
        String s;
        Comparator<Pair> comparator = (a,b) -> {
            return a.count - b.count;
        };
    }
}