package com.mebatch.kdp.ex1;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.context.annotation.Profile;

@Profile("ex1")
public class ItemReaderEx1 implements ItemReader<String> {

	int i = 0;

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub

		String[] programmingLanguages = { "java", "python", "nodejs", "ruby" };

		if (i == 3) {
			i = 0;
		}
		Thread.sleep(1000);
		System.err.println("Reading.." + programmingLanguages[i]);
		return programmingLanguages[i++];

	}

}
