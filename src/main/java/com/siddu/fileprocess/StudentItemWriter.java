package com.siddu.fileprocess;

import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@SuppressWarnings("hiding")
@Component
public class StudentItemWriter implements ItemWriter<Student> {

	@Autowired
	JdbcTemplate JdbcTemplate;

	@Override
	public void write(List<? extends Student> items) throws Exception {
		if (items.get(0) != null) {
			Student student = items.get(0);
		//	System.out.println(student.getFirstName() + "\t" + student.getLastName());
			JdbcTemplate.update("insert into  student (first_name,last_name) values('" + student.getFirstName() + "','"
					+ student.getLastName() + "')");

		}

	}

}
