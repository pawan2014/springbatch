package com.mebatch.kdp.AsyncWriter;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class ItemReaderAsync implements ItemReader<String> {

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
		Thread.sleep(100);
		System.err.println("Reading.." + i + programmingLanguages[i]);
		return programmingLanguages[i++] + Math.random();

	}

}
