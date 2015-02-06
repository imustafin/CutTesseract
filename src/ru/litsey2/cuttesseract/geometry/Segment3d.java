package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;
import java.util.ArrayList;

public class Segment3d implements Comparable<Segment3d> {
	/**
	 * The first point of the segment. It is not greater than the second point.
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public final Point3d first;
	/**
	 * The second point of the segment. It is not less than the first point.
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public final Point3d second;
	/**
	 * The color of the segment
	 */
	public final Color color;

	@Override
	public String toString() {
		return "[" + first + ", " + second + "]";
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
	 * @param color
	 *            color of segment
	 * 
	 * @see Point3d#compareTo(Point3d)
	 */
	public Segment3d(Point3d a, Point3d b, Color color) {
		this.color = color;
		if (a.compareTo(b) <= 0) {
			this.first = a;
			this.second = b;
		} else {
			this.first = b;
			this.second = a;
		}
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
	public Segment3d(Point3d a, Point3d b) {
		this(a, b, Color.BLACK);
	}

	/**
	 * Intersects this segment with the specified plane.
	 * <p>
	 * There can be zero, one or infinite number of intersection points. If
	 * there is zero points empty list is returned. If there is only one
	 * intersection point then list with only one point is returned. If there
	 * are more than one intersection points list with this segment's
	 * <code>{@link first}</code> and <code>{@link second}</code> points is returned.
	 * 
	 * @param p
	 *            the plane to intersect with
	 * @return list of intersection points
	 */
	ArrayList<Point3d> intersectWithPlane(Plane3d p) {
		ArrayList<Point3d> ans = new ArrayList<Point3d>();
		double res1 = first.applyToPlane(p);
		double res2 = second.applyToPlane(p);
		int sign1 = Geometry.compareEps(res1, 0);
		int sign2 = Geometry.compareEps(res2, 0);

		if (sign1 == 0) {
			ans.add(first);
		}
		if (sign2 == 0) {
			ans.add(second);
		}

		if (ans.size() > 0) {
			return ans;
		}

		if (sign1 == sign2) {
			return ans;
		}

		Vector3d ab = new Vector3d(first, second);
		/*
		 * t: plane intersection point
		 */
		double t = (-p.d - p.a * first.getX() - p.b * first.getY() - p.c
				* first.getZ())
				/ (p.a * ab.getX() + p.b * ab.getY() + p.c * ab.getZ());
		Point3d m = new Point3d(first.getX() + ab.getX() * t, first.getY()
				+ ab.getY() * t, first.getZ() + ab.getZ() * t);
		ans.add(m);
		return ans;
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
	public int compareTo(Segment3d z) {
		if (first.compareTo(z.first) == 0) {
			if (second.compareTo(z.second) == 0) {
				return Integer.compare(this.color.getRGB(), z.color.getRGB());
			} else {
				return second.compareTo(z.second);
			}
		} else {
			return first.compareTo(z.first);
		}
	}
}