package tree.node;

public class SimpleNode {
	private int key;
	private int value;
	private SimpleNode next;

	public SimpleNode(int key, int value) {
		this.key = key;
		this.value = value;
		this.next = null;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public SimpleNode getNext() {
		return next;
	}

	public void setNext(SimpleNode next) {
		this.next = next;
	}
}
