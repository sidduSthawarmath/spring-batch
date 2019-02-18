package com.siddu.fileprocess;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class StudentItemReader implements ItemReader<ItemReader<Student>> {

	@Override
	public ItemReader<Student> read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		FlatFileItemReader<Student> reader = new FlatFileItemReader<Student>();
		reader.setLinesToSkip(1);// first line is title definition
		reader.setResource(new ClassPathResource("inputData.txt"));
		reader.setLineMapper(lineMapper());
		return reader;

	}

	public LineMapper<Student> lineMapper() {
		DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<Student>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] { "firstName", "lastName" });
		BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<Student>();
		fieldSetMapper.setTargetType(Student.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(studentFieldSetMapper());
		return lineMapper;
	}

	@Bean
	public StudentFieldSetMapper studentFieldSetMapper() {
		return new StudentFieldSetMapper();
	}
}
