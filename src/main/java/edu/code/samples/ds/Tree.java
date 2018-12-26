package edu.code.samples.ds;

public interface Tree {

    class Node {
        public int data;
        public Node left;
        public Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    class BinarySearchTree {

        public Node root = null;

        public boolean isEmpty() {
            return root == null;
        }

        public void insert(int data) {
            if (isEmpty()) {
                root = new Node(data);
                return;
            }

            Node currentNode = root;
            while (true) {
                if (currentNode.data >= data) {
                    // move to left of tree.
                    if (currentNode.left != null) {
                        currentNode = currentNode.left;
                    } else {
                        currentNode.left = new Node(data);
                        return;
                    }
                } else {
                    if (currentNode.right != null) {
                        currentNode = currentNode.right;
                    } else {
                        currentNode.right = new Node(data);
                        return;
                    }
                }
            }
        }

        public boolean search(Node node, int data) {
            if (node == null) {
                return false;
            }

            if (data == node.data) {
                return true;
            }
            if (data < node.data) {
                return search(node.left, data);
            } else {
                return search(node.right, data);
            }
        }

        public void delete(int data) {

        }

        public void inorderTraversal() {
            System.out.println("\n\n Inorder Traversal : ");
            _inorderTraversal(root);
        }

        private void _inorderTraversal(Node root) {
            if (root == null) {
                return;
            }

            _inorderTraversal(root.left);
            System.out.print(" " + root.data);
            _inorderTraversal(root.right);
        }

        public void preorderTraversal() {
            System.out.println("\n\n Preorder Traversal : ");
            _preorderTraversal(root);
        }

        private void _preorderTraversal(Node root) {
            if (root == null) {
                return;
            }

            System.out.print(" " + root.data);
            _preorderTraversal(root.left);
            _preorderTraversal(root.right);
        }

        public void postOrderTraversal() {
            System.out.println("\n\n Postorder Traversal : ");
            _postOrderTraversal(root);
        }

        public void _postOrderTraversal(Node root) {
            if (root == null) {
                return;
            }
            _postOrderTraversal(root.left);
            _postOrderTraversal(root.right);
            System.out.print(" " + root.data);
        }

        public void iterativePreorderTraversal(Node root) {
            Node[] stack = new Node[50];
            int top = 0;
            Node node = root;

            while (true) {
                if (node != null) {
                    System.out.print(" " + node.data);
                    stack[top++] = node;
                    node = node.left;
                } else {
                    if (top <= 0) {
                        return;
                    }
                    node = stack[--top];
                    node = node.right;
                }
            }
        }

        public void iterativeInorderTraversal(Node root) {
            Node[] stack = new Node[50];
            int top = 0;
            Node node = root;

            while (true) {
                if (node != null) {
                    stack[top++] = node;
                    node = node.left;
                } else {
                    if (top <= 0) {
                        return;
                    }
                    node = stack[--top];
                    System.out.print(" " + node.data);
                    node = node.right;
                }
            }
        }

        public void iterativePostorderTraversal(Node root) {
        }

        int pIndex = 0;
        public void constructBSTFromInAndPreOrder(int[] inorder, int[] preorder, int n) {
            pIndex = 0;
            root = _constructBSTFromInAndPreOrder(inorder, preorder, n, 0, n-1);
        }

        private Node _constructBSTFromInAndPreOrder(int [] inorder, int [] preorder, int n, int iStart, int iEnd) {

            if (iStart > iEnd || pIndex >= n) {
                return null;
            }

            Node newNode = new Node(preorder[pIndex++]);
            int iIndex = searchArray(inorder, iStart, iEnd, newNode.data);
            newNode.left = _constructBSTFromInAndPreOrder(inorder, preorder, n, iStart, iIndex-1);
            newNode.right = _constructBSTFromInAndPreOrder(inorder, preorder, n, iIndex+1, iEnd);
            return newNode;
        }

        int preorderIndex = 0;
        public void constructBSTFromPreorder(int []preorder) {
            this.root = new Node(preorder[0]);
            preorderIndex = 1;
            _constructBSTFromPreorder(root, Integer.MIN_VALUE, Integer.MAX_VALUE, preorder);
        }

        private void _constructBSTFromPreorder(Node root, int lowerBound, int upperBound, int[] preorder) {
            if (preorderIndex >= preorder.length) {
                return;
            }

            if (preorder[preorderIndex] < lowerBound || preorder[preorderIndex] > upperBound) {
                // Not a right place to insert this node.
                return;
            }

            Node newNode = new Node(preorder[preorderIndex++]);
            if (newNode.data <= root.data) {
                root.left = newNode;
            } else {
                root.right = newNode;
            }

            _constructBSTFromPreorder(newNode, lowerBound, newNode.data, preorder);
            _constructBSTFromPreorder(newNode, newNode.data, upperBound, preorder);
        }


        private static int searchArray(int[] inorder, int start, int end, int element) {
            for (int i=start; i <= end; i++) {
                if (inorder[i] == element) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Print encoding for an array.
         * Rules: consider BST made from given array. Let say number x is present in the BST and to reach x
         * if you go right print 1, if left then 0. Now you are given an index i in the array A (so x= A[i])
         * and print the encoding without constructing BST to reach x and without space with leas time complexity.
         */
        public void bstEncoding(int[] array, int n , int element) {
            int lowerBound = Integer.MIN_VALUE;
            int upperBound = Integer.MAX_VALUE;
            String encoding = "";

            for (int i=0; i<n; i++) {
                if (element == array[i]) {
                    System.out.print("\n\nBST encoding for element " + element + " is " + encoding);
                    return;
                }

                if (array[i] > element && array[i] <= upperBound) {
                    upperBound = array[i];
                    encoding += "0";
                }

                if (array[i] < element && array[i] >= lowerBound) {
                    lowerBound = array[i];
                    encoding += "1";
                }
            }
        }

        int maxPathLength;
        public int lengthOfLongestLeafToLeafPath() {
            maxPathLength = 0;
            longestPath(root);
            System.out.println("\n\nMaximum leaf to leaf path length = "  + maxPathLength);
            return maxPathLength;
        }

        private int longestPath(Node root) {
            if (root == null) {
                return 0;
            }
            int leftHeight = longestPath(root.left);
            int rightHeight = longestPath(root.right);
            int candidateMax = leftHeight + rightHeight + 1;
            if (candidateMax > maxPathLength) {
                maxPathLength = candidateMax;
            }
            return leftHeight > rightHeight ? leftHeight + 1 : rightHeight + 1;
        }

        public void rootToLeafPath(int sum) {
            System.out.println("\n\n Is rhere a path from root to leaf with sum " + sum + " " + rootToLeafPath(root,sum) );
        }

        public void lowestCommonAncestor(int data1, int data2) {
            Node node = _lowestCommonAncestor(root, data1, data2);
            if (node == null) {
                System.out.println("\n\n Lowest common ancestor: either one or both elements are missing in tree");
            }
            System.out.println("\n\n Lowest common ancestor of " + data1 + " and " + data2 + " is " + node.data);
        }

        private Node _lowestCommonAncestor(Node root, int data1, int data2) {
            if (root == null) {
                return null;
            }

            if (root.data == data1 || root.data == data2) {
                return root;
            }

            Node left = _lowestCommonAncestor(root.left, data1, data2);
            Node right = _lowestCommonAncestor(root.right, data1, data2);

            if (left != null && right != null) {
                return root;
            }

            return left != null? left : right;
        }

        private boolean rootToLeafPath(Node root, int sum) {
            if (root == null) {
                return false;
            }

            if (root.left == null && root.right == null && sum - root.data == 0) {
                return true;
            }

            if (rootToLeafPath(root.left, sum - root.data)) {
                return true;
            }

            if (rootToLeafPath(root.right, sum-root.data)) {
                return true;
            }
            return false;
        }
    }
}
