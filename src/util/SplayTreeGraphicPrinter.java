package util;

import util.graphics.SurfaceTreeForSplayTree;
import tree.algorithms.SplayTree;

import javax.swing.*;

public class SplayTreeGraphicPrinter {

    public static void drawAll(final String title, final SplayTree tree) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SurfaceTreeForSplayTree(title, tree);
            }
        });
    }

}
