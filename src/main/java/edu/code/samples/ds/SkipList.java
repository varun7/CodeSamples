package edu.code.samples.ds;

public class SkipList {

    class SkipListNode {
        SkipListNode[] next;
        int levels;
        int data;

        public SkipListNode(int data, int levels) {
            this.levels = levels;
            this.data = data;
            this.next = new SkipListNode[levels];
        }

        public SkipListNode(int data) {
            this.data = data;
            this.levels = getRandomLevel();
            this.next = new SkipListNode[levels];
        }

        private int getRandomLevel() {
            int l = 1;
            while (l < MAX_LEVEL && Math.random() < 0.5) {
                l++;
            }
            return l;
        }
    }

    private SkipListNode head;
    private static final int MAX_LEVEL = 4;

    public boolean contains(int item) {
        SkipListNode current = head, next;
        for (int i=MAX_LEVEL-1; i >=0; i--) {
            next = current.next[i];
            while(next.data < item) {
                current = next;
                next = next.next[i];
            }
        }
        current = current.next[0];
        if (current.data == item) {
            return true;
        }
        return false;
    }

    public void add(int item) {
        if (head == null) {
            createAndAddFirstItem(item);
            return;
        }
        // TODO: Implement this.
    }

    public void delete(int item) {
        // TODO: Implement this.
    }

    private void createAndAddFirstItem(int item) {
        // create head node.
        head = new SkipListNode(item, MAX_LEVEL);
        SkipListNode sentinel = new SkipListNode(Integer.MAX_VALUE, MAX_LEVEL);
        for (int i=0; i < MAX_LEVEL; i++) {
            head.next[i] = sentinel;
        }
    }

}
