package tree.algorithms;

import tree.node.FibonacciHeapNode;
import tree.node.LeftistTreeNode;

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
    }

    public void find(String s) {
        int i = 0;
        FibonacciHeapNode root = this.root;
        LeftistTreeNode element;
        if (root != null) {
            do {
                element = root.tree.find(s);
                if (element != null) {
                    System.out.println("Element with value " + s + " found in node " + i);
                    break;
                }
                i++;
                root = root.next;
            } while (root != null);
        }

    }

    /**
     * Removes the hight priority element. It runs through the list.
     * */
    public void remove() {
        if (this.root != null) {

            FibonacciHeapNode beforeHighestPriority = this.root;
            FibonacciHeapNode root = this.root.next;

            if (root == null) {
                this.root.tree.remove();
            } else {
                do {
                    if (beforeHighestPriority.tree.top().value.compareTo(root.tree.top().value) < 0) {
                        beforeHighestPriority = root;
                    }

                    root = root.next;
                } while (root != null);

                beforeHighestPriority.tree.remove();
                if (beforeHighestPriority.tree.top() == null) {
                    beforeHighestPriority = beforeHighestPriority.next;
                }
            }

            if (this.root.tree.top() == null && this.root.next != null) {
                this.root = this.root.next;
            }

            merge(this.root);
        }
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
