package graphics;


import tree.node.SimpleNode;

import javax.swing.*;
import java.awt.*;

public class Surface extends JPanel {

    private static final long serialVersionUID = 8753131469505687164L;
    public static final int DIMENSION_TOP = 66, DIMENSION_LEFT = 35, DIMENSION_WIDTH = 60, DIMENSION_HEIGHT = 60;
    public static final int OFFSET = -2, SIDE_OFFSET = 1;
    public static final int LINE_HEIGHT = 15;
    public static final int LOAD_FACTOR_TOP = 20;
    public static final int BALANCING_FACTOR_TOP = 40;
    public static final int NUMBER_WIDTH = 5, NUMBER_POS = DIMENSION_TOP - NUMBER_WIDTH;

    private SimpleNode[] nodes;
    private Double loadFactor = null;

    public Surface(SimpleNode[] nodes, double loadFactor) {
        super();
        this.nodes = nodes;
        this.loadFactor = loadFactor;
    }

    private void doDrawing(Graphics g) {
        for (int i = 0; i < nodes.length; i++) {
            drawNextSimpleNode(i, g);
        }
    }

    private void drawNextSimpleNode(int i, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        printElements(i, g2d);
    }

    private void printElements(int i, Graphics2D g2d) {
        SimpleNode currentSimpleNode = nodes[i];
        int nextLeft = getRectNextLeft(i);
        int numberNextLeft = getNumberNextLeft(currentSimpleNode != null ? currentSimpleNode.getKey() : null, nextLeft);
        int numberIncrementLeft = getNumberNextLeft(i, nextLeft);

        drawElement(i, currentSimpleNode, g2d, nextLeft, numberNextLeft, numberIncrementLeft, DIMENSION_TOP,
                getNumberTop(0));

        // Print next elements in Open Hashing
        printChildren(currentSimpleNode, g2d, nextLeft, numberIncrementLeft);
    }

    private void printChildren(SimpleNode currentSimpleNode, Graphics2D g2d, int nextLeft, int numberIncrementLeft) {
        if (currentSimpleNode != null && currentSimpleNode.getNext() != null) {
            SimpleNode item = currentSimpleNode.getNext();
            int numberNextLeft = getNumberNextLeft(item != null ? item.getKey() : null, nextLeft);
            int count = 1;
            int x = nextLeft + DIMENSION_WIDTH / 2;

            while (item != null) {
                int y1 = DIMENSION_TOP + (count == 1 ? 0 : LINE_HEIGHT * (count - 1)) + DIMENSION_HEIGHT * count;
                int y2 = y1 + LINE_HEIGHT;
                int numberTop = getNumberTop(count);
                if (count != 0) {
                    g2d.drawLine(x, y1, x, y2);
                }
                drawElement(null, item, g2d, nextLeft, numberNextLeft, numberIncrementLeft, y2, numberTop);
                item = item.getNext();
                count++;
            }
        }
    }

    private void drawElement(Integer i, SimpleNode currentSimpleNode, Graphics2D g2d, int nextLeft,
                             int numberNextLeft, int numberIncrementLeft, int dimensionTop, int numberTop) {
        // Drawing rectangle
        BasicStroke bs2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2d.setStroke(bs2);
        g2d.drawRect(nextLeft, dimensionTop, DIMENSION_WIDTH, DIMENSION_HEIGHT);

        // Setting color to rectangle
        g2d.setColor(Color.WHITE);
        g2d.fillRect(nextLeft + SIDE_OFFSET, dimensionTop + SIDE_OFFSET, DIMENSION_WIDTH + OFFSET, DIMENSION_HEIGHT + OFFSET);
        g2d.setColor(Color.BLACK);

        // Drawing position
        if (i != null) {
            g2d.drawString(String.valueOf(i), numberIncrementLeft, NUMBER_POS);
        }

        // Drawing node key
        g2d.drawString(currentSimpleNode != null ? String.valueOf(currentSimpleNode.getKey()) : "", numberNextLeft, numberTop);

    }

    private int getNumberTop(int count) {
        return DIMENSION_TOP + (DIMENSION_HEIGHT + LINE_HEIGHT) * (count) + DIMENSION_HEIGHT - DIMENSION_HEIGHT / 2 + NUMBER_WIDTH;
    }

    private int getRectNextLeft(int i) {
        return DIMENSION_LEFT + i * DIMENSION_WIDTH;
    }

    private int getNumberNextLeft(Integer currentNumber, int nextLeft) {
        return currentNumber == null ? 0 : nextLeft + DIMENSION_WIDTH / 2 - numberOfDigits(currentNumber) * NUMBER_WIDTH;
    }

    public static int numberOfDigits(int number) {
        return number == 0 ? 1 : 1 + (int) Math.floor(Math.log10(Math.abs(number)));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

}