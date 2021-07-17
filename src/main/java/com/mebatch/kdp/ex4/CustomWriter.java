package com.mebatch.kdp.ex4;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;

import com.mebatch.kdp.Person;

public class CustomWriter extends FlatFileItemWriter<Person> {

	ExecutionContext jobContext;
	ExecutionContext stepContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("CustomWriter:afterPropertiesSet");
		super.afterPropertiesSet();
	}

	@Override
	public String doWrite(List<? extends Person> items) {
		int count = stepContext.getInt("counter");
		System.out.println("CustomWriter:doWrite" + count);
		// THis will not work as this list is unmodifiable items.sort((Person p1, Person
		// p2) -> p1.getCounter().compareTo(p2.getCounter()));
		// TODO Auto-generated method stub
		items.stream().forEach(p -> System.out.println(p));
		return super.doWrite(items);
	}

	@Override
	public String getExecutionContextKey(String key) {
		System.out.println("CustomWriter:getExecutionContextKey");
		// TODO Auto-generated method stub
		return super.getExecutionContextKey(key);
	}

	@BeforeStep
	public void retrieveInterstepData(StepExecution stepExecution) {
		JobExecution jobExecution = stepExecution.getJobExecution();
		jobContext = jobExecution.getExecutionContext();
		stepContext = stepExecution.getExecutionContext();

	}

}
