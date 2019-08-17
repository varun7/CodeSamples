package edu.code.samples.ds;

public interface BinaryIndexedTree {
    void update(int position, int value);

    int query(int positions);

    class RangeSumQueryBIT implements BinaryIndexedTree {
        int[] input;
        int[] tree;

        public RangeSumQueryBIT(int[] input) {
            this.input = input;
            tree = new int[input.length+1];
            constructTree();
            printTree();
        }

        private void constructTree() {
            this.tree = new int[input.length + 1];
            for (int i = 0; i < input.length; i++) {
                update(i+1, input[i]);
            }
        }

        private void printTree() {
            System.out.println("\nPrinting Fenwick tree:");
            for (int i = 0; i < tree.length; i++) {
                System.out.print(tree[i] + " ");
            }
        }

        @Override
        public int query(int position) {
            int sum = 0;
            for (int i=position; i> 0; i -= (i&-i)) {
                sum += tree[i];
            }
            return sum;
        }

        @Override
        public void update(int position, int value) {
            for (int i=position; i < tree.length; i += (i&-i)) {
                tree[i] += value;
            }
        }
    }
}

