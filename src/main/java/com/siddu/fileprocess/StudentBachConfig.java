package com.siddu.fileprocess;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.siddu.springbatch.Test;

@Configuration
@EnableBatchProcessing
public class StudentBachConfig {

	@Autowired
	StudentItemReader studentItemReader;

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	StudentItemWriter studentItemWriter;

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	StudentProcessor studentProcessor;


	@Bean
	public Step step() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		return stepBuilderFactory.get("step").<Student, Student>chunk(1).reader(studentItemReader.read())
				 .processor(studentProcessor)
				.writer(studentItemWriter).faultTolerant().skipLimit(1)
				.skip(MySQLIntegrityConstraintViolationException.class).build();
	}

	//@Scheduled(cron = "*/1 * * * * *")
	@Bean
	public Test perform1() throws Exception {
		JobParameters param = new JobParametersBuilder().addString("JobID1", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		 jobLauncher.run(jobs.get("studentDataReadJob").incrementer(new RunIdIncrementer()).start(step()).build(), param);
	return null;
	}

}
