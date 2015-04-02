package ru.litsey2.work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
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

	static double from = -1;
	static double to = 1;
	static double step = 1;
	int Fstart = 0;
	int Fend = 0;

	long cnt = 0;
	double startTime = System.currentTimeMillis() / 1000L;

	//HashSet<Graph> set = new HashSet<Graph>();
	Graph[] Graps = new Graph[1000];
	int now = 0;

	int[][] array = new int[18][18];
	
	void gend() {
		Plane4d plane = new Plane4d(pts[0], pts[1], pts[2], pts[3]);
		if (Double.isNaN(plane.getNormal().getW())
				|| Double.isNaN(plane.getNormal().getX())
				|| Double.isNaN(plane.getNormal().getY())
				|| Double.isNaN(plane.getNormal().getZ())) {
			return;
		}
		Set<Segment4d> cut = Geometry.makeSection(plane, cube);
		Graph g = new Graph(cut);
		if (g.n < 4) {
			return;
		}
		
//		int a3 = 0;
//		int a4 = 0;
//		for(int i = 0; i < g.n; i++){
//			if(g.deg[i] == 3){
//				a3++;
//			}else{
//				a4++;
//			}
//		}
//		array[a4][a3]++;
		//if(a4 == 4 && a3 == 4){
		//array[a3][a4]++;
		
		for (int i = 0; i < now; i++) {
			if (g.equals(Graps[i])) {
				return;
			}
		}
		if (g.hash1 == 0)
			g.hash();
		//System.out.println(g.hash1 + " " + g.hash2);
		Graps[now] = g;
		now++;
		String s = "[" + now + "] " + pts[0] + " " + pts[1] + " " + pts[2]
				+ " " + pts[3];
		pw.println(s);
		System.err.println(s);
		//pw.println(g.printGraph());
		//pw.println(g.hash1 + " " + g.hash2);
		pw.flush();
	}
	//}

	void gen(int num, int st) {
		if (num == 4) {
			cnt++;
			if (cnt < Fstart)
				return;
			if (cnt > Fend) {
				return;
			}
			if (cnt % 1000 == 0) {
				System.out.println(cnt / 1000 + "."
						+ String.format("%03d", cnt % 1000) + " | ["
						+ (double) cnt
						/ (System.currentTimeMillis() / 1000L - startTime)
						+ "]");
			}
			gend();
			return;
		}
		PointGenerator gen = new PointGenerator(from, to, step, st);
		Point4d p;
		while ((p = gen.nextPoint()) != null) {
			pts[num] = p;
			gen(num + 1, st + 1);
		}

	}

	public void getFstart() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("Fstart.txt"));
		Fstart = scanner.nextInt();
		Fend = scanner.nextInt();
		scanner.close();
	}

	public void prEnd() {
//		for(int i = 0; i < 18; i++){
//			for(int j = 0; j < 18; j++){
//				pw.print(array[i][j] + "	");
//			}
//			pw.println("");
//		}
		pw.println("[" + this.Fend + "][END]");
	}

	public void merge() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("merge.txt"));
		int i = -1;
		while(scanner.hasNext()){
			i++;
			scanner.next();
			Point4d p = null;
			for (int j = 0; j < 4; j++) {
				p = new Point4d(scanner.nextDouble(), scanner.nextDouble(),
						scanner.nextDouble(), scanner.nextDouble());
				pts[j] = p;
			}
			System.out.println("[" + i + "]");
			gend();
		}
		scanner.close();
	}

	public void pr10(){
		try {
			PrintWriter a = new PrintWriter("ready.txt");
			a.print("10");
			a.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {

		CutGenerator cg = new CutGenerator();
		try {
			cg.pw = new PrintWriter("cuts.txt");
			//cg.getFstart();
			//cg.gen(0, 0);
			cg.merge();
			cg.prEnd();
			//cg.pr10();
		} finally {
			cg.pw.close();
		}
		System.out.println("[END]");
	}
}
