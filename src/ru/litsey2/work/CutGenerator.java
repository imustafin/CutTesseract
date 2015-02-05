package ru.litsey2.work;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ru.litsey2.cuttesseract.Colors;
import ru.litsey2.cuttesseract.geometry.Cube4d;
import ru.litsey2.cuttesseract.geometry.Geometry;
import ru.litsey2.cuttesseract.geometry.Plane4d;
import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

public class CutGenerator {

	PrintWriter pw;

	Cube4d cube = new Cube4d(1, Colors.CUBE_COLOR);

	Point4d[] pts = new Point4d[4];

	double from = -1;
	double to = 1;
	double step = 1;
	
	long cnt = 0;
	double startTime = System.currentTimeMillis() / 1000L;

	//HashSet<Graph> set = new HashSet<Graph>();
	Graph[] Graps = new Graph[1000]; 
	int now = 0;

	void gend() {
		Plane4d plane = new Plane4d(pts[0], pts[1], pts[2], pts[3]);		
		Set<Segment4d> cut = Geometry.makeCut(plane, cube);
		Graph g = new Graph(cut);
		if (g.n < 4) {
			return;
		}
		for(int i = 0; i < now; i++){
			if(g.equals(Graps[i])){
				return;
			}
		}
		Graps[now] = g;
		now++;
		//boolean added = set.add(g);
		//if (added) {
		pw.println("[" + now + "] " + pts[0] + " " + pts[1] + " " + pts[2] + " " + pts[3]);
		pw.flush();
		/*if(now == 10) {
			throw new IllegalStateException("Too many cuts");
		}*/
		//}		
	}

	void gen(int num, int st) {		
		if (num == 4) {
			cnt++;
			System.out.println(cnt / 1000 + "." + String.format("%03d", cnt % 1000) + " | [" + (double)cnt / (System.currentTimeMillis() / 1000L - startTime) + "]");
			//long tt = System.currentTimeMillis();
			gend();
			//System.out.println("gend took " + (System.currentTimeMillis() - tt) + " ms");
			return;
		}
		PointGenerator gen = new PointGenerator(from, to, step, st);
		Point4d p;
		while ((p = gen.nextPoint()) != null) {
			pts[num] = p;			
			gen(num + 1, st + 1);
		}
		
	}

	public static void main(String[] args) throws FileNotFoundException {
		CutGenerator cg = new CutGenerator();
		try {
			cg.pw = new PrintWriter("cuts.txt");
			cg.gen(0, 0);
		} finally {
			cg.pw.close();
		}
	}
}
