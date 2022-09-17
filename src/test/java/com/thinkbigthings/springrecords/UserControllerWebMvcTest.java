package com.thinkbigthings.springrecords;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerWebMvcTest {

	private ObjectMapper mapper = new ObjectMapper();
	private ObjectWriter jsonWriter = mapper.writerFor(RegistrationRequest.class);

	@Autowired
	private MockMvc mockMvc;

//	@MockBean
//	private UserService service;

	@Test
	public void testControllerAcceptsRegistration() throws Exception {

//		when(service.greet()).thenReturn("Hello, Mock");
//		RegistrationRequest registration = new RegistrationRequest("", "", "");
		RegistrationRequest registration = new RegistrationRequest("12345", "12345", "x@123.com");

		var reqBuilder = post("/registration")
				.content(jsonWriter.writeValueAsString(registration))
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(reqBuilder)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("got it!")));
	}
}