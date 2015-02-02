package ru.litsey2.work;

import java.util.ArrayList;

import ru.litsey2.cuttesseract.geometry.Geometry;
import ru.litsey2.cuttesseract.geometry.Point4d;

public class PointGenerator {

	ArrayList<Point4d> p;
	int i;

	public PointGenerator(double from, double to, double step) {
		p = new ArrayList<Point4d>();
		i = 0;

		for (double x = from; Geometry.compareEps(x, to) <= 0; x += step) {
			for (double y = from; Geometry.compareEps(y, to) <= 0; y += step) {
				for (double z = from; Geometry.compareEps(z, to) <= 0; z += step) {
					for (double w = from; Geometry.compareEps(w, to) <= 0; w += step) {
						p.add(new Point4d(x, y, z, w));
					}
				}
			}
		}
	}

	Point4d nextPoint() {
		if (i == p.size()) {
			return null;
		} else {
			return p.get(i++);
		}
	}

}
