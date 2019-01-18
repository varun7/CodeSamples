package edu.code.samples.ds;

public interface SegmentTree {
    int query(int start, int end);

    void update(int index, int value);

    class SumSegTree implements SegmentTree {
        private int[] tree;
        private int[] input;

        public SumSegTree(int[] input) {
            this.input = input;
            constructTree();
            printTree();
        }

        private void constructTree() {
            tree = new int[elementsInCompleteBinaryTree(input.length)];
            _constructTree(0, input.length - 1, 0);
        }

        private void _constructTree(int start, int end, int index) {
            if (start == end) {
                // leaf node.
                tree[index] = input[end];
                return;
            }

            int mid = (start + end) / 2, lChild = 2 * index+1, rChild = 2*index+2;

            _constructTree(start, mid, lChild);
            _constructTree(mid + 1, end, rChild);
            tree[index] = tree[lChild] + tree[rChild];
        }

        private int elementsInCompleteBinaryTree(int n) {
            int height = (int) Math.ceil(Math.log(n)/Math.log(2));
            return 1 << (height+1);
        }

        @Override
        public int query(int rStart, int rEnd) {
            return _query(rStart, rEnd, 0, input.length-1, 0);
        }

        private int _query(int rStart, int rEnd, int s, int e, int index) {
            if (rStart == s && rEnd ==e) {
                return tree[index];
            }
            int mid = (s+e)/2, lChild = 2*index+1, rChild = 2*index+2;

            // completely in left
            if (rStart >= s && rEnd <= mid) {
                return _query(rStart, rEnd, s, mid, lChild);
            }

            // Completely in right
            if (rStart >= mid+1 && rEnd <= e) {
                return _query(rStart, rEnd, mid+1, e, rChild);
            }

            // Partially in left and partially in right.
            return _query(rStart, mid, s, mid, lChild) + _query(mid+1, rEnd, mid+1, e, rChild);
        }

        @Override
        public void update(int index, int value) {
            _update(index, value, 0, input.length-1, 0);
        }

        private void _update(int index, int value, int start, int end, int currentIndex) {
            if (start == end) {
                tree[currentIndex] = value;
                input[index] = value;
                return;
            }

            int mid = (start+end)/2, lChild = 2*currentIndex+1, rchild = 2*currentIndex+2;
            if (index >= start && index <= mid) {
                _update(index, value, start, mid, lChild);
            } else {
                _update(index, value, mid+1, end, rchild);
            }
            tree[currentIndex] = tree[lChild] + tree[rchild];
        }

        private void printTree() {
            System.out.println("\nPrinting tree");
            for(int i: tree) {
                System.out.print(i + "  ");
            }
        }
    }
}
