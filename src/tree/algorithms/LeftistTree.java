package tree.algorithms;

import tree.node.LeftistTreeNode;
import util.LeftTreePrinter;

import java.util.ArrayList;

/**
 * Rules
 * 1. npl(left) >= npl(right)
 * 2. prio(root) >= prio(root.left) >= prio(root.right)
 * Leftist tree is not a search structure, then it allows duplicate values
 */
public class LeftistTree {

    public LeftistTreeNode root;

    /**
     * Core Functions
     */
    public void insert(String value) {
        LeftistTreeNode n = new LeftistTreeNode(value);
        insertLeftTree(this.root, n);
    }

    /**
     * Insert new node
     */
    private void insertLeftTree(LeftistTreeNode currentNode, LeftistTreeNode newNode) {

        if (currentNode == null) {
            this.root = newNode;
            this.root.parent = null;
        } else {
            merge(currentNode, newNode);
        }
    }

    /**
     * Lazy merge: joins two left trees
     */
    public void merge(LeftistTreeNode root, LeftistTreeNode newNode) {
        // Considering the element with small priority/value on the top

        if (root.parent == null && root.value.compareTo(newNode.value) > 0) {

            newNode.right = root;
            root.parent = newNode;
            newNode.parent = null;
            swap(newNode);
            this.root = newNode;

        } else if (root.right == null) {
            root.right = newNode;
            newNode.parent = root;

            performSwap(this.root);

        } else if (root.right.value.compareTo(newNode.value) > 0) {
            LeftistTreeNode detached = root.right;
            root.right = newNode;
            newNode.parent = root;
            merge(newNode, detached);
        } else {
            merge(root.right, newNode);
        }
    }

    /**
     * Null Path Length: The shortest distance bettween the current node to the closest null node
     */
    public int npl(LeftistTreeNode root, int npl) {
        if (root.left != null && root.right != null) {
            int left = npl(root.left, npl);
            int right = npl(root.right, npl);
            npl = Math.min(left, right);
        }

        return npl + 1;
    }

    private void performSwap(LeftistTreeNode root) {
        if (root != null) {
            if (root.left == null) { // npl == -1
                swap(root);
            } else {

                LeftistTreeNode right, left, toBeSwapped;

                if (root.parent != null) {
                    toBeSwapped = root.parent;
                    right = toBeSwapped.right;
                    left = toBeSwapped.left;
                } else {
                    toBeSwapped = root;
                    right = root.right;
                    left = root.left;
                }

                String rightValue = right == null ? "" : right.value;
                String leftValue = left == null ? "" : left.value;

                int rightNpl = right == null ? -1 : npl(right, -1);
                int leftNpl = left == null ? -1 : npl(left, -1);

                if (rightNpl > leftNpl || (rightNpl == leftNpl && leftValue.compareTo(rightValue) > 0)) {
                    swap(toBeSwapped);
                }

                performSwap(root.left);
                performSwap(root.right);
            }
        }
    }

    private void swap(LeftistTreeNode left) {
        LeftistTreeNode swap = left.left;
        left.left = left.right;
        left.right = swap;
    }

    /**
     * Finds a node
     */
    public LeftistTreeNode find(String value) {
        return find(this.root, value);
    }

    /**
     * Finds a node
     */
    public LeftistTreeNode find(LeftistTreeNode currentNode, String value) {

        LeftistTreeNode result = null;

        if (currentNode != null) {
            if (currentNode.value.equals(value)) {
                result = currentNode;
            } else {
                result = find(currentNode.right, value);

                if (result == null)
                    result = find(currentNode.left, value);
            }
        } else {
            result = null;
        }

        return result;

    }

    /**
     * Removes a node from the tree
     *
     * @hash is just to print index in table hash
     */
    public void remove() {
        removeLeftTree(this.root);
    }

    /**
     * Removes the node from the top..
     */
    private void removeLeftTree(LeftistTreeNode startingNode) {
        if (this.root != null) {
            LeftistTreeNode toBeRemoved = this.root;
            LeftistTreeNode newRoot;
            if (this.root.right == null) {
                if (this.root.left == null) {
                    this.root = null;
                } else {
                    this.root = this.root.left;
                    this.root.parent = null;
                }
                System.out.println("\nElement removed successfully.");
            } else {
                if (toBeRemoved.left.value.compareTo(toBeRemoved.right.value) < 0) {
                    newRoot = toBeRemoved.left;
                    newRoot.parent = null;
                    merge(newRoot, toBeRemoved.right);
                    this.root = newRoot;
                } else {
                    newRoot = toBeRemoved.right;
                    newRoot.parent = null;
                    merge(newRoot, toBeRemoved.left);
                    this.root = newRoot;
                }
                performSwap(this.root.right);
                System.out.println("\nElement removed successfully.");
            }
        }

    }

    /**
     * Helper Functions
     */

    public LeftistTreeNode top() {
        return this.root;
    }

    public int height() {
        return height(this.root);
    }

    /**
     * Calculating the height of a node.
     */
    private int height(LeftistTreeNode currentNode) {
        if (currentNode == null) {
            return -1;
        }
        if (currentNode.left == null && currentNode.right == null) {
            return 1;
        } else if (currentNode.left == null) {
            return 1 + height(currentNode.right);
        } else if (currentNode.right == null) {
            return 1 + height(currentNode.left);
        } else {
            return 1 + maximum(height(currentNode.left), height(currentNode.right));
        }
    }

    /**
     * Calculating the height of a node according to the root.
     */
    public int heightRootToNode(LeftistTreeNode currentNode) {
        int height = 0;

        while (currentNode.parent != null) {
            height++;
            currentNode = currentNode.parent;
        }

        return height;
    }

    /**
     * Return the maximum of two integers.
     */
    private int maximum(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * Calculates the Inorder traversal of this tree.
     */
    public ArrayList<LeftistTreeNode> inOrder() {
        ArrayList<LeftistTreeNode> ret = new ArrayList<LeftistTreeNode>();
        inOrder(root, ret);
        return ret;
    }

    /**
     * Function to calculate in order recursively.
     */
    private void inOrder(LeftistTreeNode node, ArrayList<LeftistTreeNode> io) {
        if (node == null) {
            return;
        }
        inOrder(node.left, io);
        io.add(node);
        inOrder(node.right, io);
    }

    public void printAll() {
        LeftTreePrinter.printNode(this.root);
    }

}
