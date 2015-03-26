package ru.litsey2.cuttesseract.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import ru.litsey2.cuttesseract.Colors;

public class Geometry {

	/**
	 * The maximum error value possible in comparison
	 * 
	 * @see #compareEps(double, double)
	 */
	public static final double EPS = 1e-6;

	/**
	 * Compares two doubles. Two numbers are <i>close</i> if their absolute
	 * difference is less or equal than the value of <code>{@link #EPS}</code>
	 * 
	 * @param a
	 *            first double to compare
	 * @param b
	 *            second double to compare
	 * @return the value 0 if <code>a</code> is <i>close</i> to <code>b</code>;
	 *         a value less than 0 if <code>a</code> is less than <code>b</code>
	 *         ; a value greater than 0 if <code>a</code> is greater than
	 *         <code>b</code>
	 */
	public static int compareEps(double a, double b) {
		if (Math.abs(a - b) <= EPS) {
			return 0;
		}
		if (a < b) {
			return -1;
		} else {
			return 1;
		}
	}

	/**
	 * Returns section edges as 4d segments
	 * 
	 * @param plane
	 *            section plane
	 * @param cube
	 *            section cube
	 * @return set of 4d segments
	 */
	public static ArrayList<Segment4d> makeSection(Plane4d plane, Cube4d cube) {
		Set<Point4d> cutPoints = new TreeSet<Point4d>();
		for (int i = 0; i < 16; i++) {
			for (int j = i + 1; j < 16; j++) {
				if (!cube.getHasEdge()[i][j]) {
					continue;
				}
				Segment4d e = new Segment4d(cube.getVertices()[i],
						cube.getVertices()[j]);
				ArrayList<Point4d> inter = e.intersectWithPlane(plane);
				cutPoints.addAll(inter);
			}
		}

		Set<Segment4d> cutEdges = new TreeSet<Segment4d>();
		for (Point4d a : cutPoints) {
			for (Point4d b : cutPoints) {
				if (a == b) {
					continue;
				}
				int sameness = 0;
				if (a.x == b.x && (a.x == 1 || a.x == -1)) {
					sameness++;
				}
				if (a.y == b.y && (a.y == 1 || a.y == -1)) {
					sameness++;
				}
				if (a.z == b.z && (a.z == 1 || a.z == -1)) {
					sameness++;
				}
				if (a.w == b.w && (a.w == 1 || a.w == -1)) {
					sameness++;
				}
				if (sameness >= 2) {
					cutEdges.add(new Segment4d(a, b, Colors.CUT_COLOR));
				}
			}
		}

		for (int i = 0; i < cube.getVertices().length; i++) {
			for (int j = i + 1; j < cube.getVertices().length; j++) {
				for (int k = j + 1; k < cube.getVertices().length; k++) {
					for (int l = k + 1; l < cube.getVertices().length; l++) {
						Point4d a = cube.getVertices()[i];
						Point4d b = cube.getVertices()[j];
						Point4d c = cube.getVertices()[k];
						Point4d d = cube.getVertices()[l];

						Segment4d ab = new Segment4d(a, b, Colors.CUT_COLOR);
						Segment4d ac = new Segment4d(a, c, Colors.CUT_COLOR);
						Segment4d ad = new Segment4d(a, d, Colors.CUT_COLOR);
						Segment4d bc = new Segment4d(b, c, Colors.CUT_COLOR);
						Segment4d bd = new Segment4d(b, d, Colors.CUT_COLOR);
						Segment4d cd = new Segment4d(c, d, Colors.CUT_COLOR);

						if (!cutEdges.containsAll(Arrays.asList(ab, ac, ad, bc,
								bd, cd))) {
							continue;
						}

						Point4d[] points = { a, b, c, d };
						for (int m = 0; m < points.length; m++) {
							for (int n = 0; n < points.length; n++) {
								Point4d p = points[m];
								Point4d q = points[n];

								int sameness = 0;
								if (compareEps(p.x, q.x) == 0) {
									sameness++;
								}
								if (compareEps(p.y, q.y) == 0) {
									sameness++;
								}
								if (compareEps(p.z, q.z) == 0) {
									sameness++;
								}
								if (compareEps(p.w, q.w) == 0) {
									sameness++;
								}
								if (sameness != 3) {
									cutEdges.remove(new Segment4d(p, q,
											Colors.CUT_COLOR));
								}
							}
						}

					}
				}
			}
		}
		
		ArrayList<Segment4d> list = new ArrayList<Segment4d>();
		list.addAll(cutEdges);

		return list;
	}

}
