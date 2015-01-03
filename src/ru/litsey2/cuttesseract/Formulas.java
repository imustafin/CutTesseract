package ru.litsey2.cuttesseract;

import ru.litsey2.cuttesseract.MyGeom.Plane3d;
import ru.litsey2.cuttesseract.MyGeom.Point3d;
import ru.litsey2.cuttesseract.MyGeom.Vector3d;

public class Formulas {

	static Vector3d findK(Vector3d a, Vector3d b) {
		return new Vector3d(new Point3d(a.p.y * b.p.z - b.p.y * a.p.z, b.p.x
				* a.p.z - a.p.x * b.p.z, a.p.x * b.p.y - b.p.x * a.p.y));
	}

	static Point3d findN(Plane3d p, Point3d o) {

		double y0 = o.y;
		double x0 = o.x;
		double z0 = o.z;

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
		
		if(MyGeom.compare(d, 0) == 0) {
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
		
		if(MyGeom.compare(d, 0) == 0) {
			if(MyGeom.compare(a, 0) != 0) {
				return new Point3d((-b - c) / a, 1, 1);
			}
			if(MyGeom.compare(b, 0) != 0) {
				return new Point3d(1, (-a - c) / b, 1);
			}
			return new Point3d(1, 1, (-a - b) / c);
		}
		
		double x = (-d * a * 2) / (a * a * 2 + b * b * 3 + c * c * 4);
		double y = (-d * b * 3) / (a * a * 2 + b * b * 3 + c * c * 4);
		double z = (-d * c * 4) / (a * a * 2 + b * b * 3 + c * c * 4);
		
		return new Point3d(x, y, z);
	}

}
