package tree;

import java.util.ArrayList;

import tree.node.TreeNode;
import util.AVLTreePrinter;

@SuppressWarnings("unused")
public class AVLTree {

	protected TreeNode root;
	public int currentSearchHeight = -1;

	/** Core Functions */
	public void insert(int key, int value) {
		TreeNode n = new TreeNode(key, value);
		insertAVL(this.root, n);
	}

	/** Insert new node */
	private void insertAVL(TreeNode currentNode, TreeNode newNode) {

		if (currentNode == null) {
			this.root = newNode;
		} else {

			if (newNode.key < currentNode.key) {
				if (currentNode.left == null) {
					currentNode.left = newNode;
					newNode.parent = currentNode;

					recursiveBalance(currentNode);
				} else {
					insertAVL(currentNode.left, newNode);
				}

			} else if (newNode.key > currentNode.key) {
				if (currentNode.right == null) {
					currentNode.right = newNode;
					newNode.parent = currentNode;

					recursiveBalance(currentNode);
				} else {
					insertAVL(currentNode.right, newNode);
				}
			} else {
				System.out.println("\nKey " + newNode.key + " already exists. Ignoring...");
			}
		}
	}

	/** Finds a node */
	public TreeNode find(int key) {
		currentSearchHeight = -1;
		return find(this.root, key);
	}

	/** Finds a node */
	public TreeNode find(TreeNode currentNode, int key) {
		currentSearchHeight++;

		if (key == currentNode.key)
			return currentNode;
		else if (currentNode.left != null || currentNode.right != null) {
			if (currentNode.left != null) {
				if (key == currentNode.key)
					return currentNode;
				else if (key < currentNode.key)
					return find(currentNode.left, key);
				else if (currentNode.right != null)
					return find(currentNode.right, key);
				else
					return null;
			}
			if (currentNode.right != null) {
				if (key == currentNode.key)
					return currentNode;
				else if (key > currentNode.key)
					return find(currentNode.right, key);
				else if (currentNode.left != null)
					return find(currentNode.left, key);
				else
					return null;
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 * Check the balance for each node recursively and call required methods for
	 * balancing the tree until the root is reached.
	 */
	protected void recursiveBalance(TreeNode currentNode) {

		setBalance(currentNode);
		int balance = currentNode.balance;

		if (balance == -2) {
			if (height(currentNode.left.left) >= height(currentNode.left.right)) {
				currentNode = rotateRight(currentNode);
			} else {
				currentNode = doubleRotateLeftRight(currentNode);
			}
		} else if (balance == 2) {
			if (height(currentNode.right.right) >= height(currentNode.right.left)) {
				currentNode = rotateLeft(currentNode);
			} else {
				currentNode = doubleRotateRightLeft(currentNode);
			}
		}

		if (currentNode.parent != null) {
			recursiveBalance(currentNode.parent);
		} else {
			this.root = currentNode;
		}
	}

	/**
	 * Removes a node from the tree
	 * 
	 * @hash is just to print index in table hash
	 */
	public void remove(int k, int hash) {
		currentSearchHeight = -1;
		removeAVL(this.root, k, hash);
	}

	/**
	 * Finds a node and calls a method to remove the node..
	 */
	private void removeAVL(TreeNode startingNode, int searchingKey, int hash) {
		currentSearchHeight++;
		if (startingNode == null) {
			System.out.println("\nKey " + searchingKey + " not found.");
			return;
		} else {
			if (startingNode.key > searchingKey) {
				removeAVL(startingNode.left, searchingKey, hash);
			} else if (startingNode.key < searchingKey) {
				removeAVL(startingNode.right, searchingKey, hash);
			} else if (startingNode.key == searchingKey) {
				removeFoundNode(startingNode);
				System.out.println("\nKey " + searchingKey + " with Value " + startingNode.value
						+ " removed successfully in position " + hash + " at tree height " + currentSearchHeight + ".");
			}
		}
	}

	/**
	 * Removes a node from a AVL-Tree, while balancing will be done if
	 * necessary.
	 */
	private void removeFoundNode(TreeNode removingNode) {
		TreeNode parentNode;

		if (removingNode.left == null || removingNode.right == null) {
			parentNode = removingNode;
		} else {
			parentNode = successor(removingNode);
			removingNode.key = parentNode.key;
		}

		TreeNode childNode;
		if (parentNode.left != null) {
			childNode = parentNode.left;
		} else {
			childNode = parentNode.right;
		}

		if (childNode != null) {
			childNode.parent = parentNode.parent;
		}

		if (parentNode.parent == null) {
			this.root = childNode;
		} else {
			if (parentNode == parentNode.parent.left) {
				parentNode.parent.left = childNode;
			} else {
				parentNode.parent.right = childNode;
			}
			recursiveBalance(parentNode.parent);
		}
		parentNode = null;
	}

	/**
	 * Left rotation using the given node.
	 */
	private TreeNode rotateLeft(TreeNode rotatingNode) {

		TreeNode rotatedTreeRootNode = rotatingNode.right;
		rotatedTreeRootNode.parent = rotatingNode.parent;

		rotatingNode.right = rotatedTreeRootNode.left;

		if (rotatingNode.right != null) {
			rotatingNode.right.parent = rotatingNode;
		}

		rotatedTreeRootNode.left = rotatingNode;
		rotatingNode.parent = rotatedTreeRootNode;

		if (rotatedTreeRootNode.parent != null) {
			if (rotatedTreeRootNode.parent.right == rotatingNode) {
				rotatedTreeRootNode.parent.right = rotatedTreeRootNode;
			} else if (rotatedTreeRootNode.parent.left == rotatingNode) {
				rotatedTreeRootNode.parent.left = rotatedTreeRootNode;
			}
		}

		setBalance(rotatingNode);
		setBalance(rotatedTreeRootNode);

		return rotatedTreeRootNode;
	}

	/**
	 * Right rotation using the given node.
	 */
	private TreeNode rotateRight(TreeNode rotatingNode) {

		TreeNode rotatedTreeRootNode = rotatingNode.left;
		rotatedTreeRootNode.parent = rotatingNode.parent;

		rotatingNode.left = rotatedTreeRootNode.right;

		if (rotatingNode.left != null) {
			rotatingNode.left.parent = rotatingNode;
		}

		rotatedTreeRootNode.right = rotatingNode;
		rotatingNode.parent = rotatedTreeRootNode;

		if (rotatedTreeRootNode.parent != null) {
			if (rotatedTreeRootNode.parent.right == rotatingNode) {
				rotatedTreeRootNode.parent.right = rotatedTreeRootNode;
			} else if (rotatedTreeRootNode.parent.left == rotatingNode) {
				rotatedTreeRootNode.parent.left = rotatedTreeRootNode;
			}
		}

		setBalance(rotatingNode);
		setBalance(rotatedTreeRootNode);

		return rotatedTreeRootNode;
	}

	/**
	 * Double rotation Left Right using the given node
	 */
	private TreeNode doubleRotateLeftRight(TreeNode rotatingNode) {
		rotatingNode.left = rotateLeft(rotatingNode.left);
		return rotateRight(rotatingNode);
	}

	/**
	 * Double rotation Right Left using the given node
	 */
	private TreeNode doubleRotateRightLeft(TreeNode rotatingNode) {
		rotatingNode.right = rotateRight(rotatingNode.right);
		return rotateLeft(rotatingNode);
	}

	/** Helper Functions */

	/**
	 * Returns the successor of a given node in the tree (search recursively).
	 */
	private TreeNode successor(TreeNode predecessorNode) {
		TreeNode successorNode = null;

		if (predecessorNode.left != null) {
			successorNode = predecessorNode.left;
			while (successorNode.right != null) {
				successorNode = successorNode.right;
			}
		} else {
			successorNode = predecessorNode.parent;
			while (successorNode != null && predecessorNode == successorNode.left) {
				predecessorNode = successorNode;
				successorNode = predecessorNode.parent;
			}
		}

		return successorNode;
	}

	public int height() {
		return height(this.root);
	}

	/**
	 * Calculating the height of a node.
	 */
	private int height(TreeNode currentNode) {
		if (currentNode == null) {
			return -1;
		}
		if (currentNode.left == null && currentNode.right == null) {
			return 0;
		} else if (currentNode.left == null) {
			return 1 + height(currentNode.right);
		} else if (currentNode.right == null) {
			return 1 + height(currentNode.left);
		} else {
			return 1 + maximum(height(currentNode.left), height(currentNode.right));
		}
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
	 * All information about a node.
	 */
	private void getAll(TreeNode node) {
		int l = 0;
		int r = 0;
		int p = 0;
		if (node.left != null) {
			l = node.left.key;
		}
		if (node.right != null) {
			r = node.right.key;
		}
		if (node.parent != null) {
			p = node.parent.key;
		}

		System.out.println("Left: " + l + " Key: " + node.key + " Right: " + r + " Parent: " + p + " Balance: " + node.balance);

		if (node.left != null) {
			getAll(node.left);
		}
		if (node.right != null) {
			getAll(node.right);
		}
	}

	private void setBalance(TreeNode currentNode) {
		currentNode.balance = height(currentNode.right) - height(currentNode.left);
	}

	public TreeNode top() {
		return this.root;
	}

	public TreeNode left() {
		return this.root.left;
	}

	public TreeNode right() {
		return this.root.right;
	}

	/**
	 * Calculates the Inorder traversal of this tree.
	 */
	public ArrayList<TreeNode> inOrder() {
		ArrayList<TreeNode> ret = new ArrayList<TreeNode>();
		inOrder(root, ret);
		return ret;
	}

	/**
	 * Function to calculate in order recursively.
	 */
	private void inOrder(TreeNode node, ArrayList<TreeNode> io) {
		if (node == null) {
			return;
		}
		inOrder(node.left, io);
		io.add(node);
		inOrder(node.right, io);
	}

	public void printAll() {
		AVLTreePrinter.printNode(this.root);
	}

}
