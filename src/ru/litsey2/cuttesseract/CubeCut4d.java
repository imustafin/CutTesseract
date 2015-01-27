package ru.litsey2.cuttesseract;

import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

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
					+ "0 to skip input");

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
			case (0):
				plane = null;
				break;
			default:
				throw new IllegalArgumentException("No such mode " + mode);
			}
			MainFrame mainFrame = new MainFrame();
			if (plane != null) {
				mainFrame.pointRotator.add(new Segment4d(
						new Point4d(0, 0, 0, 0), plane.getNormal(),
						Colors.NORMAL_COLOR));
				mainFrame.planeNormal = plane.getNormal();
				mainFrame.pointRotator.addAll(Geometry.makeCut(plane, cube));
				mainFrame.pointRotator.addAll(cube.getSegments());
				mainFrame.repaint();
			}

		} finally {
			in.close();
			out.close();
		}
	}
}
