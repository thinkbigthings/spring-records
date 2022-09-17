package com.thinkbigthings.springrecords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationPropertiesScan
@SpringBootApplication
public class Application {

	// TODO check in to github

	// TODO do builders for test data
	// TODO records as response bodies - do builders here and

	// TODO finish records as config objects and demo
	// https://stackoverflow.com/questions/70683554/nested-spring-configuration-configurationproperties-in-records
	// https://youribonnaffe.github.io/java/records/spring/2021/01/10/records-spring-boot.html

	// TODO add pw validation inside canonical constructor
	// (demo of compact constructor, mention it's called while unmarshalling, applies to test objects/etc)
	// https://blogs.oracle.com/javamagazine/post/diving-into-java-records-serialization-marshaling-and-bean-state-validation


	// TODO records as repository response (already mapped) Can we nest records in query?
	// will need postgres and flyway

	// TODO record deconstruction in 19 (answers why not protobuf/lombok/etc)


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
