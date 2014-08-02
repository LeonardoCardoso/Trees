package util;

import tree.node.LeftistTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeftTreePrinter {

	public static <T extends Comparable<?>> void printNode(LeftistTreeNode root) {
		int maxLevel = LeftTreePrinter.maxLevel(root);

		if (maxLevel == 0)
			System.out.println("[ ]\n");
		else
			printNodeInternal(Collections.singletonList(root), 1, maxLevel);
	}

	private static <T extends Comparable<?>> void printNodeInternal(
			List<LeftistTreeNode> list, int level, int maxLevel) {
		if (list.isEmpty() || LeftTreePrinter.isAllElementsNull(list))
			return;

		int floor = maxLevel - level;
		int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

		LeftTreePrinter.printWhitespaces(firstSpaces);

		List<LeftistTreeNode> newNodes = new ArrayList<LeftistTreeNode>();
		for (LeftistTreeNode node : list) {
			if (node != null) {
				System.out.print(node.value);
				newNodes.add(node.left);
				newNodes.add(node.right);
			} else {
				newNodes.add(null);
				newNodes.add(null);
				System.out.print(" ");
			}
			LeftTreePrinter.printWhitespaces(betweenSpaces);
		}
		System.out.println("");

		for (int i = 1; i <= endgeLines; i++) {
			for (int j = 0; j < list.size(); j++) {
				LeftTreePrinter.printWhitespaces(firstSpaces - i);
				if (list.get(j) == null) {
					LeftTreePrinter.printWhitespaces(endgeLines + endgeLines + i
                            + 1);
					continue;
				}

				if (list.get(j).left != null)
					System.out.print("/");
				else
					LeftTreePrinter.printWhitespaces(1);

				LeftTreePrinter.printWhitespaces(i + i - 1);

				if (list.get(j).right != null)
					System.out.print("\\");
				else
					LeftTreePrinter.printWhitespaces(1);

				LeftTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
			}

			System.out.println("");
		}

		printNodeInternal(newNodes, level + 1, maxLevel);
	}

	private static void printWhitespaces(int count) {
		for (int i = 0; i < count; i++)
			System.out.print(" ");
	}

	private static <T extends Comparable<?>> int maxLevel(LeftistTreeNode node) {
		if (node == null)
			return 0;

		return Math.max(LeftTreePrinter.maxLevel(node.left), LeftTreePrinter.maxLevel(node.right)) + 1;
	}

	private static <T> boolean isAllElementsNull(List<T> list) {
		for (Object object : list) {
			if (object != null)
				return false;
		}
		return true;
	}
}