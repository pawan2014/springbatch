package com.mebatch.kdp.appendfile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class AppendFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// File f = new
		// File("C:\\Users\\Public\\Documents\\pawanwork\\sts-wrk\\kdp\\student-marksheet.csv");
		// File dest = new
		// File("C:\\Users\\Public\\Documents\\pawanwork\\sts-wrk\\kdp\\exappend.txt");

		File f = new File(
				"C:\\Users\\pawan\\Downloads\\Yoga Summer Camp Grade 35 At 830 From 15th June to 28th June-20210615_030038-Meeting Recording.mp4");
		File dest = new File("C:\\\\Users\\\\pawan\\\\Downloads\\1.mp4");

		try {
			appendFile(f, dest, 1024);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void appendFile(File src, File dest, int bufSize) throws IOException {
		System.out.println("File Append Started...");
		long start = System.currentTimeMillis();
		RandomAccessFile toFile = null;
		RandomAccessFile aFile = null;
		long srcFileSize = 0;
		long destFileSize = 0;
		if (bufSize == 0) {
			bufSize = 8192;
		}

		try {
			aFile = new RandomAccessFile(src, "r");
			FileChannel inChannel = aFile.getChannel();
			srcFileSize = inChannel.size();

			toFile = new RandomAccessFile(dest, "rw");
			FileChannel toChannel = toFile.getChannel();

			ByteBuffer buf = ByteBuffer.allocate(bufSize);

			int bytesRead = inChannel.read(buf);
			while (bytesRead != -1) {
				buf.flip();
				toChannel.write(buf);
				buf.clear();
				bytesRead = inChannel.read(buf);
			}
			toChannel.force(true);
			destFileSize = toChannel.size();
		} catch (IOException e) {

			e.printStackTrace();
			throw e;
		} finally {
			try {
				aFile.close();
				toFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		long end = System.currentTimeMillis()-start;
		boolean filesame=srcFileSize==destFileSize;
		long throughput=(srcFileSize/end)/1024;
		String display = String.format("Time Take:%s ms (%s sec),rate %s kb/sec, Source/Dest File size is equal(%s), %s/%s", end,(end/1000), throughput ,filesame,srcFileSize,
				destFileSize);
		System.out.println(display);
	}

}
