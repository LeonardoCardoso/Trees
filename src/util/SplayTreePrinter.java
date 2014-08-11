package util;

import tree.node.SplayTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SplayTreePrinter {

	public static <T extends Comparable<?>> void printNode(SplayTreeNode root) {
		int maxLevel = SplayTreePrinter.maxLevel(root);

		if (maxLevel == 0)
			System.out.println("[ ]\n");
		else
			printNodeInternal(Collections.singletonList(root), 1, maxLevel);
	}

	private static <T extends Comparable<?>> void printNodeInternal(
			List<SplayTreeNode> list, int level, int maxLevel) {
		if (list.isEmpty() || SplayTreePrinter.isAllElementsNull(list))
			return;

		int floor = maxLevel - level;
		int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

		SplayTreePrinter.printWhitespaces(firstSpaces);

		List<SplayTreeNode> newNodes = new ArrayList<SplayTreeNode>();
		for (SplayTreeNode node : list) {
			if (node != null) {
				System.out.print(node.value);
				newNodes.add(node.left);
				newNodes.add(node.right);
			} else {
				newNodes.add(null);
				newNodes.add(null);
				System.out.print(" ");
			}
			SplayTreePrinter.printWhitespaces(betweenSpaces);
		}
		System.out.println("");

		for (int i = 1; i <= endgeLines; i++) {
			for (int j = 0; j < list.size(); j++) {
				SplayTreePrinter.printWhitespaces(firstSpaces - i);
				if (list.get(j) == null) {
					SplayTreePrinter.printWhitespaces(endgeLines + endgeLines + i
                            + 1);
					continue;
				}

				if (list.get(j).left != null)
					System.out.print("/");
				else
					SplayTreePrinter.printWhitespaces(1);

				SplayTreePrinter.printWhitespaces(i + i - 1);

				if (list.get(j).right != null)
					System.out.print("\\");
				else
					SplayTreePrinter.printWhitespaces(1);

				SplayTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
			}

			System.out.println("");
		}

		printNodeInternal(newNodes, level + 1, maxLevel);
	}

	private static void printWhitespaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" ");
	}

	private static <T extends Comparable<?>> int maxLevel(SplayTreeNode node) {
		if (node == null)
			return 0;

		return Math.max(SplayTreePrinter.maxLevel(node.left), SplayTreePrinter.maxLevel(node.right)) + 1;
	}

	private static <T> boolean isAllElementsNull(List<T> list) {
		for (Object object : list) {
			if (object != null)
				return false;
		}
		return true;
	}
}