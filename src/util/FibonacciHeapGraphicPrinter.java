package util;

import graphics.SurfaceTreeForFibonacciHeap;
import tree.algorithms.FibonacciHeap;

import javax.swing.*;

public class FibonacciHeapGraphicPrinter {

    public static void drawAll(final String title, final FibonacciHeap heap) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SurfaceTreeForFibonacciHeap(title, heap);
            }
        });
    }

}
