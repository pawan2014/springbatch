package com.mebatch.kdp.ex3;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("ex3")
@Component
public class ItemProcessorEx1 implements ItemProcessor<String, String> {

	@Autowired
	ServiceProcessor serv;

	@Override
	public String process(String item) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("Processing=" + serv.getName(item));
		System.out.println("Processing=" + serv.getNameAsync(item).get());
		Thread.sleep(1000);
		return item;
	}

}
