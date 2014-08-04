package util;

import graphics.SurfaceTreeForFibonacciHeaps;
import tree.algorithms.FibonacciHeap;

import javax.swing.*;

public class FibonacciHeapGraphicPrinter {

    public static void drawAll(final String title, final FibonacciHeap heap) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SurfaceTreeForFibonacciHeaps(title, heap);
            }
        });
    }

}
