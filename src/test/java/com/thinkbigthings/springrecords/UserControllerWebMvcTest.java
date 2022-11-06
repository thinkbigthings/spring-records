package com.thinkbigthings.springrecords;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thinkbigthings.springrecords.config.ConfigurationRecord;
import com.thinkbigthings.springrecords.dto.UserRecord;
import com.thinkbigthings.springrecords.user.UserController;
import com.thinkbigthings.springrecords.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.emptySet;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// almost of the full stack is used, and your code will be called in exactly the same way
// as if it were processing a real HTTP request but without the cost of starting the server
// WebMvcTest (as opposed to pure unit test) is good for testing:
//   HTTP request mapping
//   Input field validation
//   Serialization / Deserialization
//   Error handling

@WebMvcTest(UserController.class)
public class UserControllerWebMvcTest {

	private ObjectMapper mapper = new ObjectMapper();
	private ObjectWriter jsonWriter = mapper.writerFor(UserRecord.class);

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	// Records are always final, so a record cannot be a @MockBean or be mocked at all
	// Resolutions:
	// - use the Mockito extension that allows mocking final classes (org.mockito:mockito-inline)
	// - use a record that implements an interface and THAT can be mocked
	@MockBean
	ConfigurationRecord config;


	// requires hibernate validator on classpath and @Valid on controller parameter
	// (@Validated on class allows for validation of path variables)

	@Test
	public void testRegistrationFailsSizeValidation() throws Exception {

		UserRecord badUser = new UserRecord("1", "x", emptySet());

		var reqBuilder = post("/user")
				.content(jsonWriter.writeValueAsString(badUser))
				.contentType(MediaType.APPLICATION_JSON);

		// this tests that the validation is applied
		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testRegistrationPassesValidation() throws Exception {

		UserRecord goodUser = new UserRecord("12345", "x@123.com", emptySet());

		var reqBuilder = post("/user")
				.content(jsonWriter.writeValueAsString(goodUser))
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status().isOk());
	}
}