package ru.litsey2.cuttesseract.geometry;

import java.util.ArrayList;
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
	 * @param plane section plane 
	 * @param cube section cube
	 * @return set of 4d segments
	 */
	public static Set<Segment4d> makeSection(Plane4d plane, Cube4d cube) {
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
				if (a.getX() == b.getX() && (a.getX() == 1 || a.getX() == -1)) {
					sameness++;
				}
				if (a.getY() == b.getY() && (a.getY() == 1 || a.getY() == -1)) {
					sameness++;
				}
				if (a.getZ() == b.getZ() && (a.getZ() == 1 || a.getZ() == -1)) {
					sameness++;
				}
				if (a.getW() == b.getW() && (a.getW() == 1 || a.getW() == -1)) {
					sameness++;
				}
				if (sameness >= 2) {
					cutEdges.add(new Segment4d(a, b, Colors.CUT_COLOR));
				}
			}
		}

		return cutEdges;
	}

}
