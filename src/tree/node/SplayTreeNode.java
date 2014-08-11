package tree.node;

public class SplayTreeNode {

    public String value;
    public SplayTreeNode left;
    public SplayTreeNode right;
    public SplayTreeNode parent;

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
