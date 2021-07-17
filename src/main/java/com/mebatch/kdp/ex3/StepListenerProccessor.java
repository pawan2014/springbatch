package com.mebatch.kdp.ex3;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.context.annotation.Profile;

@Profile("ex3")
public class StepListenerProccessor {
	private StepExecution stepExecution;

	@BeforeStep
	public void saveStepExecution(StepExecution stepExecution) {
		stepExecution.getExecutionContext().put("name", "pawan");
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