package main;

import tree.element.Element;
import tree.algorithms.AVLTree;
import tree.algorithms.FibonacciHeap;
import tree.algorithms.LeftistTree;
import tree.algorithms.SplayTree;
import util.AVLTreeGraphicPrinter;
import util.FibonacciHeapGraphicPrinter;
import util.LeftistTreeGraphicPrinter;
import util.SplayTreeGraphicPrinter;

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

//            new Element("03"),
//            new Element("08"),
//            new Element("21"),
//            new Element("04"),
//            new Element("09"),
//            new Element("17"),
//            new Element("32"),
//            new Element("19"),
//            new Element("44"),
//            new Element("03"),
//            new Element("07"),
//            new Element("19"),
//            new Element("26"),

            new Element("03"),
            new Element("08"),
            new Element("17"),
            new Element("04"),
            new Element("29"),
            new Element("36"),
            new Element("49"),
            new Element("18"),
            new Element("12"),
            new Element("07"),
            new Element("23"),
            new Element("52"),
            new Element("48"),
            new Element("11"),
            new Element("32"),
    };

    public static void main(String[] args) {
//        callAVLTree();
//        callLeftistTree();
//        callFibonacciHeap();
        callSplayTree();
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

//        fibonacciHeap.find("17");
//        fibonacciHeap.remove();

        FibonacciHeapGraphicPrinter.drawAll("Fibonacci Heap", fibonacciHeap);
    }

    private static void callSplayTree() {
        SplayTree leftistTree = new SplayTree();

        for (Element element : SET) {
            leftistTree.insert(element.value);
        }

//        leftistTree.printAll();

        SplayTreeGraphicPrinter.drawAll("Splay Tree", leftistTree);

    }


}
