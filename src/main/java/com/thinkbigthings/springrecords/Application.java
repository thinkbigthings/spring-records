package com.thinkbigthings.springrecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Application {

	// TODO records as repository response (already mapped) Can we nest records in query?
	// will need postgres and flyway
	// write integration tests

	// TODO do builders for test data, and for records as response bodies
	// show formatting for nested constructors
	// show withers
	// try out the libraries?

	// TODO pin library versions, try Spring Boot 3

	// TODO add pw validation inside canonical constructor
	// (demo of compact constructor, mention it's called while unmarshalling, applies to test objects/etc)
	// https://blogs.oracle.com/javamagazine/post/diving-into-java-records-serialization-marshaling-and-bean-state-validation

	// TODO record deconstruction in 19 (answers why not protobuf/lombok/etc)
	// https://openjdk.org/jeps/405

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
