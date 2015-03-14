package ru.litsey2.work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import ru.litsey2.cuttesseract.Colors;
import ru.litsey2.cuttesseract.geometry.Cube4d;
import ru.litsey2.cuttesseract.geometry.Geometry;
import ru.litsey2.cuttesseract.geometry.Plane4d;
import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

public class CutGenerator {

	PrintWriter pw;

	Cube4d cube = new Cube4d(1, Colors.CUBE_COLOR);

	Point4d[] pts = new Point4d[4];

	static double from = -1;
	static double to = 1;
	static double step = 1;
	int Fstart = 0;
	int Fend = 0;

	long cnt = 0;
	double startTime = System.currentTimeMillis() / 1000L;

	// HashSet<Graph> set = new HashSet<Graph>();
	Graph[] Graps = new Graph[1000];
	int now = 0;

	// int numsnow = 0;
	// int[] nums = new int[100];

	void gend(boolean skip) {
		Plane4d plane = new Plane4d(pts[0], pts[1], pts[2], pts[3]);
		if (Double.isNaN(plane.getNormal().getW())
				|| Double.isNaN(plane.getNormal().getX())
				|| Double.isNaN(plane.getNormal().getY())
				|| Double.isNaN(plane.getNormal().getZ())) {
			return;
		}
		Set<Segment4d> cut = Geometry.makeSection(plane, cube);
		Graph g = new Graph(cut);
		if (g.n < 4) {
			return;
		}
		// nums[g.n] = 1;
		if (!skip) {
			for (int i = 0; i < now; i++) {
				if (g.equals(Graps[i])) {
					return;
				}
			}
		}
		if (g.hash1 == 0)
			g.hash();
		//System.out.println(g.hash1 + " " + g.hash2);
		Graps[now] = g;
		now++; // boolean added = set.add(g); // if (added) {
		pw.println("[" + now + "] " + pts[0] + " " + pts[1] + " " + pts[2]
				+ " " + pts[3]);
		pw.flush();
		/*
		 * if(now == 10) { throw new IllegalStateException("Too many cuts"); }
		 */
		// }
	}

	void gen(int num, int st) {
		if (num == 4) {
			cnt++;
			if (cnt < Fstart)
				return;
			/*
			 * if (cnt == Fend) { pw.println(Arrays.toString(nums)); pw.flush();
			 * }
			 */
			if (cnt > Fend) {
				return;
			}
			if (cnt % 1000 == 0) {
				System.out.println(cnt / 1000 + "."
						+ String.format("%03d", cnt % 1000) + " | ["
						+ (double) cnt
						/ (System.currentTimeMillis() / 1000L - startTime)
						+ "]");
			}
			// System.out.println(pts[0].toString() + " " + pts[1].toString()
			// + " " + pts[2].toString() + " " + pts[3].toString());
			// long tt = System.currentTimeMillis();
			gend(false);
			// System.out.println("gend took " + (System.currentTimeMillis() -
			// tt) + " ms");
			return;
		}
		PointGenerator gen = new PointGenerator(from, to, step, st);
		Point4d p;
		while ((p = gen.nextPoint()) != null) {
			pts[num] = p;
			// System.out.println(Arrays.toString(pts));
			gen(num + 1, st + 1);
		}

	}

	public static class Checker {
		Cube4d cube = new Cube4d(1, Colors.CUBE_COLOR);
		ArrayList<Graph> graphs = new ArrayList<Graph>();
		int ans[][] = new int[1000][];
		int now = 0;
		Scanner scanner;

		public Checker() throws FileNotFoundException {
			scanner = new Scanner(new File("cuts_375.txt"));
		}

		public void check() {
			Point4d[] pts = new Point4d[4];
			scanner.next();
			for (int i = 0; i < 4; i++) {
				double coor[] = new double[4];
				for (int j = 0; j < 4; j++) {
					coor[j] = scanner.nextInt();
				}
				pts[i] = new Point4d(coor[0], coor[1], coor[2], coor[3]);
			}
			Plane4d plane = new Plane4d(pts[0], pts[1], pts[2], pts[3]);
			Set<Segment4d> cut = Geometry.makeSection(plane, cube);
			Graph g = new Graph(cut);
			System.out.println("[" + now + "]" + Arrays.toString(g.deg));
			/*
			 * for (int i = 0; i < now; i++) { if (Arrays.equals(ans[i], g.deg))
			 * { } else { return; } } ans[now] = g.deg; graphs.add(g);
			 */
			now++;
		}

		public void out() {
			System.out.println("" + this.now);
			for (int i = 0; i < this.now; i++) {
				System.out.println(Arrays.toString(this.ans[i]) + "\n");
			}
		}
	}

	public static int[] getPermByPoints(Point4d[] points) {
		int[] answer = new int[4];
		for (int i = 0; i < 4; i++) {
			PointGenerator gen = new PointGenerator(from, to, step, 0);
			Point4d p;
			int count = 0;
			while ((p = gen.nextPoint()) != null) {
				if (p.equals(points[i])) {
					answer[i] = count;
				}
				count++;
			}
		}
		return answer;
	}

	public static int getNumByPerm(int[] perm) {
		int count = 0;
		int g = 81;
		for (int i = 0; i < g; i++) {
			for (int i1 = 0; i1 < g; i1++) {
				for (int i2 = 0; i2 < g; i2++) {
					for (int i3 = 0; i3 < g; i3++) {
						if (perm[0] == i && perm[1] == i1 && perm[2] == i2
								&& perm[3] == i3)
							return count;
						count++;
					}
				}
			}
		}
		return count;
	}

	public void getFstart() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("Fstart.txt"));
		Fstart = scanner.nextInt();
		Fend = scanner.nextInt();
		scanner.close();
	}

	public void prEnd() {
		pw.println("[" + this.Fend + "][END]");
	}

	public void merge() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("merge.txt"));
		int k = 1;
		for (int i = 0; i < 2472; i++) {
			Point4d p = null;
			boolean skip = false;
			if (scanner.nextInt() == k) {
				k++;
				skip = true;
			} else {
				k = 1000000000;
			}
			for (int j = 0; j < 4; j++) {
				p = new Point4d(scanner.nextDouble(), scanner.nextDouble(),
						scanner.nextDouble(), scanner.nextDouble());
				pts[j] = p;
			}
			// System.out.println("[" + i + "]");
			gend(skip);
			// System.out.println(Graps[now - 1].hash);
		}
		scanner.close();
	}

	public static void main(String[] args) throws FileNotFoundException {

		CutGenerator cg = new CutGenerator();
		try {
			cg.pw = new PrintWriter("cuts.txt");
			cg.getFstart();
			cg.gen(0, 0);
			// cg.merge();
			cg.prEnd();
		} finally {
			cg.pw.close();
		}
		/*
		 * Checker c = new Checker(); int n = 375; for (int i = 0; i < n; i++) {
		 * c.check(); }
		 */
		System.out.println("[END]");
	}
}
