package ru.litsey2.cuttesseract.geometry;

public class Plane4d {
	final double a;
	final double b;
	final double c;
	final double d;
	final double e;

	public Plane4d(Point4d p1, Point4d p2, Point4d p3, Point4d p4) {
		double[] cfs = Formulas.plane4dCoefficients(p1, p2, p3, p4);
		a = cfs[0];
		b = cfs[1];
		c = cfs[2];
		d = cfs[3];
		e = cfs[4];
	}

	@Override
	public String toString() {
		return "Plane4d [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d
				+ ", e=" + e + "]";
	}

	public Vector4d getNormal() {
		return new Vector4d(a, b, c, d).getNormalized();
	}

}