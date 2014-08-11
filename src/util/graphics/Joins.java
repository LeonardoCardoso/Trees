package util.graphics;


import tree.node.SimpleNode;

import javax.swing.*;

public class Joins extends JFrame {

    private static final long serialVersionUID = -4142017181972222003L;
    private String title = "";
    private SimpleNode[] nodes;
    private double loadFactor;

    public Joins(String title, SimpleNode[] nodes, double loadFactor) {
        this.title = title;
        this.nodes = nodes;
        this.loadFactor = loadFactor;
        initUI();
    }

    private void initUI() {

        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int jPanelWidth = nodes.length * Surface.DIMENSION_WIDTH + 2 * Surface.DIMENSION_LEFT;
        setSize(jPanelWidth, Surface.DIMENSION_HEIGHT * 10);
        add(new Surface(nodes, loadFactor));
        setLocationRelativeTo(null);

    }

}