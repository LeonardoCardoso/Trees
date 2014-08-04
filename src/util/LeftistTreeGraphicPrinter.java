package util;

import graphics.SurfaceTree;
import tree.algorithms.LeftistTree;

import javax.swing.*;

public class LeftistTreeGraphicPrinter {

    public static void drawAll(final String title, final LeftistTree tree) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SurfaceTree(title, tree);
            }
        });
    }

}
