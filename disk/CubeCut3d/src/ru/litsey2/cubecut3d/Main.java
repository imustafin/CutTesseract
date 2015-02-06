package ru.litsey2.cubecut3d;

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import ru.litsey2.cubecut3d.MyGeom.Cube3d;
import ru.litsey2.cubecut3d.MyGeom.Plane3d;
import ru.litsey2.cubecut3d.MyGeom.Point3d;
import ru.litsey2.cubecut3d.MyGeom.Segment3d;

public class Main {

	static final Color CUBE_COLOR = Color.BLACK;
	static final Color CUT_COLOR = Color.MAGENTA;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		in.useLocale(Locale.US);
		PrintStream out = new PrintStream(System.out);
		Draw3d canvas = new Draw3d();
		canvas.createAndShowGUI();
		// canvas.frame.setVisible(false);
		try {
			out.println("We need the Cut and the Cube");
			out.println("The Cut is Plane3d. Input 3 Points3d (x1 y1 z1 x2 y2 z2 x3 y3 z3)...");
			Point3d p1 = new Point3d(in.nextDouble(), in.nextDouble(),
					in.nextDouble());
			Point3d p2 = new Point3d(in.nextDouble(), in.nextDouble(),
					in.nextDouble());
			Point3d p3 = new Point3d(in.nextDouble(), in.nextDouble(),
					in.nextDouble());
			Plane3d cut = new Plane3d(p1, p2, p3);
			out.println(cut);
			Point3d[] vertices = { new Point3d(-1, -1, -1), new Point3d(-1, -1, 1),
					new Point3d(-1, 1, -1), new Point3d(1, -1, -1),
					new Point3d(-1, 1, 1), new Point3d(1, -1, 1),
					new Point3d(1, 1, -1), new Point3d(1, 1, 1) };
			boolean[][] hasEdge = new boolean[8][8];
			Set<Segment3d> cubeEdges = new TreeSet<Segment3d>();
			for (int i = 0; i < vertices.length; i++) {
				for (int j = i + 1; j < vertices.length; j++) {
					Point3d a = vertices[i];
					Point3d b = vertices[j];
					int sameness = 0;
					if (a.x == b.x) {
						sameness++;
					}
					if (a.y == b.y) {
						sameness++;
					}
					if (a.z == b.z) {
						sameness++;
					}
					if (sameness == 2) {
						hasEdge[i][j] = true;
						hasEdge[j][i] = true;
						cubeEdges.add(new Segment3d(a, b, CUBE_COLOR));
					} else {
						hasEdge[i][j] = false;
						hasEdge[j][i] = false;
					}
				}
			}
			
			Cube3d cube = new Cube3d(vertices, hasEdge);
			out.println("Have data, will compute");
			Set<Segment3d> ans = makeCut(cut, cube);
			for (Segment3d p : ans) {
				out.println(p);
			}
			if (ans.size() == 0) {
				out.println("NO POINTS!1!!!!!11!");
			}
			canvas.segments.addAll(ans);
			canvas.segments.addAll(cubeEdges);
			canvas.frame.repaint();
		} finally {
			in.close();
			out.close();
		}
	}

	static Set<Segment3d> makeCut(Plane3d cut, Cube3d cube) {
		Set<Point3d> cutPoints = new TreeSet<Point3d>();
		for (int i = 0; i < 8; i++) {
			for (int j = i + 1; j < 8; j++) {
				if (!cube.hasEdge[i][j]) {
					continue;
				}
				Segment3d e = new Segment3d(cube.vertices[i], cube.vertices[j]);
				ArrayList<Point3d> inter = e.intersectWithPlane(cut);
				cutPoints.addAll(inter);
			}
		}

		System.out.println("=======CUT POINTS=====");
		for (Point3d p : cutPoints) {
			System.out.println(p);
		}
		System.out.println("======================");

		Set<Segment3d> cutEdges = new TreeSet<Segment3d>();
		for(Point3d a : cutPoints) {
			for(Point3d b : cutPoints) {
				if(a == b) {
					continue;
				}
				if ((a.x == b.x && (a.x == -1 || a.x == 1))
						|| (a.y == b.y && (a.y == -1 || a.y == 1))
						|| (a.z == b.z && (a.z == -1 || a.z == 1))) {
					cutEdges.add(new Segment3d(a, b, CUT_COLOR));
				}
			}
		}
		
		return cutEdges;
	}
}
