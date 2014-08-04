package tree.node;

import tree.algorithms.LeftistTree;

public class FibonacciHeapNode {

    public FibonacciHeapNode next;
    public LeftistTree tree;

    public FibonacciHeapNode(String value) {
        this.next = null;
        tree = new LeftistTree();
        tree.insert(value);
    }
}
