package com.mebatch.kdp.ex4;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.mebatch.kdp.Person;

@Profile("ex4")
@Component
public class ItemProcessorEx1 implements ItemProcessor<Person, Person> {

	@Autowired
	ServiceProcessor serv;

	ExecutionContext jobContext;
	ExecutionContext stepContext;

	@Override
	public Person process(Person item) throws Exception {
		// TODO Auto-generated method stub
		// System.out.println("Processing=" + serv.getName(item));
		System.out.println("Processing=" + item);
		int count = stepContext.getInt("counter");
		stepContext.put("counter", count);
		System.out.println();
		Thread.sleep(500);
		item.setCounter(count);
		return item;
	}

	@BeforeStep
	public void retrieveInterstepData(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		jobContext = jobExecution.getExecutionContext();
		stepContext = stepExecution.getExecutionContext();

	}

}
