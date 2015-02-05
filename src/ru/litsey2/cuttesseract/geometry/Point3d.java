package ru.litsey2.cuttesseract.geometry;

public class Point3d implements Comparable<Point3d> {
	/**
	 * The <code>x</code> coordinate of this point
	 */
	protected final double x;
	/**
	 * The <code>y</code> coordinate of this point
	 */
	protected final double y;
	/**
	 * The <code>z</code> coordinate of this point
	 */
	protected final double z;

	/**
	 * The point with coordinates <code>(0, 0, 0)</code>
	 */
	public static final Point3d ZERO = new Point3d(0, 0, 0);

	/**
	 * Constructs point as a copy of other point
	 * 
	 * @param a
	 *            point to copy
	 */
	public Point3d(Point3d a) {
		this(a.x, a.y, a.z);
	}

	/**
	 * Constructs point with the specified coordinates
	 * 
	 * @param x
	 *            the specified <code>x</code> coordinate
	 * @param y
	 *            the specified <code>y</code> coordinate
	 * @param z
	 *            the specified <code>z</code> coordinate
	 */
	Point3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Applies this point's coordinates to the plane's
	 * <code>ax + by + cz + d</code> expression
	 * 
	 * @param p
	 *            plane to apply coordinates to
	 * @return result of <code>ax + by + cz + d</code>
	 * 
	 */
	double applyToPlane(Plane3d p) {
		double res = p.a * x + p.b * y + p.c * z + p.d;
		return res;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	/**
	 * Returns distance from this to the other point
	 * 
	 * @param b
	 *            point to compute the distance to
	 * @return distance to the other point
	 */
	double distance(Point3d b) {
		Point3d a = this;
		double dx = a.x - b.x;
		double dy = a.y - b.y;
		double dz = a.z - b.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	/**
	 * Compares this point to other point.
	 * <p>
	 * Points are first compared by the
	 * <code>x</code> coordinate then by the <code>y</code> coordinate then by
	 * the <code>z</code> coordinate
	 * 
	 * @param b
	 *            the point to be compared
	 */
	@Override
	public int compareTo(Point3d b) {
		if (Geometry.compareEps(x, b.x) == 0) {
			if (Geometry.compareEps(y, b.y) == 0) {
				if (Geometry.compareEps(z, b.z) == 0) {
					return 0;
				} else {
					return Double.compare(z, b.z);
				}
			} else {
				return Double.compare(y, b.y);
			}
		} else {
			return Double.compare(x, b.x);
		}
	}

}