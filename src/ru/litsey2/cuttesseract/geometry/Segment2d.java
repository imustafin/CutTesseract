package ru.litsey2.cuttesseract.geometry;

import java.awt.Color;

public class Segment2d implements Comparable<Segment2d> {
	/**
	 * The first point of the segment. It is not greater than the second point.
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public final Point2d first;
	/**
	 * The second point of the segment. It is not less than the first point.
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public final Point2d second;
	/**
	 * The color of the segment
	 */
	public final Color color;


	@Override
	public String toString() {
		return "Segment2d [first=" + first + ", second=" + second + ", color="
				+ color + ", a=" + a + ", b=" + b + ", c=" + c + "]";
	}

	final double a;
	final double b;
	final double c;

	/**
	 * Constructs segment with the specified two points and color. The second
	 * point should not be less than the first point if it is not so then they
	 * are swapped.
	 * 
	 * @param p
	 *            first point
	 * @param q
	 *            second point
	 * @param color
	 *            color of segment
	 * 
	 * @see Point4d#compareTo(Point4d)
	 */
	public Segment2d(Point2d p, Point2d q, Color color) {
		if (p.compareTo(q) <= 0) {
			this.first = p;
			this.second = q;
		} else {
			this.first = q;
			this.second = p;
		}
		this.color = color;
		a = first.y - second.y;
		b = second.x - first.x;
		c = -(a * first.x + b * first.y);
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
	public Segment2d(Point2d a, Point2d b) {
		this(a, b, Color.BLACK);
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
	public int compareTo(Segment2d z) {
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

	public Point2d intersectionPoint(Segment2d s) {

		double a1 = a;
		double b1 = b;
		double c1 = -c;

		double a2 = s.a;
		double b2 = s.b;
		double c2 = -s.c;

		double d = a1 * b2 - b1 * a2;
		double x = (c1 * b2 - c2 * b1) / d;
		double y = (a1 * c2 - a2 * c1) / d;

		boolean in1 = false;
		boolean in2 = false;

		double r1;
		if (Geometry.compareEps(second.x, first.x) != 0) {
			r1 = (x - first.x) / (second.x - first.x);
		} else {
			r1 = (y - first.y) / (second.y - first.y);
		}
		if (Geometry.compareEps(0, r1) <= 0 && Geometry.compareEps(r1, 1) <= 0) {
			in1 = true;
		}

		double r2;
		if (Geometry.compareEps(s.second.x, s.first.x) != 0) {
			r2 = (x - s.first.x) / (s.second.x - s.first.x);
		} else {
			r2 = (y - s.first.y) / (s.second.y - s.first.y);
		}
		if (Geometry.compareEps(0, r2) <= 0 && Geometry.compareEps(r2, 1) <= 0) {
			in2 = true;
		}

		if (!(in1 && in2)) {
			return null;
		}

		return new Point2d(x, y);
	}

	public double length() {
		double dx = second.x - first.x;
		double dy = second.y - first.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

}