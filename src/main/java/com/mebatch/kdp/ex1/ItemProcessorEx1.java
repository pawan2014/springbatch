package com.mebatch.kdp.ex1;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Profile;

@Profile("ex1")
public class ItemProcessorEx1 implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Processing=" + item);
		Thread.sleep(2000);
		return item;
	}

}
