package ru.litsey2.cuttesseract;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

public class PointRotator {
	
	Set<Segment4d> segments;
	
	private double[] angles = new double[4];
	
	public Point4d getRotated(Point4d a) {
		double x = a.getX();
		double y = a.getY();
		double z = a.getZ();
		double w = a.getW();

		double nx = x;
		double ny = y;
		double nz = z;
		double nw = w;

		nx = x * Math.cos(angles[0]) - y * Math.sin(angles[0]);
		ny = x * Math.sin(angles[0]) + y * Math.cos(angles[0]);

		x = nx;
		y = ny;
		z = nz;
		w = nw;

		ny = y * Math.cos(angles[1]) - z * Math.sin(angles[1]);
		nz = y * Math.sin(angles[1]) + z * Math.cos(angles[1]);

		x = nx;
		y = ny;
		z = nz;
		w = nw;

		nz = z * Math.cos(angles[2]) - w * Math.sin(angles[2]);
		nw = z * Math.sin(angles[2]) + w * Math.cos(angles[2]);

		x = nx;
		y = ny;
		z = nz;
		w = nw;

		nw = w * Math.cos(angles[3]) - x * Math.sin(angles[3]);
		nx = w * Math.sin(angles[3]) + x * Math.cos(angles[3]);

		return new Point4d(nx, ny, nz, nw);
	}
	
	public void add(Segment4d e) {
		segments.add(e);
	}
	
	public void addAll(Collection<? extends Segment4d> col) {
		segments.addAll(col);
	}
	
	public void addAngle(int i, double deg) {
		angles[i] += deg;
	}
	
}
