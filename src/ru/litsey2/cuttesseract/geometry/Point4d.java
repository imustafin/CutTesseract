package ru.litsey2.cuttesseract.geometry;

public class Point4d implements Comparable<Point4d> {
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
	 * The <code>w</code> coordinate of this point
	 */
	protected final double w;

	/**
	 * The point with coordinates <code>(0, 0, 0, 0)</code>
	 */
	public static final Point4d ZERO = new Point4d(0, 0, 0, 0);

	/**
	 * Constructs point with the specified coordinates
	 * 
	 * @param x
	 *            the specified <code>x</code> coordinate
	 * @param y
	 *            the specified <code>y</code> coordinate
	 * @param z
	 *            the specified <code>z</code> coordinate
	 * @param w
	 *            the specified <code>w</code> coordinate
	 */
	public Point4d(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	@Override
	public String toString() {
		double cx = Geometry.compareEps(0, x) == 0 ? 0 : x;
		double cy = Geometry.compareEps(0, y) == 0 ? 0 : y;
		double cz = Geometry.compareEps(0, z) == 0 ? 0 : z;
		double cw = Geometry.compareEps(0, w) == 0 ? 0 : w;
		return "(" + cx + ", " + cy + ", " + cz + ", " + cw + ")";
	}

	/**
	 * Constructs point as a copy of other point
	 * 
	 * @param a
	 *            point to copy
	 */
	Point4d(Point4d a) {
		this(a.x, a.y, a.z, a.w);
	}

	/**
	 * Compares this point to other point.
	 * <p>
	 * Points are first compared by the <code>x</code> coordinate, then by the
	 * <code>y</code> coordinate, then by the <code>z</code> coordinate, then by
	 * the <code>w</code> coordinate
	 * 
	 * @param b
	 *            the point to be compared
	 */
	@Override
	public int compareTo(Point4d b) {
		if (Geometry.compareEps(getX(), b.getX()) == 0) {
			if (Geometry.compareEps(getY(), b.getY()) == 0) {
				if (Geometry.compareEps(getZ(), b.getZ()) == 0) {
					if (Geometry.compareEps(getW(), b.getW()) == 0) {
						return 0;
					} else {
						return Double.compare(getW(), b.getW());
					}
				} else {
					return Double.compare(getZ(), b.getZ());
				}
			} else {
				return Double.compare(getY(), b.getY());
			}
		} else {
			return Double.compare(getX(), b.getX());
		}
	}

	/**
	 * Checks if four points are <i>cospatial</i>. If four points are
	 * <i>cospatial</i> they do not define a hyperplane.
	 * 
	 * @param a first point
	 * @param b second point
	 * @param c third point
	 * @param d fourth point
	 * @return true if the points are cospatial, false otherwise
	 */
	static boolean coSpatial(Point4d a, Point4d b, Point4d c, Point4d d) {
		double[][] mt = new double[4][4];
		Point4d[] ar = { a, b, c, d };
		for (int i = 0; i < 4; i++) {
			Point4d p = ar[i];
			mt[i][0] = p.getX();
			mt[i][1] = p.getY();
			mt[i][2] = p.getZ();
			mt[i][3] = p.getW();
		}
		return (Geometry.compareEps(new Matrix(mt).getDeterminant(), 0) == 0);
	}

	/**
	 * Returns distance from this to the other point
	 * 
	 * @param b
	 *            point to compute the distance to
	 * @return distance to the other point
	 */
	public double distance(Point4d b) {
		double dx = b.getX() - getX();
		double dy = b.getY() - getY();
		double dz = b.getZ() - getZ();
		double dw = b.getW() - getW();
		return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
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
	public double applyToPlane(Plane4d p) {
		double res = p.a * getX() + p.b * getY() + p.c * getZ() + p.d * getW()
				+ p.e;
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

	public double getW() {
		return w;
	}

}