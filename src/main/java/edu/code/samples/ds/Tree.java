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

            return false;
        }

        public void delete(int data) {

        }

        public void inorderTraversal(Node root) {
            if (root == null) {
                return;
            }

            inorderTraversal(root.left);
            System.out.print(" " + root.data);
            inorderTraversal(root.right);
        }

        public void preOrderTraversal(Node root) {
            if (root == null) {
                return;
            }

            System.out.print(" " + root.data);
            preOrderTraversal(root.left);
            preOrderTraversal(root.right);
        }

        public void postOrderTraversal(Node root) {
            if (root == null) {
                return;
            }

            preOrderTraversal(root.left);
            preOrderTraversal(root.right);
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
    }
}
