package edu.code.samples.ds;

public class BinaryIndexedTree {

    private int[] input;
    private int[] tree;

    public BinaryIndexedTree(int []input) {
        this.input = input;
        constructTree();
        printTree();
    }

    public BinaryIndexedTree(int n) {
        tree = new int[n];
        setAllZeros(tree);
    }

    public void update(int index, int val) {
        for (int pos = index+1; pos < tree.length; pos += pos & (-pos)) {
            tree[pos] += val;
        }
    }

    public int query(int index) {
        int val = 0;
        for (int i=index; i>0; i -= (i & -i)) {
            val += tree[i];
        }
        return val;
    }

    private void constructTree() {
        this.tree = new int[input.length+1];
        setAllZeros(tree);
        for (int i=0; i<input.length; i++) {
            update(i, input[i]);
        }
    }

    private void setAllZeros(int[] array) {
        for (int i=0; i < array.length; i++) {
            array[i] = 0;
        }
    }

    private void printTree() {
        System.out.println("\nPrinting Fenwick tree:");
        for (int i=0; i<tree.length; i++) {
            System.out.print(tree[i] + " ");
        }
    }
}
