package com.thinkbigthings.springrecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Application {

	// TODO show records as repository response can be returned as already mapped, test nested records

	// TODO cleanup
	// change com.thinkbigthings to org
	// put github url in presentation
	// review all tests
	// add to presentation: UserDetails could be a spring record, but Spring provides a rich implementation already
	// Spring Boot 3 requires Java 17
	// mention the other use cases, don't need all demos or slides for the other topics
	// update README


	// TODO do builders for test data, and for records as response bodies
	// show formatting for nested constructors
	// show withers and delegating constructors for default values
	// try out the libraries?

	// TODO pin library versions, try Spring Boot 3

	// TODO try with spring data JDBC?

	// TODO add pw validation inside canonical constructor
	// (demo of compact constructor, mention it's called while unmarshalling, applies to test objects/etc)
	// https://blogs.oracle.com/javamagazine/post/diving-into-java-records-serialization-marshaling-and-bean-state-validation

	// TODO record deconstruction in 19 (answers why not protobuf/lombok/etc)
	// https://openjdk.org/jeps/405

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
