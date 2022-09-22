package com.thinkbigthings.springrecords;

import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserControllerUnitTest {

	private Validator validator;

	private ConfigurationRecord confi = mock(ConfigurationRecord.class);

	@BeforeEach
	public void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void controllerParametersAreInvalid() {

		var invalidRequest = new RegistrationRequest("", "", "");
		var violations = validator.validate(invalidRequest);

		assertEquals(2, violations.size());

	}

}