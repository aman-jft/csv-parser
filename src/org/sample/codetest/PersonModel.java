package org.sample.codetest;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;



public class PersonModel {
	public String county;
	public String city;
	public float salary;
	public Gender gender;
	
	public enum Gender {
		MALE,
		FEMALE
	}
	public static Stream<PersonModel> mock() {
		
		AtomicInteger runCount = new AtomicInteger(0);
		return Stream.generate(()-> {
			return PersonModel.getInstance(runCount.getAndIncrement());
		});
		//return IntStream.rangeClosed(1, count).mapToObj(PersonModel::getInstance).collect(Collectors.toList());
	}

	public static PersonModel getInstance(Integer i) {
		PersonModel u = new PersonModel();
		//u.county = (i % 3)==0?null:"County-" + ((i % 3)+1);
		u.county = "County-" + ((i % 3)+1);
		u.city = "City-" + ((i % 3)+1);
		u.salary = 10000 * (new Random(i).nextFloat());
		u.gender = (i%2)==0?Gender.FEMALE:Gender.MALE;
		return u;
	}

	public static void main(String[] args) {
		PersonModel.mock().limit(100).forEach(System.out::println);
	}
	
	

	@Override
	public String toString() {
		return county+","+gender+","+salary;
	}

	public String getCounty() {
		return county==null?city:county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
