package tree.node;

public class SplayTreeNode {

    public String value;
    public SplayTreeNode left;
    public SplayTreeNode right;
    public SplayTreeNode parent;

    public SplayTreeNode(SplayTreeNode splayTreeNode) {
        if (splayTreeNode != null) {
            this.value = splayTreeNode.value;
            this.left = splayTreeNode.left;
            this.right = splayTreeNode.right;
            this.parent = splayTreeNode.parent;
        }
    }

    public boolean isNull() {
        return (this.value == null && this.left == null && this.right == null && this.parent == null);
    }

    public SplayTreeNode(String v) {
        left = right = parent = null;
        value = v;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
