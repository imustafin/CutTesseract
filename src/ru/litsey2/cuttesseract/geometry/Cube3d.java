package ru.litsey2.cuttesseract.geometry;

public class Cube3d {

	final Point3d[] vertices;
	final boolean[][] hasEdge;

	public Cube3d(Point3d[] vertices, boolean[][] hasEdge) {
		this.vertices = vertices;
		this.hasEdge = hasEdge;
	}
}