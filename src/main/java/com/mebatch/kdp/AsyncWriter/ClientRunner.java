package com.mebatch.kdp.AsyncWriter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mebatch.kdp.Person;

@Service
public class ClientRunner {

	@Autowired
	AsyncReaderWritter<Person> readerWritter;

	@Autowired
	ItemReaderAsync itemReaderAsync;

	@Autowired
	ItemWriter<Person> writer;

	public void run() {
		// readerWritter.setReader(itemReaderAsync);

	}
}
