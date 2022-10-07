package com.thinkbigthings.springrecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Application {

	// TODO try new mapper config, can we put it on a Repository?
	// https://docs.spring.io/spring-data/jdbc/docs/3.0.0-M6/reference/html/#jdbc.query-methods.at-query.custom-rowmapper

	// TODO cleanup code
	// change com.thinkbigthings to org or io.springone22
	// review all tests: BasicTest, DtoTest, and methods in the User tests
	// pin library versions, try Spring Boot 3

	// TODO cleanup presentation
	// UserDetails could be a spring record, but Spring provides a rich implementation already
	// put github url in presentation
	// Spring Boot 3 requires Java 17
	// mention the other use cases, don't need all demos or slides for the other topics
	// update README


	// TODO add pw validation inside canonical constructor
	// (demo of compact constructor, mention it's called while unmarshalling, applies to test objects/etc)
	// https://blogs.oracle.com/javamagazine/post/diving-into-java-records-serialization-marshaling-and-bean-state-validation

	// TODO record deconstruction in 19 (answers why not protobuf/lombok/etc)
	// https://openjdk.org/jeps/405

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
