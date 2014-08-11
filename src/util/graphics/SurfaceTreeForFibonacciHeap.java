package util.graphics;

import tree.algorithms.FibonacciHeap;
import tree.algorithms.LeftistTree;
import tree.node.LeftistTreeNode;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class SurfaceTreeForFibonacciHeap extends JPanel {

    private static final long serialVersionUID = -9054075219988539709L;
    private FibonacciHeap heap;
    private String title = "";
    private int screenWidth, screenHeight;

    public static final int DIMENSION_TOP = 100, DIMENSION_LEFT = 35, DIMENSION_WIDTH = 100, DIMENSION_HEIGHT = 60;

    Node selection = null;

    Point linkEnd = null;
    Node linkTarget = null;
    int i = 0;

    public SurfaceTreeForFibonacciHeap(String title, FibonacciHeap heap) {
        setDimensions();
        this.title = title;
        this.heap = heap;

        setDimensions();
        setLayout(null);

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        setPreferredSize(new Dimension(screenWidth, screenHeight));

        setSelection(null);

        buildData();

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

        setPreferredSize(new Dimension(screenWidth, screenHeight - DIMENSION_LEFT));
        JScrollPane scrollFrame = new JScrollPane(this);
        setAutoscrolls(true);
        scrollFrame.setPreferredSize(new Dimension(screenWidth, screenHeight));

        Container contentPane = f.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollFrame, BorderLayout.CENTER);
        f.pack();

        return f;
    }

    private void buildData() {
        while (heap.root != null) {
            printAll(heap.root.tree, null, null, getRectNextLeft(i), i);
            i++;
            heap.root = heap.root.next;
        }
    }

    private void printAll(LeftistTree tree, LeftistTreeNode parent, Node parentNode, int parentPosition, int i) {
        Node p;

        LeftistTreeNode node = tree.root;

        if (node != null && parent == null) {
            p = makeNode(node.value + "<br/>npl: " + tree.npl(node, -1), new Point(parentPosition, getRectNextTop(-1)), Node.COLOR_PARENT, i);
        } else {
            p = parentNode;
        }

        if (tree.top() == null) {
            this.i--;
        }

        if (node != null) {
            recursivePrint(tree, node.left, parentPosition, null, p, i);
            recursivePrint(tree, node.right, parentPosition, null, p, i);
        }
    }

    public void recursivePrint(LeftistTree tree, LeftistTreeNode node, int parentPosition, Node innerNode, Node p, int i) {
        Node innerParent;
        int position;

        if (node != null) {

            int height = tree.heightRootToNode(node);
            int nextTop = getRectNextTop(height);

            if (innerNode != null)
                innerParent = innerNode;
            else
                innerParent = p;

            if (node.parent.left != null && node.parent.left.equals(node)) {
                position = getRectNextLeft(parentPosition, height);
                innerNode = makeNode(node.value + "<br/>npl: " + tree.npl(node, -1), new Point(position, nextTop), Node.COLOR_LEFT, i);
            } else {
                position = getRectNextRight(parentPosition, height);
                innerNode = makeNode(node.value + "<br/>npl: " + tree.npl(node, -1), new Point(position, nextTop), Node.COLOR_RIGHT, i);
            }

            innerNode.setParent(innerParent);
            parentPosition = position;

            // --
            recursivePrint(tree, node.left, parentPosition, innerNode, p, i);
            recursivePrint(tree, node.right, parentPosition, innerNode, p, i);
        }

    }


    public class Node extends JLabel {

        private static final long serialVersionUID = -3405121483030773951L;
        Node parent = null;
        private Set<Node> children = new HashSet<Node>();
        public static final String COLOR_LEFT = "red", COLOR_RIGHT = "blue", COLOR_PARENT = "black";

        public Node(String text, Point location, String color, int index) {
            super("<html>"
                    + (color == COLOR_PARENT ? "<div style='border: 1px dashed black; border-bottom: 0px; text-align: center;'>"
                    + index + "</div>" : "") + "<div style='width: 35px; height: 35px; color: " + color
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
    public Node makeNode(String text, Point pt, String color, int i) {
        Node n = new Node(text, pt, color, i);
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

    private int getRectNextLeft(int i) {
        return DIMENSION_LEFT + (i + 1) * (DIMENSION_WIDTH * 2 + DIMENSION_HEIGHT);
    }

    private int getRectNextLeft(int parentPosition, int division) {
        return -DIMENSION_WIDTH / (division + 1) + parentPosition;
    }

    private int getRectNextRight(int parentPosition, int division) {
        return DIMENSION_WIDTH / (division + 1) + parentPosition;
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
        g2d.drawString("Black node = heap node", 50, 30);
        g2d.drawString("Red nodes = left nodes", 50, 50);
        g2d.drawString("Blue nodes = right nodes", 50, 70);
    }
}
