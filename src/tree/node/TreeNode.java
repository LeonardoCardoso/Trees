package tree.node;

public class TreeNode {
    public String value;
    public TreeNode left;
    public TreeNode right;
    public TreeNode parent;
    public int balance;

    public TreeNode(String v) {
        left = right = parent = null;
        balance = 0;
        value = v;
    }
}
