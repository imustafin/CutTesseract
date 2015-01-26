package ru.litsey2.cuttesseract;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFrame;

import ru.litsey2.cuttesseract.geometry.Cube4d;
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
					+ "2 for point and normal vector\n" + "4 for 4 points4d");

			int mode = in.nextInt();

			Plane4d plane;

			switch (mode) {
			case (4):
				Point4d[] pts = new Point4d[4];

				out.println("We need 4 4d points");
				for (int i = 0; i < 4; i++) {
					Point4d p = readPoint4d();
					pts[i] = p;
				}
				plane = new Plane4d(pts[0], pts[1], pts[2], pts[3]);
				break;
			case (2):
				out.println("We need Point4d");
				Point4d point = readPoint4d();
				out.println("We need Vector4d as Point4d");
				Vector4d n = new Vector4d(readPoint4d());

				plane = new Plane4d(point, n);
				break;
			case (-1) :
				out.println("A secret debug mode!!");
				out.println("2");
				out.println("0 0 0 0");
				out.println("1 1 1 1");
				plane = new Plane4d(new Point4d(0, 0, 0, 0), new Vector4d(1, 1, 1, 1));
				break;
			default:
				throw new IllegalArgumentException("No such mode " + mode);
			}
			MainFrame mainFrame = new MainFrame();
			mainFrame.pointRotator.add(new Segment4d(new Point4d(0, 0, 0, 0),
					plane.getNormal(), Colors.NORMAL_COLOR));
			mainFrame.planeNormal = plane.getNormal();
			mainFrame.pointRotator.addAll(makeCut(plane, cube));
			mainFrame.pointRotator.addAll(cube.getSegments());
			mainFrame.repaint();

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

		return cutEdges;
	}
}
