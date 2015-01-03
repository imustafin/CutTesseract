package ru.litsey2.cuttesseract;

import ru.litsey2.cuttesseract.MyGeom.Matrix;
import ru.litsey2.cuttesseract.MyGeom.Plane4d;
import ru.litsey2.cuttesseract.MyGeom.Point4d;
import ru.litsey2.cuttesseract.MyGeom.Vector4d;

public class Test {
	public static void main(String[] args) {
		Plane4d p = new Plane4d(new Point4d(1, 0, 0, 0),
				new Point4d(0, 1, 0, 0), new Point4d(0, 0, 1, 0), new Point4d(
						0, 0, 0, 0));
		System.out.println(p);
	}

}
