package com.mebatch.kdp.ex5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SortingLogic {

	public void testLogi() throws IOException {
		System.err.println("Starting ...");
		File file1 = new File("ex1.txt");
		File file2 = new File("ex2.txt");

		BufferedReader br1 = new BufferedReader(new FileReader(file1));
		BufferedReader br2 = new BufferedReader(new FileReader(file2));

		boolean readLeft = true;
		boolean readRight = true;

		int left = -1;
		int right = -1;
		int lastvalue = -1;
		boolean leftEnd = false;
		boolean rightEnd = false;
		while (true) {
			String line1 = null;
			String line2 = null;

			if (readLeft) {
				line1 = br1.readLine();
				// if null then set to boolean fasle so it never enter this if else
				if (line1 == null) {
					leftEnd = true;
					break;
				} else {
					left = Integer.parseInt(line1);
				}

			}
			if (readRight) {
				line2 = br2.readLine();
				if (line2 == null) {
					rightEnd = true;
					break;
				} else {
					right = Integer.parseInt(line2);
				}

			}

			if (left < right) {
				System.out.println(left);
				readLeft = true;
				readRight = false;
				lastvalue = right;
			} else if (left > right) {
				System.out.println(right);
				readLeft = false;
				readRight = true;
				lastvalue = left;
			}
		}
		System.out.println(lastvalue);
		if (leftEnd) {
			String line;
			while ((line = br2.readLine()) != null) {
				System.out.println(line);
			}
		} else if (rightEnd) {
			String line;
			while ((line = br1.readLine()) != null) {
				System.out.println(line);
			}
		}

		br1.close();
		br2.close();
		System.err.println("Ending ...");

	}
}
