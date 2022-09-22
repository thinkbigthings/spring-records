package com.thinkbigthings.springrecords;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thinkbigthings.springrecords.dto.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
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

	// A record cannot be a @MockBean because records are always final
//	@TestConfiguration
//	static class TestConfig {
//
//		// this will be injected into the Test class, since it's constructor bound it can't be a @Bean
//		ConfigurationRecord configurationRecord() {
//			return new ConfigurationRecord(1, new ConfigurationRecord.UserField("a", 2), new ConfigurationRecord.UserPage(20));
//		}
//	}

//	@MockBean
//	private UserService;



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