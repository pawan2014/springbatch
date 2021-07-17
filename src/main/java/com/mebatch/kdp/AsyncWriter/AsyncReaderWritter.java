package com.mebatch.kdp.AsyncWriter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Service;

@Service
public class AsyncReaderWritter<R> {

	ItemReader<R> itemreader;
	ItemWriter<R> itemWriter;
	BlockingQueue<R> queue = new ArrayBlockingQueue<>(10);

	public void setReader(ItemReader<R> itemreader) {
		this.itemreader = itemreader;

	}

	public void setWriter(ItemWriter<R> itemWriter) {
		this.itemWriter = itemWriter;
	}

	public void execute() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		System.out.println("Reading");
		while (true) {

			R item = itemreader.read();
			queue.put(item);
			if (item == null) {
				break;
			}
			System.out.println(item);
		}
	}

}
