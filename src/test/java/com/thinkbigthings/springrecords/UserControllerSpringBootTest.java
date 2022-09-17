package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerSpringBootTest {

//	@Autowired
//	private UserController controller;

	private static String registrationUrl;

	@BeforeAll
	public static void createReadOnlyTestData(@LocalServerPort int port) {

		registrationUrl = "http://localhost:" + port + "/registration";
	}

	// requires hibernate validator on classpath and @Valid on controller parameter
	// (@Validated on class allows for validation of path variables)

	// NOTE WebMvcTest does not apply validation to the controller parameters
	// testing that @Valid was put on your arguments needs to happen in a test that starts the server

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void controllerArgsHaveValidAnnotation() {

		RegistrationRequest invalidRegistration = new RegistrationRequest("", "", "");

		var response = restTemplate.postForEntity(registrationUrl, invalidRegistration, String.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}

}