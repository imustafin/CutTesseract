package ru.litsey2.cuttesseract;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import ru.litsey2.cuttesseract.geometry.Cube4d;
import ru.litsey2.cuttesseract.geometry.Geometry;
import ru.litsey2.cuttesseract.geometry.Plane4d;
import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;
import ru.litsey2.cuttesseract.geometry.Vector4d;

public class CubeCut4d {

	static Scanner in = new Scanner(System.in);

	static Point4d readPoint4d() {
		int x = in.nextInt();
		int y = in.nextInt();
		int z = in.nextInt();
		int w = in.nextInt();
		return new Point4d(x, y, z, w);
	}

	public static void main(String[] args) {
		in.useLocale(Locale.US);
		PrintStream out = new PrintStream(System.out);

		Cube4d cube = new Cube4d(1, Colors.CUBE_COLOR);

		try {

			out.println("Enter input mode\n"
					+ "2 for point and normal vector\n" + "4 for 4 points4d\n"
					+ "0 for no cut, only cube");

			int mode = in.nextInt();

			Plane4d plane = null;

			if (mode != 2 && mode != 4 && mode != 0) {
				throw new IllegalArgumentException("Wrong mode " + mode);
			}
			if (mode != 0) {
				if (mode == 4) {
					Point4d[] pts = new Point4d[4];

					out.println("We need 4 4d points");
					for (int i = 0; i < 4; i++) {
						Point4d p = readPoint4d();
						pts[i] = p;
					}

					plane = new Plane4d(pts[0], pts[1], pts[2], pts[3]);
				} else { // mode == 2
					out.println("We need Point4d");
					Point4d point = readPoint4d();
					out.println("We need Vector4d as Point4d");
					Vector4d n = new Vector4d(readPoint4d());

					plane = new Plane4d(point, n);
				}
			}
			Panel4d canvas = new Panel4d();
			canvas.createAndShowGUI();
			if (plane != null) {
				canvas.pointRotator.add(new Segment4d(new Point4d(0, 0, 0, 0),
						plane.getNormal(), Colors.NORMAL_COLOR));
				canvas.planeNormal = plane.getNormal();
				canvas.pointRotator.addAll(makeCut(plane, cube));
			}
			canvas.pointRotator.addAll(cube.getSegments());
			canvas.frame.repaint();

		} finally {
			in.close();
			out.close();
		}
	}

	static Set<Segment4d> makeCut(Plane4d cut, Cube4d cube) {
		Set<Point4d> cutPoints = new TreeSet<Point4d>();
		for (int i = 0; i < 16; i++) {
			for (int j = i + 1; j < 16; j++) {
				if (!cube.getHasEdge()[i][j]) {
					continue;
				}
				Segment4d e = new Segment4d(cube.getVertices()[i],
						cube.getVertices()[j]);
				ArrayList<Point4d> inter = e.intersectWithPlane(cut);
				cutPoints.addAll(inter);
			}
		}

		System.out.println("=======CUT POINTS=====");
		for (Point4d p : cutPoints) {
			System.out.println(p);
		}
		System.out.println("======================");

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
								if (Geometry.compareEps(p.getX(), q.getX()) == 0) {
									sameness++;
								}
								if (Geometry.compareEps(p.getY(), q.getY()) == 0) {
									sameness++;
								}
								if (Geometry.compareEps(p.getZ(), q.getZ()) == 0) {
									sameness++;
								}
								if (Geometry.compareEps(p.getW(), q.getW()) == 0) {
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
		return cutEdges;
	}
}
