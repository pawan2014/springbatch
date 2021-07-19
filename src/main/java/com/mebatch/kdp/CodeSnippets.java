package com.mebatch.kdp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class CodeSnippets {

	@Bean
	public FlatFileItemReader<User> flatFileItemReader() {
		FlatFileItemReader<User> reader = new FlatFileItemReader<>();
		DefaultLineMapper<User> lineMapper = new DefaultLineMapper<User>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "firstName", "lastName" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
					{
						setTargetType(User.class);
					}
				});
			}
		};
		reader.setResource(new ClassPathResource("sample-data.csv"));
		reader.setLineMapper(lineMapper);
		return reader;
	}
	/*
	 * mapper.setFieldSetMapper(fieldSet -> { TestData data = new TestData();
	 * data.setId(fieldSet.readInt("id"));
	 * data.setField1(fieldSet.readString("field1"));
	 * data.setField2(fieldSet.readString("field2"));
	 * data.setField3(fieldSet.readString("field3")); return data; });
	 */

	@Bean
	JdbcBatchItemWriter<Person> jdbcBatchItemWriter(DataSource h2) {
		JdbcBatchItemWriter<Person> w = new JdbcBatchItemWriter<>();
		w.setDataSource(h2);
		w.setSql("insert into Person( first) values ( :first, )");
		w.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		return w;
	}

	@Bean
	@Qualifier("jdbcBatchItemWriter")
	public JdbcBatchItemWriter<Person> jdbcBatchItemWriter1(DataSource h2) {
		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.setSql("insert into people (person_id, first_name, last_name) VALUES (uuid(), :firstName, :lastName)");
		writer.setDataSource(h2);
		return writer;
	}

	public void FileDelete() {
		Resource directory = null;
		try (Stream<Path> walk = Files.walk(Paths.get(directory.getFile().getPath()))) {
			walk.filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);
		} catch (IOException e) {

			throw new UnexpectedJobExecutionException("unable to delete files");
		}
	}

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step ourBatchStep() {
		return stepBuilderFactory.get("stepPackPub1").tasklet(new Tasklet() {
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
				return null;
			}
		}).build();
	}

}
