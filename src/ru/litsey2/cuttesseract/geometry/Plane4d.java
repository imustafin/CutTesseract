package ru.litsey2.cuttesseract.geometry;

public class Plane4d {

	/**
	 * The <code>a</code> coefficient in the
	 * <code>ax + by + cz + dw + e= 0</code> plane equation
	 */
	final double a;
	/**
	 * The <code>b</code> coefficient in the
	 * <code>ax + by + cz + dw + e= 0</code> plane equation
	 */
	final double b;
	/**
	 * The <code>c</code> coefficient in the
	 * <code>ax + by + cz + dw + e= 0</code> plane equation
	 */
	final double c;
	/**
	 * The <code>d</code> coefficient in the
	 * <code>ax + by + cz + dw + e= 0</code> plane equation
	 */
	final double d;
	/**
	 * The <code>e</code> coefficient in the
	 * <code>ax + by + cz + dw + e= 0</code> plane equation
	 */
	final double e;

	/**
	 * Constructs plane by four points
	 * 
	 * @param p1
	 *            the first point
	 * @param p2
	 *            the first point
	 * @param p3
	 *            the first point
	 * @param p4
	 *            the first point
	 */
	public Plane4d(Point4d p1, Point4d p2, Point4d p3, Point4d p4) {
		double[] cfs = Formulas.plane4dCoefficients(p1, p2, p3, p4);
		a = cfs[0];
		b = cfs[1];
		c = cfs[2];
		d = cfs[3];
		e = cfs[4];
	}

	/**
	 * Constructs plane by point and plane normal
	 * 
	 * @param p
	 *            point
	 * @param n
	 *            plane normal
	 */
	public Plane4d(Point4d p, Vector4d n) {
		a = n.x;
		b = n.y;
		c = n.z;
		d = n.w;
		e = -(a * p.x + b * p.y + c * p.z + d * p.w);
	}

	@Override
	public String toString() {
		return "Plane4d [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d
				+ ", e=" + e + "]";
	}

	/**
	 * 
	 * @return plane normal vector
	 */
	public Vector4d getNormal() {
		return new Vector4d(a, b, c, d).getNormalized();
	}

}