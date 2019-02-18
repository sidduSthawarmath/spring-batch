package com.siddu.springbatch;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class MyTaskTwo implements Tasklet {

	@Autowired
	JdbcTemplate JdbcTemplate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("Started Task One..");

		for (int i = 0; i < 5; i++) {
			Thread.sleep(1000);
			System.out.println("processing..");
		}

		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HHmmss");
		
		
		JdbcTemplate.update("insert into  task_two (updated_time) values('"+simpleDateFormat.format(new Date())+"')");
		
		System.out.println("END Task One..");

		return RepeatStatus.FINISHED;
	}

}
