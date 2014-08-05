package util;

import graphics.SurfaceTreeForAVLTree;
import tree.algorithms.AVLTree;

import javax.swing.*;

public class AVLTreeGraphicPrinter {

    public static void drawAll(final String title, final AVLTree tree) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SurfaceTreeForAVLTree(title, tree);
            }
        });
    }

}
