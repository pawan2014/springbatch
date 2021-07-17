package com.mebatch.kdp.ex4;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.context.annotation.Profile;

@Profile("ex4")
public class StepListenerProccessor {
	private StepExecution stepExecution;

	@BeforeStep
	public void saveStepExecution(StepExecution stepExecution) {
		stepExecution.getExecutionContext().put("name", "pawan");
		stepExecution.getExecutionContext().putInt("counter", 0);
		System.err.println("before Step fargi listener");
		this.stepExecution = stepExecution;
	}

	@AfterStep
	public void afterStepExecution(StepExecution stepExecution) {
		System.err.println(stepExecution.getExecutionContext().get("name"));
		System.err.println("After Step fargi listener");
		this.stepExecution = stepExecution;
	}

}