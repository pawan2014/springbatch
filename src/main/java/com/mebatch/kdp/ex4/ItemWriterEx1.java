package com.mebatch.kdp.ex4;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Profile;

import com.mebatch.kdp.Person;

@Profile("ex4")
public class ItemWriterEx1 implements ItemWriter<Person> {

	@Override
	public void write(List<? extends Person> items) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Writing->" + items);
		Thread.sleep(3000);
		System.out.println("-");

	}

}
