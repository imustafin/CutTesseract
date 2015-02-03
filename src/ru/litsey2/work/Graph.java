package ru.litsey2.work;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ru.litsey2.cuttesseract.geometry.Point4d;
import ru.litsey2.cuttesseract.geometry.Segment4d;

public class Graph {

	boolean[][] e;
	Set<Segment4d> segments;

	int[] deg;

	int n;
	int m;

	public Graph(Collection<Segment4d> segments) {
		this.segments = new TreeSet<Segment4d>();
		this.segments.addAll(segments);
		TreeMap<Point4d, Integer> map = new TreeMap<Point4d, Integer>();
		n = 0;
		m = 0;
		for (Segment4d s : segments) {
			Point4d a = s.getA();
			if (!map.containsKey(a)) {
				map.put(a, n);
				n++;
			}
			Point4d b = s.getB();
			if (!map.containsKey(b)) {
				map.put(b, n);
				n++;
			}
		}

		deg = new int[n];
		e = new boolean[n][n];

		for (Segment4d s : segments) {
			Point4d a = s.getA();
			Point4d b = s.getB();
			int u = map.get(a);
			int v = map.get(b);
			if (e[u][v]) {
				continue;
			}
			e[u][v] = true;
			e[v][u] = true;
			deg[u]++;
			deg[v]++;
			m++;
		}
		Arrays.sort(deg);
	}

	@Override
	public int hashCode() {
		return m * 1000 + n;
	}
	
	public String printGraph(){
		String s = "[" + this.n + "][" + this.m + "]\n" + "(";
		for(int i = 0; i < this.n - 1; i++){
			s += this.deg[i] + ", ";
		}
		s += this.deg[n-1] + ")\n";
		return s;
	}

	public boolean equals(Graph g) {
		//if(g) throw new IllegalStateException("Bug");
		if (n != g.n || m != g.m) {
			return false;
		}
		for (int i = 0; i < n; i++) {
			if (deg[i] != g.deg[i]) {
				return false;
			}
		}

		int[] rep = new int[n];
		for (int i = 0; i < n; i++) {
			rep[i] = i;
		}

		do {
			/*boolean[][] re = new boolean[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					int u = rep[i];
					int v = rep[j];
					re[u][v] = g.e[i][j];
				}
			}*/

			boolean ok = true;
			for (int i = 0; i < n && ok; i++) {
				for (int j = 0; j < n && ok; j++) {
					if(g.e[rep[i]][rep[j]] != e[i][j]) {
						ok = false;						
					}
				}				
			}
			if(ok) {
				return true;
			}
		} while ((rep = Permutator.nextPermutation(rep)) != null);
		
		return false;
	}
}
