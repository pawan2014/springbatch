package com.mebatch.kdp.ex3;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Profile;

@Profile("ex3")
public class ItemWriterEx1 implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Writing->" + items.size());
		Thread.sleep(3000);
		System.out.println("-");

	}

}
