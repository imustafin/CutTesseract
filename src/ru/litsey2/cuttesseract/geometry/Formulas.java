package ru.litsey2.cuttesseract.geometry;


public class Formulas {

	static Vector3d findK(Vector3d a, Vector3d b) {
		return new Vector3d(a.getY() * b.getZ() - b.getY() * a.getZ(), b.getX() * a.getZ() - a.getX() * b.getZ(), a.getX()
				* b.getY() - b.getX() * a.getY());
	}

	static Point3d findN(Plane3d p, Point3d o) {

		double y0 = o.getY();
		double x0 = o.getX();
		double z0 = o.getZ();

		double a = p.a;
		double b = p.b;
		double c = p.c;
		double d = p.d;

		double x = (-c + (Math.pow(b, 2) * c)
				/ (Math.pow(a, 2) + Math.pow(b, 2)) - d + (Math.pow(b, 2) * d)
				/ (Math.pow(a, 2) + Math.pow(b, 2)) + (a * Math.pow(b, 2) * x0)
				/ (Math.pow(a, 2) + Math.pow(b, 2)) - (Math.pow(a, 2) * b * y0)
				/ (Math.pow(a, 2) + Math.pow(b, 2)) - (b * Math.sqrt(Math.pow(2
				* b * c + 2 * b * d + 2 * a * b * x0 - 2 * Math.pow(a, 2) * y0,
				2)
				- 4
				* (Math.pow(a, 2) + Math.pow(b, 2))
				* (Math.pow(c, 2) + 2 * c * d + Math.pow(d, 2) + 2 * a * c * x0
						+ 2 * a * d * x0 + Math.pow(a, 2) * Math.pow(x0, 2)
						+ Math.pow(a, 2) * Math.pow(y0, 2) - 2 * Math.pow(a, 2)
						* z0 + Math.pow(a, 2) * Math.pow(z0, 2))))
				/ (2. * (Math.pow(a, 2) + Math.pow(b, 2))))
				/ a;
		double y = (-2 * b * c - 2 * b * d - 2 * a * b * x0 + 2
				* Math.pow(a, 2) * y0 + Math.sqrt(Math.pow(2 * b * c + 2 * b
				* d + 2 * a * b * x0 - 2 * Math.pow(a, 2) * y0, 2)
				- 4
				* (Math.pow(a, 2) + Math.pow(b, 2))
				* (Math.pow(c, 2) + 2 * c * d + Math.pow(d, 2) + 2 * a * c * x0
						+ 2 * a * d * x0 + Math.pow(a, 2) * Math.pow(x0, 2)
						+ Math.pow(a, 2) * Math.pow(y0, 2) - 2 * Math.pow(a, 2)
						* z0 + Math.pow(a, 2) * Math.pow(z0, 2))))
				/ (2. * (Math.pow(a, 2) + Math.pow(b, 2)));
		double z = 1;

		return new Point3d(x, y, z);

	}

	static Point3d newFindM(Plane3d p) {
		double a = p.a;
		double b = p.b;
		double c = p.c;
		double d = p.d;

		if (Geometry.compareEps(d, 0) == 0) {
			return new Point3d(0, 0, 0);
		}

		double x = (-d * a) / (a * a + b * b + c * c);
		double y = (-d * b) / (a * a + b * b + c * c);
		double z = (-d * c) / (a * a + b * b + c * c);

		return new Point3d(x, y, z);
	}

	static Point3d newFindN(Plane3d p) {
		double a = p.a;
		double b = p.b;
		double c = p.c;
		double d = p.d;

		if (Geometry.compareEps(d, 0) == 0) {
			if (Geometry.compareEps(a, 0) != 0) {
				return new Point3d((-b - c) / a, 1, 1);
			}
			if (Geometry.compareEps(b, 0) != 0) {
				return new Point3d(1, (-a - c) / b, 1);
			}
			return new Point3d(1, 1, (-a - b) / c);
		}

		double x = (-d * a * 2) / (a * a * 2 + b * b * 3 + c * c * 4);
		double y = (-d * b * 3) / (a * a * 2 + b * b * 3 + c * c * 4);
		double z = (-d * c * 4) / (a * a * 2 + b * b * 3 + c * c * 4);

		return new Point3d(x, y, z);
	}

	/**
	 * Returns coefficients from plane equation <code>ax + by + cz + d = 0</code>
	 * 
	 * @param p1 first point
	 * @param p2 second point
	 * @param p3 third point
	 * @return array <code>{a, b, c, d}</code> 
	 */
	static double[] plane3dCoefficients(Point3d p1, Point3d p2, Point3d p3) {
		double x1 = p1.getX();
		double x2 = p2.getX();
		double x3 = p3.getX();

		double y1 = p1.getY();
		double y2 = p2.getY();
		double y3 = p3.getY();

		double z1 = p1.getZ();
		double z2 = p2.getZ();
		double z3 = p3.getZ();

		double a = (-y2) * z1 + y3 * z1 + y1 * z2 - y3 * z2 - y1 * z3 + y2 * z3;
		double b = x2 * z1 - x3 * z1 - x1 * z2 + x3 * z2 + x1 * z3 - x2 * z3;
		double c = (-x2) * y1 + x3 * y1 + x1 * y2 - x3 * y2 - x1 * y3 + x2 * y3;
		double d = x3 * y2 * z1 - x2 * y3 * z1 - x3 * y1 * z2 + x1 * y3 * z2
				+ x2 * y1 * z3 - x1 * y2 * z3;
		return new double[] { a, b, c, d };
	}

	/**
	 * Returns coefficients from plane equation <code>ax + by + cz + dw + e = 0</code>
	 * 
	 * @param p1 first point
	 * @param p2 second point
	 * @param p3 third point
	 * @param p4 fourth point
	 * @return array <code>{a, b, c, d, e}</code> 
	 */
	static double[] plane4dCoefficients(Point4d p1, Point4d p2, Point4d p3,
			Point4d p4) {

		double x1 = p1.getX();
		double x2 = p2.getX();
		double x3 = p3.getX();
		double x4 = p4.getX();

		double y1 = p1.getY();
		double y2 = p2.getY();
		double y3 = p3.getY();
		double y4 = p4.getY();

		double z1 = p1.getZ();
		double z2 = p2.getZ();
		double z3 = p3.getZ();
		double z4 = p4.getZ();

		double w1 = p1.getW();
		double w2 = p2.getW();
		double w3 = p3.getW();
		double w4 = p4.getW();

		double a = w3 * y2 * z1 - w4 * y2 * z1 - w2 * y3 * z1 + w4 * y3 * z1
				+ w2 * y4 * z1 - w3 * y4 * z1 - w3 * y1 * z2 + w4 * y1 * z2
				+ w1 * y3 * z2 - w4 * y3 * z2 - w1 * y4 * z2 + w3 * y4 * z2
				+ w2 * y1 * z3 - w4 * y1 * z3 - w1 * y2 * z3 + w4 * y2 * z3
				+ w1 * y4 * z3 - w2 * y4 * z3 - w2 * y1 * z4 + w3 * y1 * z4
				+ w1 * y2 * z4 - w3 * y2 * z4 - w1 * y3 * z4 + w2 * y3 * z4;
		double b = -(w3 * x2 * z1) + w4 * x2 * z1 + w2 * x3 * z1 - w4 * x3 * z1
				- w2 * x4 * z1 + w3 * x4 * z1 + w3 * x1 * z2 - w4 * x1 * z2
				- w1 * x3 * z2 + w4 * x3 * z2 + w1 * x4 * z2 - w3 * x4 * z2
				- w2 * x1 * z3 + w4 * x1 * z3 + w1 * x2 * z3 - w4 * x2 * z3
				- w1 * x4 * z3 + w2 * x4 * z3 + w2 * x1 * z4 - w3 * x1 * z4
				- w1 * x2 * z4 + w3 * x2 * z4 + w1 * x3 * z4 - w2 * x3 * z4;
		double c = w3 * x2 * y1 - w4 * x2 * y1 - w2 * x3 * y1 + w4 * x3 * y1
				+ w2 * x4 * y1 - w3 * x4 * y1 - w3 * x1 * y2 + w4 * x1 * y2
				+ w1 * x3 * y2 - w4 * x3 * y2 - w1 * x4 * y2 + w3 * x4 * y2
				+ w2 * x1 * y3 - w4 * x1 * y3 - w1 * x2 * y3 + w4 * x2 * y3
				+ w1 * x4 * y3 - w2 * x4 * y3 - w2 * x1 * y4 + w3 * x1 * y4
				+ w1 * x2 * y4 - w3 * x2 * y4 - w1 * x3 * y4 + w2 * x3 * y4;
		double d = -(x3 * y2 * z1) + x4 * y2 * z1 + x2 * y3 * z1 - x4 * y3 * z1
				- x2 * y4 * z1 + x3 * y4 * z1 + x3 * y1 * z2 - x4 * y1 * z2
				- x1 * y3 * z2 + x4 * y3 * z2 + x1 * y4 * z2 - x3 * y4 * z2
				- x2 * y1 * z3 + x4 * y1 * z3 + x1 * y2 * z3 - x4 * y2 * z3
				- x1 * y4 * z3 + x2 * y4 * z3 + x2 * y1 * z4 - x3 * y1 * z4
				- x1 * y2 * z4 + x3 * y2 * z4 + x1 * y3 * z4 - x2 * y3 * z4;
		double e = w2
				* x4
				* y3
				* z1
				- w2
				* x3
				* y4
				* z1
				- w1
				* x4
				* y3
				* z2
				+ w1
				* x3
				* y4
				* z2
				- w2
				* x4
				* y1
				* z3
				+ w1
				* x4
				* y2
				* z3
				+ w2
				* x1
				* y4
				* z3
				- w1
				* x2
				* y4
				* z3
				+ w4
				* (x3 * y2 * z1 - x2 * y3 * z1 - x3 * y1 * z2 + x1 * y3 * z2
						+ x2 * y1 * z3 - x1 * y2 * z3)
				+ (w2 * x3 * y1 - w1 * x3 * y2 - w2 * x1 * y3 + w1 * x2 * y3)
				* z4
				+ w3
				* (-(x4 * y2 * z1) + x2 * y4 * z1 + x4 * y1 * z2 - x1 * y4 * z2
						- x2 * y1 * z4 + x1 * y2 * z4);
		return new double[] { a, b, c, d, e };
	}
	
	static Vector4d multiply(Vector4d a, Vector4d b, Vector4d c, Vector4d d) {
		double[][] mt = new double[4][4];
		Vector4d[] ar = { a, b, c, d };
		for (int i = 0; i < 4; i++) {
			Vector4d v = ar[i];
			mt[i][0] = v.getX();
			mt[i][1] = v.getY();
			mt[i][2] = v.getZ();
			mt[i][3] = v.getW();
		}

		double[] dets = new double[4];

		for (int i = 0; i < 4; i++) {
			double[][] cm = new double[3][3];
			for (int j = 1; j < 4; j++) {

				for (int k = 0; k < 4; k++) {
					int ck = k;
					if (k == i) {
						continue;
					} else {
						if (k > i) {
							ck++;
						}
					}
					cm[j][k] = mt[j][ck];
				}
			}
			dets[i] = new Matrix(mt).getDeterminant();
		}
		Vector4d ans = new Vector4d(new Point4d(dets[0], -dets[1], dets[2],
				-dets[3]));
		return ans.getNormalized();
	}

}
