package tree.node;

public class LeftistTreeNode {

    public String value;
    public LeftistTreeNode left;
    public LeftistTreeNode right;
    public LeftistTreeNode parent;

    public LeftistTreeNode(String v) {
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
