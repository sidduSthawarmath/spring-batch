package com.siddu.springbatch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	MyTaskOne myTaskOne;

	@Autowired
	MyTaskTwo myTaskTwo;

	@Autowired
	MyTaskThree myTaskThree;

	/* This step is used to store the data into task_one table */
	@Bean
	public Step stepOne() {
		return steps.get("stepOne").tasklet(myTaskOne).build();
	}

	/* This step is used to store the data into task_two table */
	@Bean
	public Step stepTwo() {
		return steps.get("stepTwo").tasklet(myTaskTwo).build();
	}

	/* This step is used to store the data into task_three table */
	@Bean
	public Step stepThree() {
		return steps.get("stepThree").tasklet(myTaskThree).build();
	}

	// This job will execute every 5 seconds
	@Scheduled(cron = "*/5 * * * * *")
	public void performTaskOneAndTwo() throws Exception {
		JobParameters param = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(jobs.get("JobID").incrementer(new RunIdIncrementer()).start(stepOne()).next(stepTwo()).build(),
				param);
	}

	// This job will execute every 1 seconds
	@Scheduled(cron = "*/1 * * * * *")
	public void perform1() throws Exception {
		JobParameters param = new JobParametersBuilder().addString("JobID1", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(jobs.get("JobID1").incrementer(new RunIdIncrementer()).start(stepThree()).build(), param);
	}
}
