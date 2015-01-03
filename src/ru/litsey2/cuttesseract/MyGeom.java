package ru.litsey2.cuttesseract;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class MyGeom {

	static final double EPS = 1E-6;

	static int compare(double a, double b) {
		if (Math.abs(a - b) <= EPS) {
			return 0;
		}
		if (a < b) {
			return -1;
		} else {
			return 1;
		}
	}

	static class Point3d implements Comparable<Point3d> {
		double x;
		double y;
		double z;

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

		@Override
		public String toString() {
			return "(" + x + ", " + y + ", " + z + ")";
		}

		@Override
		public int compareTo(Point3d b) {
			if (compare(x, b.x) == 0) {
				if (compare(y, b.y) == 0) {
					if (compare(z, b.z) == 0) {
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

	static class Vector3d {
		Point3d p;

		Vector3d(Point3d a, Point3d b) {
			p = new Point3d(b.x - a.x, b.y - a.y, b.z - a.z);
		}

		Vector3d(Point3d p) {
			this.p = p;
		}

		Vector3d multiply(double k) {
			return new Vector3d(new Point3d(p.x * k, p.y * k, p.z * k));
		}

		double length() {
			return Math.sqrt(p.x * p.x + p.y * p.y + p.z * p.z);
		}

		@Override
		public String toString() {
			return "{" + p.x + ", " + p.y + ", " + p.z + "}";
		}

	}

	static class Segment3d implements Comparable<Segment3d> {
		Point3d a;
		Point3d b;

		Color color = Color.BLACK;

		@Override
		public String toString() {
			return "[" + a + ", " + b + "]";
		}

		public Segment3d(Point3d a, Point3d b, Color color) {
			this.color = color;
			if (a.compareTo(b) < 0) {
				this.a = a;
				this.b = b;
			} else {
				this.a = b;
				this.b = a;
			}
		}

		public Segment3d(Point3d a, Point3d b) {
			this(a, b, Color.BLACK);
		}

		ArrayList<Point3d> intersectWithPlane(Plane3d p) {
			ArrayList<Point3d> ans = new ArrayList<Point3d>();
			double res1 = a.compareToPlane(p);
			double res2 = b.compareToPlane(p);
			int sign1 = compare(res1, 0);
			int sign2 = compare(res2, 0);

			if (sign1 == 0) {
				ans.add(a);
			}
			if (sign2 == 0) {
				ans.add(b);
			}

			if (ans.size() > 0) {
				return ans;
			}

			if (sign1 == sign2) {
				return ans;
			}

			Vector3d ab = new Vector3d(a, b);
			/*
			 * t: в параметрическом уравенении прямой параметр задающий точку
			 * пересечения с плоскостью.
			 */
			double t = (-p.d - p.a * a.x - p.b * a.y - p.c * a.z)
					/ (p.a * ab.p.x + p.b * ab.p.y + p.c * ab.p.z);
			Point3d m = new Point3d(a.x + ab.p.x * t, a.y + ab.p.y * t, a.z
					+ ab.p.z * t);
			ans.add(m);
			return ans;
		}

		@Override
		public int compareTo(Segment3d z) {
			if (a.compareTo(z.a) == 0) {
				if (b.compareTo(z.b) == 0) {
					return 0;
				} else {
					return b.compareTo(z.b);
				}
			} else {
				return a.compareTo(z.a);
			}
		}
	}

	static class Plane3d {

		@Override
		public String toString() {
			return "Plane3d [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d
					+ "]";
		}

		double a;
		double b;
		double c;
		double d;

		public Plane3d(double a, double b, double c, double d) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
		}

		public Plane3d(Point3d p1, Point3d p2, Point3d p3) {
			double x1 = p1.x;
			double y1 = p1.y;
			double z1 = p1.z;
			double x2 = p2.x;
			double y2 = p2.y;
			double z2 = p2.z;
			double x3 = p3.x;
			double y3 = p3.y;
			double z3 = p3.z;
			a = (-y2) * z1 + y3 * z1 + y1 * z2 - y3 * z2 - y1 * z3 + y2 * z3;
			b = x2 * z1 - x3 * z1 - x1 * z2 + x3 * z2 + x1 * z3 - x2 * z3;
			c = (-x2) * y1 + x3 * y1 + x1 * y2 - x3 * y2 - x1 * y3 + x2 * y3;
			d = x3 * y2 * z1 - x2 * y3 * z1 - x3 * y1 * z2 + x1 * y3 * z2 + x2
					* y1 * z3 - x1 * y2 * z3;
		}

		Vector3d getNormal() {
			Vector3d v = new Vector3d(new Point3d(a, b, c));
			double l = v.length();
			return new Vector3d(new Point3d(a / l, b / l, c / l));
		}
	}

	static class Cube3d {

		Point3d[] vertices = new Point3d[8];
		boolean[][] hasEdge = new boolean[8][8];

		public Cube3d(Point3d[] vertices, boolean[][] hasEdge) {
			this.vertices = vertices;
			this.hasEdge = hasEdge;
		}
	}

	static class Point2d {
		double x;
		double y;

		public Point2d(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	class Segment2d {
		Point2d a;
		Point2d b;

		Color color;

		public Segment2d(Point2d a, Point2d b, Color color) {
			this.a = a;
			this.b = b;
			this.color = color;
		}

		public Segment2d(Point2d a, Point2d b) {
			this(a, b, Color.BLACK);
		}

	}

	static class Point4d implements Comparable<Point4d> {
		double x;
		double y;
		double z;
		double w;

		public Point4d(double x, double y, double z, double w) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ", " + z + ", " + w
					+ ")";
		}

		Point4d(Point4d a) {
			this(a.x, a.y, a.z, a.w);
		}

		@Override
		public int compareTo(Point4d b) {
			if (compare(x, b.x) == 0) {
				if (compare(y, b.y) == 0) {
					if (compare(z, b.z) == 0) {
						if (compare(w, b.w) == 0) {
							return 0;
						} else {
							return Double.compare(w, b.w);
						}
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

		static boolean cospatial(Point4d a, Point4d b, Point4d c, Point4d d) {
			double[][] mt = new double[4][4];
			Point4d[] ar = { a, b, c, d };
			for (int i = 0; i < 4; i++) {
				Point4d p = ar[i];
				mt[i][0] = p.x;
				mt[i][1] = p.y;
				mt[i][2] = p.z;
				mt[i][3] = p.w;
			}
			return (compare(new Matrix(mt).getDeterminant(), 0) == 0);
		}

		public double length(Point4d b) {
			double dx = b.x - x;
			double dy = b.y - y;
			double dz = b.z - z;
			double dw = b.w - w;
			return Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
		}

		public double compareToPlane(Plane4d p) {
			double res = p.a * x + p.b * y + p.c * z + p.d * w + p.e;
			return res;
		}

	}

	static class Segment4d implements Comparable<Segment4d> {
		Point4d a;
		Point4d b;
		Color color;

		public Segment4d(Point4d a, Point4d b, Color c) {
			if (a.compareTo(b) < 0) {
				this.a = a;
				this.b = b;
			} else {
				this.a = b;
				this.b = a;
			}
			this.color = c;
		}

		public Segment4d(Point4d a, Point4d b) {
			this(a, b, Color.BLACK);
		}

		@Override
		public int compareTo(Segment4d z) {
			if (a.compareTo(z.a) == 0) {
				if (b.compareTo(z.b) == 0) {
					return 0;
				} else {
					return b.compareTo(z.b);
				}
			} else {
				return a.compareTo(z.a);
			}
		}

		ArrayList<Point4d> intersectWithPlane(Plane4d p) {
			ArrayList<Point4d> ans = new ArrayList<Point4d>();

			double res1 = a.compareToPlane(p);
			double res2 = b.compareToPlane(p);
			int sign1 = compare(res1, 0);
			int sign2 = compare(res2, 0);

			if (sign1 == 0) {
				ans.add(a);
			}
			if (sign2 == 0) {
				ans.add(b);
			}

			if (ans.size() > 0) {
				return ans;
			}

			if (sign1 == sign2) {
				return ans;
			}

			Vector4d ab = new Vector4d(a, b);
			/*
			 * t: в параметрическом уравенении прямой параметр задающий точку
			 * пересечения с плоскостью.
			 */
			double t = (-p.a * a.x - p.b * a.y - p.c * a.z - p.d * a.w - p.e)
					/ (p.a * ab.p.x + p.b * ab.p.y + p.c * ab.p.z + p.d
							* ab.p.w);
			Point4d m = new Point4d(a.x + ab.p.x * t, a.y + ab.p.y * t, a.z
					+ ab.p.z * t, a.w + ab.p.w * t);
			ans.add(m);
			return ans;
		}

	}

	static class Cube4d {

		Point4d[] vertices = new Point4d[16];
		boolean[][] hasEdge = new boolean[16][16];
		Set<Segment4d> segments;

		public Cube4d(double coord, Color edgeColor) {
			Point4d[] v = { new Point4d(-coord, -coord, -coord, -coord),
					new Point4d(-coord, -coord, -coord, coord), new Point4d(-coord, -coord, coord, -coord),
					new Point4d(-coord, -coord, coord, coord), new Point4d(-coord, coord, -coord, -coord),
					new Point4d(-coord, coord, -coord, coord), new Point4d(-coord, coord, coord, -coord),
					new Point4d(-coord, coord, coord, coord), new Point4d(coord, -coord, -coord, -coord),
					new Point4d(coord, -coord, -coord, coord), new Point4d(coord, -coord, coord, -coord),
					new Point4d(coord, -coord, coord, coord), new Point4d(coord, coord, -coord, -coord),
					new Point4d(coord, coord, -coord, coord), new Point4d(coord, coord, coord, -coord),
					new Point4d(coord, coord, coord, coord) };
			vertices = v;
			segments =  new TreeSet<Segment4d>();
			for (int i = 0; i < vertices.length; i++) {
				for (int j = i + 1; j < vertices.length; j++) {
					Point4d a = vertices[i];
					Point4d b = vertices[j];
					int sameness = 0;
					if (a.x == b.x) {
						sameness++;
					}
					if (a.y == b.y) {
						sameness++;
					}
					if (a.z == b.z) {
						sameness++;
					}
					if(a.w == b.w){
						sameness++;
					}
					if (sameness == 3) {
						hasEdge[i][j] = true;
						hasEdge[j][i] = true;
						segments.add(new Segment4d(a, b, edgeColor));
					} else {
						hasEdge[i][j] = false;
						hasEdge[j][i] = false;
					}
				}
			}
		}
		
		public Cube4d(Point4d[] vertices, boolean[][] hasEdge) {
			this.vertices = vertices;
			this.hasEdge = hasEdge;
		}
	}

	static class Vector4d {
		Point4d p;

		public Vector4d(Point4d a, Point4d b) {
			p = new Point4d(b.x - a.x, b.y - a.y, b.z - a.z, b.w - a.w);
		}

		public Vector4d(Point4d p) {
			this.p = p;
		}

		public double length() {
			return p.length(new Point4d(0, 0, 0, 0));
		}

		public Vector4d getNormalized() {
			double l = this.length();
			Vector4d ans = new Vector4d(new Point4d(p.x / l, p.y / l, p.z / l,
					p.w / l));
			return ans;
		}

		static Vector4d multiply(Vector4d a, Vector4d b, Vector4d c, Vector4d d) {
			double[][] mt = new double[4][4];
			Vector4d[] ar = { a, b, c, d };
			for (int i = 0; i < 4; i++) {
				Point4d p = ar[i].p;
				mt[i][0] = p.x;
				mt[i][1] = p.y;
				mt[i][2] = p.z;
				mt[i][3] = p.w;
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

	public static class Matrix {
		double[][] data;

		public Matrix(double[][] data) {
			this.data = data;
		}

		public double getDeterminant() {
			return determinant(data);
		}

		/**
		 * 
		 * @param matrix
		 *            (two dimensional array)
		 * @return determinant
		 */
		double determinant(double[][] matrix) {
			int sum = 0;
			int s;
			if (matrix.length == 1) {
				/*
				 * bottom case of recursion. size 1 matrix determinant is
				 * itself.
				 */
				return (matrix[0][0]);
			}
			for (int i = 0; i < matrix.length; i++) {
				/*
				 * finds determinant using row-by-row expansion
				 */
				double[][] smaller = new double[matrix.length - 1][matrix.length - 1];
				/*
				 * creates smaller matrix- values not in same row, column
				 */
				for (int a = 1; a < matrix.length; a++) {
					for (int b = 0; b < matrix.length; b++) {
						if (b < i) {
							smaller[a - 1][b] = matrix[a][b];
						} else if (b > i) {
							smaller[a - 1][b - 1] = matrix[a][b];
						}
					}
				}
				if (i % 2 == 0) { // sign changes based on i
					s = 1;
				} else {
					s = -1;
				}
				sum += s * matrix[0][i] * (determinant(smaller));
			}
			/*
			 * returns determinant value. once stack is finished, returns final
			 * determinant.
			 */
			return (sum);
		}
	}

	public static class Plane4d {
		double a;
		double b;
		double c;
		double d;
		double e;

		public Plane4d(Point4d p1, Point4d p2, Point4d p3, Point4d p4) {
			double x1 = p1.x;
			double x2 = p2.x;
			double x3 = p3.x;
			double x4 = p4.x;

			double y1 = p1.y;
			double y2 = p2.y;
			double y3 = p3.y;
			double y4 = p4.y;

			double z1 = p1.z;
			double z2 = p2.z;
			double z3 = p3.z;
			double z4 = p4.z;

			double w1 = p1.w;
			double w2 = p2.w;
			double w3 = p3.w;
			double w4 = p4.w;

			a = w3 * y2 * z1 - w4 * y2 * z1 - w2 * y3 * z1 + w4 * y3 * z1 + w2
					* y4 * z1 - w3 * y4 * z1 - w3 * y1 * z2 + w4 * y1 * z2 + w1
					* y3 * z2 - w4 * y3 * z2 - w1 * y4 * z2 + w3 * y4 * z2 + w2
					* y1 * z3 - w4 * y1 * z3 - w1 * y2 * z3 + w4 * y2 * z3 + w1
					* y4 * z3 - w2 * y4 * z3 - w2 * y1 * z4 + w3 * y1 * z4 + w1
					* y2 * z4 - w3 * y2 * z4 - w1 * y3 * z4 + w2 * y3 * z4;
			b = -(w3 * x2 * z1) + w4 * x2 * z1 + w2 * x3 * z1 - w4 * x3 * z1
					- w2 * x4 * z1 + w3 * x4 * z1 + w3 * x1 * z2 - w4 * x1 * z2
					- w1 * x3 * z2 + w4 * x3 * z2 + w1 * x4 * z2 - w3 * x4 * z2
					- w2 * x1 * z3 + w4 * x1 * z3 + w1 * x2 * z3 - w4 * x2 * z3
					- w1 * x4 * z3 + w2 * x4 * z3 + w2 * x1 * z4 - w3 * x1 * z4
					- w1 * x2 * z4 + w3 * x2 * z4 + w1 * x3 * z4 - w2 * x3 * z4;
			c = w3 * x2 * y1 - w4 * x2 * y1 - w2 * x3 * y1 + w4 * x3 * y1 + w2
					* x4 * y1 - w3 * x4 * y1 - w3 * x1 * y2 + w4 * x1 * y2 + w1
					* x3 * y2 - w4 * x3 * y2 - w1 * x4 * y2 + w3 * x4 * y2 + w2
					* x1 * y3 - w4 * x1 * y3 - w1 * x2 * y3 + w4 * x2 * y3 + w1
					* x4 * y3 - w2 * x4 * y3 - w2 * x1 * y4 + w3 * x1 * y4 + w1
					* x2 * y4 - w3 * x2 * y4 - w1 * x3 * y4 + w2 * x3 * y4;
			d = -(x3 * y2 * z1) + x4 * y2 * z1 + x2 * y3 * z1 - x4 * y3 * z1
					- x2 * y4 * z1 + x3 * y4 * z1 + x3 * y1 * z2 - x4 * y1 * z2
					- x1 * y3 * z2 + x4 * y3 * z2 + x1 * y4 * z2 - x3 * y4 * z2
					- x2 * y1 * z3 + x4 * y1 * z3 + x1 * y2 * z3 - x4 * y2 * z3
					- x1 * y4 * z3 + x2 * y4 * z3 + x2 * y1 * z4 - x3 * y1 * z4
					- x1 * y2 * z4 + x3 * y2 * z4 + x1 * y3 * z4 - x2 * y3 * z4;
			e = w2
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
					* (x3 * y2 * z1 - x2 * y3 * z1 - x3 * y1 * z2 + x1 * y3
							* z2 + x2 * y1 * z3 - x1 * y2 * z3)
					+ (w2 * x3 * y1 - w1 * x3 * y2 - w2 * x1 * y3 + w1 * x2
							* y3)
					* z4
					+ w3
					* (-(x4 * y2 * z1) + x2 * y4 * z1 + x4 * y1 * z2 - x1 * y4
							* z2 - x2 * y1 * z4 + x1 * y2 * z4);
		}

		@Override
		public String toString() {
			return "Plane4d [a=" + a + ", b=" + b + ", c=" + c + ", d=" + d
					+ ", e=" + e + "]";
		}

		public Vector4d getNormal() {
			return new Vector4d(new Point4d(a, b, c, d)).getNormalized();
		}
		
	}

}
