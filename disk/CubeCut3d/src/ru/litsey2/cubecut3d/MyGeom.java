package ru.litsey2.cubecut3d;

import java.awt.Color;
import java.util.ArrayList;

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
					if(compare(z, b.z) == 0) {
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
			double t = (-p.d - p.a * a.x - p.b * a.y - p.c * a.z)
					/ (p.a * ab.p.x + p.b * ab.p.y + p.c * ab.p.z);
			// ab = ab.multiply(t);
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

}
