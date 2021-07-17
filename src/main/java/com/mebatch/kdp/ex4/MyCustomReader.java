package com.mebatch.kdp.ex4;

import org.springframework.batch.item.file.FlatFileItemReader;

import com.mebatch.kdp.Person;

public class MyCustomReader extends FlatFileItemReader<Person> {

	@Override
	protected Person doRead() throws Exception {
		System.out.println("I am thread=" + Thread.currentThread().getName());
		return super.doRead();
	}

	@Override
	protected void doClose() throws Exception {
		// TODO Auto-generated method stub
		super.doClose();
	}

	@Override
	protected void doOpen() throws Exception {

		super.doOpen();
	}

}
