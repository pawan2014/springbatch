package com.mebatch.kdp.random;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class TestRandomAccessReadWrite {

	public static void main(String[] args) {
		try {
			test1();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void test1() throws IOException {
		RandomAccessFile reader = new RandomAccessFile("src/main/java/com/mebatch/kdp/random/filereadwrite.txt", "rwd");
		Random p = new Random();

		int c = 0;
		String load;
		while (true) {
			load = reader.readLine();

			if (load == null) {
				break;
			}
			c++;
			System.out.println(load + reader.getFilePointer());
			if (true) {
				reader.seek(reader.getFilePointer() - 136);
				// System.err.println(load.replace('O', 'A'));

				String pk = p.nextInt(9) + load.substring(1);
				// String pk = load.replace('O', 'A');
				reader.writeBytes(pk + "\r\n");
				System.err.println(pk + reader.getFilePointer());
			} else {

			}

		}
		reader.close();
	}
}
