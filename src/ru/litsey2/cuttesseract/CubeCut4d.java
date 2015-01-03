package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.print.CancelablePrintJob;

import ru.litsey2.cuttesseract.MyGeom.Cube3d;
import ru.litsey2.cuttesseract.MyGeom.Cube4d;
import ru.litsey2.cuttesseract.MyGeom.Plane3d;
import ru.litsey2.cuttesseract.MyGeom.Plane4d;
import ru.litsey2.cuttesseract.MyGeom.Point3d;
import ru.litsey2.cuttesseract.MyGeom.Point4d;
import ru.litsey2.cuttesseract.MyGeom.Segment3d;
import ru.litsey2.cuttesseract.MyGeom.Segment4d;

public class CubeCut4d {

	static final Color CUBE_COLOR = Color.BLACK;
	static final Color CUT_COLOR = Color.MAGENTA;
	static final Color NORMAL_COLOR = Color.GRAY;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		in.useLocale(Locale.US);
		PrintStream out = new PrintStream(System.out);
		CubeCut4dFrame canvas = new CubeCut4dFrame();
		canvas.createAndShowGUI();

		Cube4d cube = new Cube4d(1, CUBE_COLOR);
		
		
		try {

			Point4d[] pts = new Point4d[4];

			out.println("We need 4 4d points");
			for (int i = 0; i < 4; i++) {
				try {
					int x = in.nextInt();
					int y = in.nextInt();
					int z = in.nextInt();
					int w = in.nextInt();
					pts[i] = new Point4d(x, y, z, w);
				} catch (Exception e) {
					i--;
					e.printStackTrace();
					out.println("Try again plz. Point #" + i);
				}
			}
			
			Plane4d plane = new Plane4d(pts[0], pts[1], pts[2], pts[3]);
			canvas.segments.add(new Segment4d(new Point4d(0, 0, 0, 0), plane.getNormal().p));
			canvas.segments.addAll(makeCut(plane, cube));
			canvas.segments.addAll(cube.segments);
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
				if (!cube.hasEdge[i][j]) {
					continue;
				}
				Segment4d e = new Segment4d(cube.vertices[i], cube.vertices[j]);
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
				if(a.x == b.x && (a.x == 1 || a.x == -1)){
					sameness++;
				}
				if(a.y == b.y && (a.y == 1 || a.y == -1)) {
					sameness++;
				}
				if(a.z == b.z && (a.z == 1 || a.z == -1)) {
					sameness++;
				}
				if(a.w == b.w && (a.w == 1 || a.w == -1)) {
					sameness++;
				}
				if(sameness == 2) {
					cutEdges.add(new Segment4d(a, b, CUT_COLOR));
				}
			}
		}

		return cutEdges;
	}
}
