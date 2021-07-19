package com.mebatch.kdp.ex7;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.mebatch.kdp.JobCompletionNotificationListener;
import com.mebatch.kdp.Person;

@Configuration
@EnableBatchProcessing
@Profile("ex7")
/**
 * Spring Batch’s multithreaded step concept allows a batch job to use Spring’s
 * TaskExecutor abstraction to execute each chunk in its own thread. a step in a
 * job can be configured to perform within a threadpool, processing each chunk
 * independently.
 * 
 * This example shows how a Step can be wraped ina flow and hpw split flow can
 * be created Step individually can be executed as TaskExecutor
 * 
 * @author pawan
 *
 */
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	// end::setup[]

	@Bean
	public Job processJob() {
		// return
		// jobBuilderFactory.get("processJob").start(splitFlow()).next(orderStep1()).end().build();
		return jobBuilderFactory.get("processJob").start(wrapStepFlow()).next(splitFlow()).end().build();

	}

	@Bean
	public Step orderStep1() {
		return stepBuilderFactory.get("orderStep1").<Person, Person>chunk(2)
				// reader(getItem())
				.reader(flatFileItemReader()).processor(getProcessor()).writer(getWriter()).taskExecutor(taskExecutor())
				.throttleLimit(2).build();
	}

	public ItemReader<String> getItem() {
		return new ItemReader<String>() {
			int i = 0;

			@Override
			public String read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
				String[] programmingLanguages = { "java", "python", "nodejs", "ruby" };

				synchronized (programmingLanguages) {
					if (i == 3) {
						i = 0;
					}
					i++;
				}

				Thread.sleep(1000);
				System.err.println("Reading.." + programmingLanguages[i]);
				return programmingLanguages[i];

			}

		};
	}

	public ItemProcessor<Person, Person> getProcessor() {
		return new ItemProcessor<Person, Person>() {

			@Override
			public Person process(Person item) throws Exception {
				Thread.sleep(1000);
				System.out.println("Processor=" + item);
				return item;
			}

		};
	}

	public ItemWriter<Person> getWriter() {
		return new ItemWriter<Person>() {

			@Override
			public void write(List<? extends Person> items) throws Exception {
				Thread.sleep(3000);
				System.out.println("Writing->" + items.size());
				// System.out.println(items.get(0));

			}

		};
	}

	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor("spring_batch");
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener();
	}

	// Parallel flow
	@Bean
	public Flow splitFlow() {
		return new FlowBuilder<SimpleFlow>("splitFlow").split(taskExecutor()).add(flow1(), flow2()).build();
	}

	public Flow wrapStepFlow() {
		return new FlowBuilder<SimpleFlow>("flow1").start(orderStep1()).build();
	}

	@Bean
	public Flow flow1() {
		return new FlowBuilder<SimpleFlow>("flow1")
				.start(this.stepBuilderFactory.get("flow1-step1").tasklet(new Tasklet() {

					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
							throws Exception {
						Thread.sleep(2000);
						System.out.println("flow1-step1-tasklet1");
						return RepeatStatus.FINISHED;
					}
				}).build()).next(this.stepBuilderFactory.get("flow1-step2").tasklet(new Tasklet() {

					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
							throws Exception {
						Thread.sleep(2000);
						System.out.println("flow1-step2-tasklet2");
						return RepeatStatus.FINISHED;
					}
				}).build())

				.build();
	}

	@Bean
	public Flow flow2() {
		return new FlowBuilder<SimpleFlow>("flow2")
				.start(this.stepBuilderFactory.get("flow2-step1").tasklet(new Tasklet() {

					@Override
					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
							throws Exception {
						Thread.sleep(5000);
						System.out.println("flow2-step1-tasklet1");
						return RepeatStatus.FINISHED;
					}
				}).build())

				.build();
	}

	@Bean
	public FlatFileItemReader<Person> flatFileItemReader() {
		FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
		DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<Person>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "person_ID", "name", "first", "last" });
						setStrict(false);
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
					{
						setTargetType(Person.class);
					}
				});
			}
		};
		reader.setResource(new ClassPathResource("sampledata.csv"));
		reader.setLineMapper(lineMapper);
		return reader;
	}

}