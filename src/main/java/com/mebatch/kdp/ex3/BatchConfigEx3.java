package com.mebatch.kdp.ex3;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.mebatch.kdp.JobCompletionNotificationListener;

@Configuration
@EnableBatchProcessing
@Profile("ex3")
public class BatchConfigEx3 {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	// end::setup[]

	@Bean
	public Job processJob() {
		return jobBuilderFactory.get("processJob").incrementer(new RunIdIncrementer()).listener(listener())
				.flow(orderStep1()).end().build();
	}

	@Autowired
	ItemProcessorEx1 itprocessor;

	@Bean
	public Step orderStep1() {
		return stepBuilderFactory.get("orderStep1").<String, String>chunk(4).reader(new ItemReaderEx1())
				.processor((ItemProcessor) asyncItemProcessor()).writer((ItemWriter) asyncItemWriter())
				.listener(new StepListenerProccessor()).build();

		// .listener(new ItemProcessorListenerEx2()).writer(new ItemWriterEx1())
		// .listener(new ChunkStepListener()).build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener();
	}

	@Bean
	public ItemProcessor<String, Future<String>> asyncItemProcessor() {
		AsyncItemProcessor<String, String> asyncItemProcessor = new AsyncItemProcessor<>();
		asyncItemProcessor.setDelegate(itprocessor);
		asyncItemProcessor.setTaskExecutor(getAsyncExecutor());
		return asyncItemProcessor;
	}

	@Bean
	public ItemWriter<Future<String>> asyncItemWriter() {
		AsyncItemWriter<String> asyncItemWriter = new AsyncItemWriter<>();
		asyncItemWriter.setDelegate(new ItemWriterEx1());
		return asyncItemWriter;
	}

	@Bean(name = "asyncExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(2);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setThreadNamePrefix("AsyncExecutor-");
		return executor;
	}

}