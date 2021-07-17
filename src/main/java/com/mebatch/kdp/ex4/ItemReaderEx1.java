package com.mebatch.kdp.ex4;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Profile;

@Profile("ex4")
public class ItemReaderEx1 implements ItemReader<String> {

	int i = 0;

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub

		if (i > 5) {
			return null;
		}
		String[] programmingLanguages = { "java", "python", "nodejs", "ruby", "toby", "goby", "soby" };

		if (i == 5) {
			i = 0;
		}
		Thread.sleep(1000);
		System.err.println("Reading.." + i + programmingLanguages[i]);
		return programmingLanguages[i++] + Math.random();

	}

}
