package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;
import java.util.ArrayList;

public class Segment4d implements Comparable<Segment4d> {
	private final Point4d a;
	private final Point4d b;
	protected final Color color;

	public Segment4d(Point4d a, Point4d b, Color c) {
		if (a.compareTo(b) < 0) {
			this.a = a;
			this.b = b;
		} else {
			this.a = b;
			this.b = a;
		}
		this.color = c;
	}

	public Segment4d(Point4d a, Point4d b) {
		this(a, b, Color.BLACK);
	}

	@Override
	public int compareTo(Segment4d z) {
		if (getA().compareTo(z.getA()) == 0) {
			if (getB().compareTo(z.getB()) == 0) {
				return Integer.compare(this.color.getRGB(), z.color.getRGB());
			} else {
				return getB().compareTo(z.getB());
			}
		} else {
			return getA().compareTo(z.getA());
		}
	}

	public ArrayList<Point4d> intersectWithPlane(Plane4d p) {
		ArrayList<Point4d> ans = new ArrayList<Point4d>();

		double res1 = getA().compareToPlane(p);
		double res2 = getB().compareToPlane(p);
		int sign1 = Geometry.compareEps(res1, 0);
		int sign2 = Geometry.compareEps(res2, 0);

		if (sign1 == 0) {
			ans.add(getA());
		}
		if (sign2 == 0) {
			ans.add(getB());
		}

		if (ans.size() > 0) {
			return ans;
		}

		if (sign1 == sign2) {
			return ans;
		}

		Vector4d ab = new Vector4d(getA(), getB());
		/*
		 * t: в параметрическом уравенении прямой параметр задающий точку
		 * пересечения с плоскостью.
		 */
		double t = (-p.a * getA().getX() - p.b * getA().getY() - p.c
				* getA().getZ() - p.d * getA().getW() - p.e)
				/ (p.a * ab.getX() + p.b * ab.getY() + p.c * ab.getZ() + p.d
						* ab.getW());
		Point4d m = new Point4d(getA().getX() + ab.getX() * t, getA().getY()
				+ ab.getY() * t, getA().getZ() + ab.getZ() * t, getA().getW()
				+ ab.getW() * t);
		ans.add(m);
		return ans;
	}

	public Color getColor() {
		return color;
	}

	public Point4d getA() {
		return a;
	}

	public Point4d getB() {
		return b;
	}

}