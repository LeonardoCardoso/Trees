package main;

import element.Element;
import tree.algorithms.AVLTree;
import tree.algorithms.FibonacciHeap;
import tree.algorithms.LeftistTree;
import util.AVLTreeGraphicPrinter;
import util.FibonacciHeapGraphicPrinter;
import util.LeftistTreeGraphicPrinter;

public class Main {

    public static final Element[] SET = {
//            new Element("02"),
//            new Element("11"),
//            new Element("21"),
//            new Element("14"),
//            new Element("32"),
//            new Element("65"),
//            new Element("09"),
//            new Element("17"),
//            new Element("01"),
//            new Element("04"),

            new Element("03"),
            new Element("08"),
            new Element("21"),
            new Element("04"),
            new Element("09"),
            new Element("17"),
            new Element("32"),
            new Element("19"),
            new Element("44"),
            new Element("03"),
            new Element("07"),
            new Element("19"),
            new Element("26"),
    };

    public static void main(String[] args) {
//        callAVLTree();
//        callLeftistTree();
//        callFibonacciHeap();
    }

    private static void callAVLTree() {
        AVLTree avlTree = new AVLTree();

        for (Element element : SET) {
            avlTree.insert(element.value);
        }

//        avlTree.printAll();

        AVLTreeGraphicPrinter.drawAll("AVL Tree", avlTree);

    }

    private static void callLeftistTree() {
        LeftistTree leftistTree = new LeftistTree();

        for (Element element : SET) {
            leftistTree.insert(element.value);
        }

//        leftistTree.printAll();

        LeftistTreeGraphicPrinter.drawAll("Leftist Tree", leftistTree);

    }

    private static void callFibonacciHeap() {
        FibonacciHeap fibonacciHeap = new FibonacciHeap();

        for (Element element : SET) {
            fibonacciHeap.insert(element.value);
        }

        FibonacciHeapGraphicPrinter.drawAll("Fibonacci Heap", fibonacciHeap);
    }


}
