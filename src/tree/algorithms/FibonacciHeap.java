package tree.algorithms;

import tree.node.FibonacciHeapNode;

public class FibonacciHeap {

    public FibonacciHeapNode root;

    /**
     * Core Functions
     */

    /**
     * Insert new node
     */
    public void insert(String value) {
        FibonacciHeapNode newNode = new FibonacciHeapNode(value);
        if (this.root == null) {
            this.root = newNode;
        } else {
            newNode.next = this.root;
            merge(newNode);
        }
        System.out.println(this.root);
    }

    private void merge(FibonacciHeapNode root) {
        if (root.next != null) {

            FibonacciHeapNode next = root.next;
            LeftistTree nextLeftistTree = next.tree;
            LeftistTree leftistTree = root.tree;

            if (leftistTree.height() == nextLeftistTree.height()) {
                leftistTree.merge(leftistTree.top(), nextLeftistTree.top());
                root.next = root.next.next;
                this.root = root;
                merge(root);
            } else {
                this.root = root;
            }
        }
    }

}
