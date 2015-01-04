package ru.litsey2.cuttesseract.geometry;


public class Vector4d extends Point4d {

	public Vector4d(Point4d a, Point4d b) {
		super(b.getX() - a.getX(), b.getY() - a.getY(), b.getZ() - a.getZ(), b.getW() - a.getW());
	}

	public Vector4d(Point4d p) {
		super(p);
	}

	public Vector4d(double x, double y, double z, double w) {
		super(x, y, z, w);
	}

	public double length() {
		return distance(new Point4d(0, 0, 0, 0));
	}

	public Vector4d getNormalized() {
		double l = this.length();
		Vector4d ans = new Vector4d(getX() / l, getY() / l, getZ() / l, getW() / l);
		return ans;
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