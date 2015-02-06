package ru.litsey2.cuttesseract.geometry;


public class Point3d implements Comparable<Point3d> {
	protected final double x;
	protected final double y;
	protected final double z;

	public static final Point3d ZERO = new Point3d(0, 0, 0);

	public Point3d(Point3d a) {
		this(a.x, a.y, a.z);
	}

	Point3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	double compareToPlane(Plane3d p) {
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