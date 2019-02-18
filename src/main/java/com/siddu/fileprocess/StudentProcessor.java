package com.siddu.fileprocess;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class StudentProcessor implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student student) throws Exception {

		if ("Ram".equals(student.getFirstName())) {
			System.out.println(student.getFirstName() +" missed in processor");
			student = null;
		}
		return student;
	}

}
