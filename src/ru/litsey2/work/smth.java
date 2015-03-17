package ru.litsey2.work;

import ru.litsey2.cuttesseract.geometry.Point4d;

public class smth {
	
	static double from = -1;
	static double to = 1;
	static double step = 1;
	
	public static int[] getPermByPoints(Point4d[] points) {
		int[] answer = new int[4];
		for (int i = 0; i < 4; i++) {
			PointGenerator gen = new PointGenerator(from, to, step, 0);
			Point4d p;
			int count = 0;
			while ((p = gen.nextPoint()) != null) {
				if (p.equals(points[i])) {
					answer[i] = count;
				}
				count++;
			}
		}
		return answer;
	}

	public static int getNumByPerm(int[] perm) {
		int count = 0;
		int g = 81;
		for (int i = 0; i < g; i++) {
			for (int i1 = 0; i1 < g; i1++) {
				for (int i2 = 0; i2 < g; i2++) {
					for (int i3 = 0; i3 < g; i3++) {
						if (perm[0] == i && perm[1] == i1 && perm[2] == i2
								&& perm[3] == i3)
							return count;
						count++;
					}
				}
			}
		}
		return count;
	}
}
