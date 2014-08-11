package util.graphics;

import tree.algorithms.LeftistTree;
import tree.node.LeftistTreeNode;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class SurfaceTreeForLeftistTree extends JPanel {

    private static final long serialVersionUID = -9054075219988539709L;
    private LeftistTree tree;
    private LeftistTreeNode root;
    private String title = "";
    private int screenWidth, screenHeight;

    public static final int DIMENSION_TOP = 66, DIMENSION_WIDTH = 300;

    Node selection = null;

    Point linkEnd = null;
    Node linkTarget = null;

    public SurfaceTreeForLeftistTree(String title, LeftistTree tree) {
        setDimensions();
        this.title = title;
        this.tree = tree;
        this.root = tree.top();

        setDimensions();
        setLayout(null);

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        setPreferredSize(new Dimension(screenWidth, screenHeight));

        setSelection(null);

        printAll(this.root, null, null, screenWidth / 2);

        JFrame frame = makeFrame();
        frame.setVisible(true);
    }

    private void setDimensions() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
    }

    public JFrame makeFrame() {

        JFrame f = new JFrame(title);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setPreferredSize(new Dimension(screenWidth, DIMENSION_WIDTH * tree.height()));
        JScrollPane scrollFrame = new JScrollPane(this);
        setAutoscrolls(true);
        scrollFrame.setPreferredSize(new Dimension(screenWidth, screenHeight));

        Container contentPane = f.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollFrame, BorderLayout.CENTER);
        f.pack();

        return f;
    }

    private void printAll(LeftistTreeNode root, LeftistTreeNode parent, Node parentNode, int parentPosition) {
        Node p;

        if (root != null && parent == null) {
            p = makeNode(root.value + "<br/>npl: " + tree.npl(root, -1), new Point(parentPosition, getRectNextTop(-1)), Node.COLOR_PARENT);
        } else {
            p = parentNode;
        }

        if (root != null) {
            recursivePrint(root.left, parentPosition, null, p);
            recursivePrint(root.right, parentPosition, null, p);
        }
    }

    public void recursivePrint(LeftistTreeNode root, int parentPosition, Node innerNode, Node p) {
        Node innerParent;
        int position;

        if (root != null) {

            int height = tree.heightRootToNode(root);
            int nextTop = getRectNextTop(height);

            if (innerNode != null)
                innerParent = innerNode;
            else
                innerParent = p;

            if (root.parent.left != null && root.parent.left.equals(root)) {
                position = getRectNextLeft(parentPosition, height);
                innerNode = makeNode(root.value + "<br/>npl: " + tree.npl(root, -1), new Point(position, nextTop), Node.COLOR_LEFT);
            } else {
                position = getRectNextRight(parentPosition, height);
                innerNode = makeNode(root.value + "<br/>npl: " + tree.npl(root, -1), new Point(position, nextTop), Node.COLOR_RIGHT);
            }

            innerNode.setParent(innerParent);
            parentPosition = position;

            // --
            recursivePrint(root.left, parentPosition, innerNode, p);
            recursivePrint(root.right, parentPosition, innerNode, p);
        }

    }


    public class Node extends JLabel {

        private static final long serialVersionUID = -3405121483030773951L;
        Node parent = null;
        private Set<Node> children = new HashSet<Node>();
        public static final String COLOR_LEFT = "red", COLOR_RIGHT = "blue", COLOR_PARENT = "black";

        public Node(String text, Point location, String color) {
            super("<html>"
                    + "<div style='width: 35px; height: 35px; color: " + color
                    + "; font-family: Arial; padding-top: 10px; text-align: center; background: white; "
                    + "border: 1px solid black; margin: auto;'>" + text + "<div></html>");
            setOpaque(true);
            setLocation(location);
            setSize(getPreferredSize());
        }

        public void setText(String text) {
            super.setText(text);
            setSize(getPreferredSize());
        }

        public void setParent(Node newParent) {
            if (parent != null) {
                parent.getChildren().remove(this);
            }
            this.parent = newParent;
            if (newParent != null) {
                newParent.getChildren().add(this);
            }
        }

        public boolean hitsLinkHotspot(Point pt) {
            Rectangle r = getBounds();
            int x = pt.x - r.x;
            int y = pt.y - r.y;
            x -= (r.width / 2 - 4);
            return (-2 < x && x < 10 && -2 < y && y < 10);
        }

        public void paintLink(Graphics g) {
            Rectangle myRect = getBounds();
            int x1 = myRect.x + myRect.width / 2;
            int y1 = myRect.y + 4;

            int x2;
            int y2;
            if (parent != null) {
                Rectangle parentRect = parent.getBounds();
                x2 = parentRect.x + parentRect.width / 2;
                y2 = parentRect.y + parentRect.height;
            } else if (selection == this && linkEnd != null) {
                x2 = linkEnd.x;
                y2 = linkEnd.y;
            } else {
                return;
            }

            g.drawLine(x1, y1, x2, y2);
        }

        public Set<Node> getChildren() {
            return children;
        }

        public void setChildren(Set<Node> children) {
            this.children = children;
        }

    } // end of Node class

    // Resuming Tree methods.

    // paint links and nodes
    public void paintChildren(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        int n = getComponentCount();
        for (int i = 0; i < n; ++i) {
            Component c = getComponent(i);
            if (c instanceof Node) {
                ((Node) c).paintLink(g2);
            }
        }

        super.paintChildren(g);
    }

    // make a new node
    public Node makeNode(String text, Point pt, String color) {
        Node n = new Node(text, pt, color);
        add(n);
        return n;
    }

    // Mouse listener
    // for selecting nodes, moving nodes, and dragging out links
    private MouseInputListener mouseListener = new MouseInputAdapter() {
        boolean dragging = false;
        int offsetX;
        int offsetY;

        public void mousePressed(MouseEvent e) {
            Component c = getComponentAt(e.getPoint());
            if (c instanceof Node) {
                setSelection((Node) c);
            } else {
                setSelection(null);
            }
        }

        public void mouseDragged(MouseEvent e) {
            if (selection == null) {
                return;
            }

            if (dragging) {
                selection.setLocation(e.getX() + offsetX, e.getY() + offsetY);
                repaint();
            } else {
                dragging = true;
                offsetX = selection.getX() - e.getX();
                offsetY = selection.getY() - e.getY();
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (dragging) {
                dragging = false;
            } else if (linkEnd != null) {
                if (linkTarget != null) {
                    selection.setParent(linkTarget);
                }
                linkEnd = null;
                linkTarget = null;
                repaint();
            }
        }
    };

    public void setSelection(Node n) {

        if (selection != null) {
            selection.repaint();
        }

        selection = n;
    }

    private int getRectNextLeft(int parentPosition, int division) {
        return -DIMENSION_WIDTH / division + parentPosition;
    }

    private int getRectNextRight(int parentPosition, int division) {
        return DIMENSION_WIDTH / division + parentPosition;
    }

    private int getRectNextTop(int i) {
        if (i == -1)
            return DIMENSION_TOP;

        return (i + 1) * DIMENSION_TOP;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString("Black node = root node", 50, 30);
        g2d.drawString("Red nodes = left nodes", 50, 50);
        g2d.drawString("Blue nodes = right nodes", 50, 70);
    }
}
