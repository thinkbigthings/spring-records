package com.thinkbigthings.springrecords;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// WebMvcTest (as opposed to pure unit test) is good for testing:
//  HTTP request mapping
//  Deserialization
//  Input field validation
//  Serialization
//  Error handling

@WebMvcTest(UserController.class)
public class UserControllerWebMvcTest {

	private ObjectMapper mapper = new ObjectMapper();
	private ObjectWriter jsonWriter = mapper.writerFor(RegistrationRequest.class);

	@Autowired
	private MockMvc mockMvc;

	// Records are always final, so a record cannot be a @MockBean
	// unless you use the Mockito extension that allows mocking final classes
	@MockBean
	ConfigurationRecord config;

	private RegistrationRequest registration = new RegistrationRequest("12345", "12345", "x@123.com");

	// requires hibernate validator on classpath and @Valid on controller parameter
	// (@Validated on class allows for validation of path variables)

	@Test
	public void testRegistrationFailsValidation() throws Exception {

		registration = registration.withUsername("1");

		var reqBuilder = post("/registration")
				.content(jsonWriter.writeValueAsString(registration))
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testRegistrationPassesValidation() throws Exception {


		var reqBuilder = post("/registration")
				.content(jsonWriter.writeValueAsString(registration))
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status().isOk());
	}
}