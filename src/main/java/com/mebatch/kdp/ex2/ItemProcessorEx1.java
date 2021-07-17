package com.mebatch.kdp.ex2;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Profile;

@Profile("ex2")
public class ItemProcessorEx1 implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Processing=" + item);
		Thread.sleep(1000);
		return item;
	}

}
