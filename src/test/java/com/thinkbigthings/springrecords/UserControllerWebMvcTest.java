package com.thinkbigthings.springrecords;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thinkbigthings.springrecords.config.ConfigurationRecord;
import com.thinkbigthings.springrecords.dto.CreateUser;
import com.thinkbigthings.springrecords.user.UserController;
import com.thinkbigthings.springrecords.user.UserService;
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
// almost of the full stack is used, and your code will be called in exactly the same way as if
// it were processing a real HTTP request but without the cost of starting the server
//   HTTP request mapping
//   Input field validation
//   Serialization / Deserialization
//   Error handling

@WebMvcTest(UserController.class)
public class UserControllerWebMvcTest {

	private ObjectMapper mapper = new ObjectMapper();
	private ObjectWriter jsonWriter = mapper.writerFor(CreateUser.class);

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	// Records are always final, so a record cannot be a @MockBean or be mocked at all
	// unless you use the Mockito extension that allows mocking final classes (org.mockito:mockito-inline)
	// A record can implement an interface and THAT can be mocked
	@MockBean
	ConfigurationRecord config;


	// requires hibernate validator on classpath and @Valid on controller parameter
	// (@Validated on class allows for validation of path variables)

	@Test
	public void testRegistrationFailsSizeValidation() throws Exception {

		var badRegistration = new CreateUser("1", "1", "x@123.com");

		var reqBuilder = post("/registration")
				.content(jsonWriter.writeValueAsString(badRegistration))
				.contentType(MediaType.APPLICATION_JSON);

		// this tests that the validation is applied
		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testRegistrationPassesValidation() throws Exception {

		CreateUser registration = new CreateUser("12345", "12345", "x@123.com");

		var reqBuilder = post("/registration")
				.content(jsonWriter.writeValueAsString(registration))
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status().isOk());
	}
}