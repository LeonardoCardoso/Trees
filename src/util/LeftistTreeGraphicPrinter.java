package util;

import util.graphics.SurfaceTreeForLeftistTree;
import tree.algorithms.LeftistTree;

import javax.swing.*;

public class LeftistTreeGraphicPrinter {

    public static void drawAll(final String title, final LeftistTree tree) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SurfaceTreeForLeftistTree(title, tree);
            }
        });
    }

}
