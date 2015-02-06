package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;
import java.util.ArrayList;

public class Segment3d implements Comparable<Segment3d> {
	protected final Point3d a;
	protected final Point3d b;

	final Color color;

	@Override
	public String toString() {
		return "[" + a + ", " + b + "]";
	}

	public Segment3d(Point3d a, Point3d b, Color color) {
		this.color = color;
		if (a.compareTo(b) < 0) {
			this.a = a;
			this.b = b;
		} else {
			this.a = b;
			this.b = a;
		}
	}

	public Segment3d(Point3d a, Point3d b) {
		this(a, b, Color.BLACK);
	}

	ArrayList<Point3d> intersectWithPlane(Plane3d p) {
		ArrayList<Point3d> ans = new ArrayList<Point3d>();
		double res1 = a.compareToPlane(p);
		double res2 = b.compareToPlane(p);
		int sign1 = Geometry.compareEps(res1, 0);
		int sign2 = Geometry.compareEps(res2, 0);

		if (sign1 == 0) {
			ans.add(a);
		}
		if (sign2 == 0) {
			ans.add(b);
		}

		if (ans.size() > 0) {
			return ans;
		}

		if (sign1 == sign2) {
			return ans;
		}

		Vector3d ab = new Vector3d(a, b);
		/*
		 * t: в параметрическом уравенении прямой параметр задающий точку
		 * пересечения с плоскостью.
		 */
		double t = (-p.d - p.a * a.getX() - p.b * a.getY() - p.c * a.getZ())
				/ (p.a * ab.getX() + p.b * ab.getY() + p.c * ab.getZ());
		Point3d m = new Point3d(a.getX() + ab.getX() * t, a.getY() + ab.getY() * t, a.getZ() + ab.getZ()
				* t);
		ans.add(m);
		return ans;
	}

	@Override
	public int compareTo(Segment3d z) {
		if (a.compareTo(z.a) == 0) {
			if (b.compareTo(z.b) == 0) {
				return Integer.compare(this.color.getRGB(), z.color.getRGB());
			} else {
				return b.compareTo(z.b);
			}
		} else {
			return a.compareTo(z.a);
		}
	}
}