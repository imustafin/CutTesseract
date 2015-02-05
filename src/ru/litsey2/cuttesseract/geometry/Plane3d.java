package ru.litsey2.cuttesseract.geometry;

public class Plane3d {

	@Override
	public String toString() {
		return "Plane3d [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + "]";
	}

	/**
	 * The <code>a</code> coefficient in the <code>ax + by + cz + d = 0</code>
	 * plane equation
	 */
	final double a;
	/**
	 * The <code>b</code> coefficient in the <code>ax + by + cz + d = 0</code>
	 * plane equation
	 */
	final double b;
	/**
	 * The <code>c</code> coefficient in the <code>ax + by + cz + d = 0</code>
	 * plane equation
	 */
	final double c;
	/**
	 * The <code>d</code> coefficient in the <code>ax + by + cz + d = 0</code>
	 * plane equation
	 */
	final double d;

	/**
	 * Constructs plane by the <code>ax + by + cz + d = 0</code> plane equation
	 * coefficients
	 * 
	 * @param a
	 *            the <code>a</code> coefficient
	 * @param b
	 *            the <code>b</code> coefficient
	 * @param c
	 *            the <code>c</code> coefficient
	 * @param d
	 *            the <code>d</code> coefficient
	 */
	public Plane3d(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	/**
	 * Constructs plane by three points
	 * 
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the second point
	 * @param p3
	 *            the third point
	 */
	public Plane3d(Point3d p1, Point3d p2, Point3d p3) {
		double[] cfs = Formulas.plane3dCoefficients(p1, p2, p3);
		a = cfs[0];
		b = cfs[1];
		c = cfs[2];
		d = cfs[3];
	}

	/**
	 * 
	 * @return plane normal
	 */
	Vector3d getNormal() {
		Vector3d v = new Vector3d(new Point3d(a, b, c));
		double l = v.length();
		return new Vector3d(new Point3d(a / l, b / l, c / l));
	}
}