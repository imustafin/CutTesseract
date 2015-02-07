package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;
import java.util.ArrayList;

public class Segment4d implements Comparable<Segment4d> {
	/**
	 * The first point of the segment. It is not greater than the second point.
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public final Point4d a;
	/**
	 * The second point of the segment. It is not less than the first point.
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public final Point4d b;
	/**
	 * The color of the segment
	 */
	public final Color color;

	@Override
	public String toString() {
		return "Segment4d [a=" + a + ", b=" + b + ", color=" + color + "]";
	}

	/**
	 * Constructs segment with the specified two points and color. The second
	 * point should not be less than the first point if it is not so then they
	 * are swapped.
	 * 
	 * @param a
	 *            first point
	 * @param b
	 *            second point
	 * @param c
	 *            color of segment
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
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

	/**
	 * Constructs segment with the specified two points only. The color is
	 * considered to be {@link Color#BLACK} The second point should not be less
	 * than the first point if it is not so then they are swapped.
	 * 
	 * @param a
	 *            first point
	 * @param b
	 *            second point
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public Segment4d(Point4d a, Point4d b) {
		this(a, b, Color.BLACK);
	}

	/**
	 * Constructs this segment as a copy of the specified segment
	 * 
	 * @param s
	 *            a segment to copy
	 */
	public Segment4d(Segment4d s) {
		this.a = s.a;
		this.b = s.b;
		this.color = s.color;
	}

	/**
	 * Compares this segment to the other segment.
	 * <p>
	 * Segments are first compared by the <code>first</code> point then by the
	 * <code>second</code> point then by the color {@link Color#getRGB()}
	 * 
	 * @param z
	 *            the segment to compare
	 */
	@Override
	public int compareTo(Segment4d z) {
		if (a.compareTo(z.a) == 0) {
			if (b.compareTo(z.b) == 0) {
				return Integer.compare(color.getRGB(), z.color.getRGB());
			} else {
				return b.compareTo(z.b);
			}
		} else {
			return a.compareTo(z.a);
		}
	}

	public ArrayList<Point4d> intersectWithPlane(Plane4d p) {
		ArrayList<Point4d> ans = new ArrayList<Point4d>();

		double res1 = a.applyToPlane(p);
		double res2 = b.applyToPlane(p);
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

		Vector4d ab = new Vector4d(a, b);
		/*
		 * t: в параметрическом уравенении прямой параметр задающий точку
		 * пересечения с плоскостью.
		 */
		double t = (-p.a * a.x - p.b * a.y - p.c * a.z - p.d * a.w - p.e)
				/ (p.a * ab.x + p.b * ab.y + p.c * ab.z + p.d * ab.w);
		Point4d m = new Point4d(a.x + ab.x * t, a.y + ab.y * t, a.z + ab.z * t,
				a.w + ab.w * t);
		ans.add(m);
		return ans;
	}

}