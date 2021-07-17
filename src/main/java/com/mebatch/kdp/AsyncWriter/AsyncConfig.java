package com.mebatch.kdp.AsyncWriter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.mebatch.kdp.Person;

@Configuration
public class AsyncConfig {

	@Bean
	public ItemWriter<Person> writer() {
		FlatFileItemWriter<Person> writer = new FlatFileItemWriter();
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
