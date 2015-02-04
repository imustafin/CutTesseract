package ru.litsey2.work;

import java.util.Arrays;

public class Permutator {

	/**
	 * modifies <b>c</b> to next permutation or returns null if such permutation
	 * does not exist
	 */
	public static int[] nextPermutation(final int[] c, final int bad) {
		//optimization
		if(bad != -1){
			int min = 100000000;
			int min_index = bad;
			for(int i = bad + 1; i < c.length - 1; i++){
				if(c[i] > c[bad] && c[i] < min){
					min = c[i];
					min_index = i;
				}
			}
			if(min_index != bad){
				int tmp = c[bad];
				c[bad] = c[min_index];
				c[min_index] = tmp;
				Arrays.sort(c, bad + 1, c.length - 1);
				return c;
			}
		}
		// 1. finds the largest k, that c[k] < c[k+1]
		int first = getFirst(c);
		if (first == -1)
			return null; // no greater permutation
		// 2. find last index toSwap, that c[k] < c[toSwap]
		int toSwap = c.length - 1;
		while (c[first] >= c[toSwap])
			--toSwap;
		// 3. swap elements with indexes first and last
		swap(c, first++, toSwap);
		// 4. reverse sequence from k+1 to n (inclusive)
		toSwap = c.length - 1;
		while (first < toSwap)
			swap(c, first++, toSwap--);
		return c;
	}

	/**
	 * finds the largest k, that c[k] < c[k+1] if no such k exists (there is not
	 * greater permutation), return -1
	 */
	private static int getFirst(final int[] c) {
		for (int i = c.length - 2; i >= 0; --i)
			if (c[i] < c[i + 1])
				return i;
		return -1;
	}

	// swaps two elements (with indexes i and j) in array
	private static void swap(final int[] c, final int i, final int j) {
		final int tmp = c[i];
		c[i] = c[j];
		c[j] = tmp;
	}
}
