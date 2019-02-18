package com.siddu.app;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.siddu.fileprocess.Student;
import com.siddu.fileprocess.StudentItemWriter;
import com.siddu.springbatch.MyTaskOne;
import com.siddu.springbatch.MyTaskThree;
import com.siddu.springbatch.MyTaskTwo;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.siddu")
@EntityScan("com.siddu")
@EnableJpaRepositories("com.siddu")
// @RestController
@RequestMapping("test")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @CrossOrigin(origins = "*")
	@RequestMapping(value = "sample", method = RequestMethod.GET)
	public String test() {
		return "This is dummy resposne";
	}

	/*
	 * Two do the parallel execution of the jobs we need to configure the
	 * taskExecutor and jobLauncher
	 */

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(15);
		taskExecutor.setMaxPoolSize(20);
		taskExecutor.setQueueCapacity(30);
		return taskExecutor;
	}

	@Bean
	public JobLauncher jobLauncher(ThreadPoolTaskExecutor taskExecutor, JobRepository jobRepository) {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setTaskExecutor(taskExecutor);
		jobLauncher.setJobRepository(jobRepository);
		return jobLauncher;
	}

	// Creation of bean for the classes
	@Bean
	MyTaskOne createMyTaskOne() {
		return new MyTaskOne();
	}

	@Bean
	MyTaskTwo createMyTaskTwo() {
		return new MyTaskTwo();
	}

	@Bean
	MyTaskThree createMyTaskThree() {
		return new MyTaskThree();
	}

	

}
