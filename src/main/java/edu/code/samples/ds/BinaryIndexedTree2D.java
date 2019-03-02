package edu.code.samples.ds;

public class BinaryIndexedTree2D {

    private int[][] input;
    private int[][] tree;
    private int rows;
    private int cols;

    public BinaryIndexedTree2D(int [][]input) {
        this.input = input;
        rows = input.length + 1;
        cols = input[0].length + 1;
        this.tree = new int[rows][cols];
        constructTree();
        printTree();
    }

    public void update(int xIndex, int yIndex, int val) {
        for (int x=xIndex +1; x < rows; x += (x & -x)) {
            for (int y = yIndex + 1; y < cols; y += (y & -y)) {
                tree[x][y] += val;
            }
        }
    }

    public int query(int xIndex, int yIndex) {
        int sum = 0;
        for (int x = xIndex; x > 0 ; x -= (x & -x)) {
            for (int y = yIndex; y > 0; y -= (y & -y)) {
                sum += tree[x][y];
            }
        }
        return sum;
    }

    private void constructTree() {
        // Initialize all elements to zero.
        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                tree[i][j] = 0;
            }
        }

        // Iterate each element and add it to tree.
        for (int i=0; i < input.length; i++) {
            for (int j=0; j<input[0].length; j++) {
                update(i,j,input[i][j]);
            }
        }
    }

    private void printTree() {
        for (int i = 0; i < rows; i ++) {
            System.out.println();
            for (int j = 0; j < cols; j++) {
                System.out.print(tree[i][j] + " ");
            }
        }
    }

}