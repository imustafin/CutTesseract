package ru.litsey2.cuttesseract.geometry;


public class Point4d implements Comparable<Point4d> {
	protected final double x;
	protected final double y;
	protected final double z;
	protected final double w;

	static final Point4d ZERO = new Point4d(0, 0, 0, 0);

	public Point4d(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	@Override
	public String toString() {
		return "(" + getX() + ", " + getY() + ", " + getZ() + ", " + getW() + ")";
	}

	Point4d(Point4d a) {
		this(a.getX(), a.getY(), a.getZ(), a.getW());
	}

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

	public double distance(Point4d b) {
		double dx = b.getX() - getX();
		double dy = b.getY() - getY();
		double dz = b.getZ() - getZ();
		double dw = b.getW() - getW();
		return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
	}

	public double compareToPlane(Plane4d p) {
		double res = p.a * getX() + p.b * getY() + p.c * getZ() + p.d * getW() + p.e;
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