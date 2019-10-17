package org.sample.codetest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sample.codetest.PersonModel.Gender;

public class Processor {
	public static Stream<PersonModel> process(Stream<PersonModel> model) {
		 Map<String, Map<Gender, Double>> collect = model
				 //.peek(System.out::println)
				.collect(Collectors.groupingBy(PersonModel::getCounty, Collectors.groupingBy(PersonModel::getGender,
						Collectors.averagingDouble(PersonModel::getSalary))));
		 return collect.entrySet().stream().flatMap(m -> {
			 String country = m.getKey();
			 return m.getValue().entrySet().stream().map(v -> {
				 PersonModel p = new PersonModel();
				 p.setCounty(country);
				 p.setGender(v.getKey());
				 p.setSalary(new Float(v.getValue()));
				 return p;
			 });
		 }).peek(System.out::println).sorted(Comparator.comparing(PersonModel::getCounty).thenComparing(PersonModel::getGender));
	}

	public static void main(String[] args) throws IOException{
		// Get data from CSV file itself.
		// For testing, I am creating mock data
		Stream<PersonModel> collect = process(PersonModel.mock().limit(100));
		
		List<String> collect2 = (List<String>)collect.map(PersonModel::toString).collect(Collectors.toList());
		
		
		// Path where output file will be placed
		Path file = Paths.get("C:\\Users\\e5545941\\eclipse-workspace\\otp-api\\csv-parser\\output.csv");
		Files.write(file, collect2, StandardCharsets.UTF_8);
	}
}
