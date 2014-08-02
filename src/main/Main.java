package main;

import element.Element;
import tree.algorithms.LeftistTree;
import util.LeftTreeGraphicPrinter;

public class Main {

    public static final Element[] SET = {
            new Element("02"),
            new Element("11"),
            new Element("21"),
            new Element("14"),
            new Element("32"),
            new Element("65"),
            new Element("09"),
            new Element("17"),
            new Element("01"),
            new Element("04")
    };

    public static void main(String[] args) {
        callLeftTree();
    }

    private static void callLeftTree() {
        LeftistTree leftistTree = new LeftistTree();

        for (Element element : SET) {
            leftistTree.insert(element.value);
        }

//        leftistTree.printAll();

        LeftTreeGraphicPrinter.drawAll("Left Tree", leftistTree);

    }


}
