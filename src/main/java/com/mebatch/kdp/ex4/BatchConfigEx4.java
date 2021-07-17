package com.mebatch.kdp.ex4;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.mebatch.kdp.JobCompletionNotificationListener;
import com.mebatch.kdp.Person;

@Configuration
@EnableBatchProcessing
@Profile("ex4")
public class BatchConfigEx4 {

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
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor t1 = new SimpleAsyncTaskExecutor("spring_batch");
		t1.setConcurrencyLimit(2);
		return t1;
	}

	@Bean
	public Step orderStep1() {
		return stepBuilderFactory.get("orderStep1").<Person, Person>chunk(3).reader(personReader())
				.processor(itprocessor).writer(writer()).listener(new StepListenerProccessor())
				.taskExecutor(taskExecutor()).throttleLimit(2).build();

		// .listener(new ItemProcessorListenerEx2()).writer(new ItemWriterEx1())
		// .listener(new ChunkStepListener()).build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener();
	}

	@Bean
	public ItemReader<Person> personReader() {
		FlatFileItemReader<Person> reader = new MyCustomReader();

		reader.setResource(new ClassPathResource("student-data.csv"));
		reader.setLineMapper(new DefaultLineMapper<Person>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "lastName", "firstName" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
					{
						setTargetType(Person.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	public ItemWriter<Person> writer() {
		FlatFileItemWriter<Person> writer = new CustomWriter();
		writer.setResource(new FileSystemResource("student-marksheet.csv"));
		DelimitedLineAggregator<Person> delLineAgg = new DelimitedLineAggregator<Person>();
		delLineAgg.setDelimiter(",");
		BeanWrapperFieldExtractor<Person> fieldExtractor = new BeanWrapperFieldExtractor<Person>();
		fieldExtractor.setNames(new String[] { "lastName", "firstName", "counter" });
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		return writer;
	}

}