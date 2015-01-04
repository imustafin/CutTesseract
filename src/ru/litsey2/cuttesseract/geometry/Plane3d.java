package ru.litsey2.cuttesseract.geometry;

public class Plane3d {

	@Override
	public String toString() {
		return "Plane3d [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d
				+ "]";
	}

	final double a;
	final double b;
	final double c;
	final double d;

	public Plane3d(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public Plane3d(Point3d p1, Point3d p2, Point3d p3) {
		double[] cfs = Formulas.plane3dCoefficients(p1, p2, p3);
		a = cfs[0];
		b = cfs[1];
		c = cfs[2];
		d = cfs[3];
	}

	Vector3d getNormal() {
		Vector3d v = new Vector3d(new Point3d(a, b, c));
		double l = v.length();
		return new Vector3d(new Point3d(a / l, b / l, c / l));
	}
}