package util;

import java.util.ArrayList;

import graphics.SurfaceTree;
import graphics.SurfaceTreeForRadixHeap;

import javax.swing.SwingUtilities;

import element.Pair;

public class PrintHeap {
	public static void printPairs(Pair[] set) {

		int sumBreaks = 0;
		int nextBreak = 1;
		int level = 1;
		int levels = countLevels(set);
		int mininum = (int) Math.pow(2, levels - 1);
		double mininumPixels = mininum * 8.5;

		sumBreaks += nextBreak;
		int restart = 0;

		for (int i = 0; i < set.length; i++) {

			if (restart == 0) {
				printSpace((int) (mininumPixels - (i + 1) * set[i].toString().length()) / 2);
			}
			System.out.print(set[i].toString());
			restart++;

			if (sumBreaks == i + 1) {
				restart = 0;
				nextBreak = (int) Math.pow(2, level++);
				System.out.println();
				sumBreaks += nextBreak;
			}

		}

	}

	public static void printSpace(int count) {
		for (int i = 0; i < count; i++) {
			System.out.print(" ");
		}
	}

	public static int countLevels(Pair[] set) {
		int sumBreaks = 0;
		int nextBreak = 1;
		int levels = 1;
		sumBreaks += nextBreak;
		for (int i = 0; i < set.length; i++) {
			if (sumBreaks == i + 1) {
				nextBreak = (int) Math.pow(2, levels++);
				sumBreaks += nextBreak;
			}
		}
		return levels;
	}

	public static void drawAll(final String title, final Pair[] nodes) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SurfaceTree(title, nodes);
			}
		});
	}

	public static void drawAllRadix(final String title, final ArrayList<Pair>[] nodes) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SurfaceTreeForRadixHeap(title, nodes);
			}
		});
	}
}
