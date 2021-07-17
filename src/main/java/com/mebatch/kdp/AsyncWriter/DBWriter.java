package com.mebatch.kdp.AsyncWriter;

import java.util.concurrent.BlockingQueue;

import org.springframework.batch.item.ItemWriter;

public class DBWriter<T> implements Runnable {
	private BlockingQueue<T> queue;
	ItemWriter<T> itemWriter;

	public DBWriter(BlockingQueue<T> queue, ItemWriter<T> itemWriter) {
		super();
		this.queue = queue;
		this.itemWriter = itemWriter;

	}

	@Override
	public void run() {
		try {
			T msg;
			// consuming messages until exit message is received

			while ((msg = queue.take()) != null) {

				Thread.sleep(10);
				// System.out.println("Consumed " + msg());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
