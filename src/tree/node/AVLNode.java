package tree.node;

import tree.AVLTree;

public class AVLNode {
	private int key;
	private int value;
	private AVLTree avlTree;

	public AVLNode(int key, int value) {
		this.avlTree = new AVLTree();
		this.avlTree.insert(key, value);
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public AVLTree getAVLTree() {
		return avlTree;
	}

	public void setAVLTree(AVLTree avlTree) {
		this.avlTree = avlTree;
	}

}
