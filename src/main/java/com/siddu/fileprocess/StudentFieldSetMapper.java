package com.siddu.fileprocess;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class StudentFieldSetMapper implements FieldSetMapper<Student> {

	@Override
	public Student mapFieldSet(FieldSet fieldSet) throws BindException {
		System.out.println("In Writer");
		Student student = new Student();
		student.setFirstName(fieldSet.readString("firstName"));
		student.setLastName(fieldSet.readString("lastName"));
		return student;
	}

}
