package ru.litsey2.cuttesseract.geometry;

public class Point2d implements Comparable<Point2d> {

	/**
	 * The <code>x</code> coordinate of this point
	 */
	public final double x;
	/**
	 * The <code>y</code> coordinate of this point
	 */
	public final double y;

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/**
	 * Constructs point by the specified <code>x</code> and <code>y</code>
	 * coordinates
	 * 
	 * @param x
	 *            specified <code>x</code> coordinate
	 * @param y
	 *            specified <code>y</code> coordinate
	 */
	public Point2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Compares this point to other point.
	 * <p>
	 * Points are first compared by the
	 * <code>x</code> coordinate then by the <code>y</code> coordinate
	 * 
	 * @param b
	 *            the point to be compared
	 */
	@Override
	public int compareTo(Point2d b) {
		if (Geometry.compareEps(x, b.x) == 0) {
			if (Geometry.compareEps(y, b.y) == 0) {
				return 0;
			} else {
				return Double.compare(y, b.y);
			}
		} else {
			return Double.compare(x, b.x);
		}
	}

}