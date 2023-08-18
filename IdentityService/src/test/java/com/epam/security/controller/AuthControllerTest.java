package com.epam.security.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.security.dto.AuthRequest;
import com.epam.security.entity.UserCredential;
import com.epam.security.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AuthController.class)
class AuthControllerTest {

	@MockBean
	private AuthService service;

	@MockBean
	private AuthenticationManager authenticationManager;

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	Authentication authenticate;

	UserCredential credential;

	@WithMockUser(value = "saiprani123@gmail.com", password = "java.util.stream.DoublePipeline$Head@44eef651")
	@Test
	void testGetToken() throws Exception {
		AuthRequest authRequest = new AuthRequest("saiprani123@gmail.com",
				"java.util.stream.DoublePipeline$Head@44eef651");
		String input = "hello";
		Mockito.when(authenticationManager.authenticate(any())).thenReturn(authenticate);
		Mockito.when(authenticationManager.authenticate(any()).isAuthenticated()).thenReturn(true);
		Mockito.when(service.generateToken(any())).thenReturn(input);
		MvcResult mvcResult = mockMvc
				.perform(post("/auth/token").content(new ObjectMapper().writeValueAsString(authRequest))
						.contentType(MediaType.APPLICATION_JSON).with(csrf()))
				.andReturn();

		assertEquals(input, mvcResult.getResponse().getContentAsString());
		assertNotNull(mvcResult);

	}

	@WithMockUser(value = "saiprani123@gmail.com", password = "java.util.stream.DoublePipeline$Head@44eef651")
	@Test
	void testGetTokenUnsucessful() throws Exception {
		AuthRequest authRequest = new AuthRequest("saiprani123@gmail.com",
				"java.util.stream.DoublePipeline$Head@44eef651");

		Mockito.when(authenticationManager.authenticate(any())).thenReturn(authenticate);
		Mockito.when(authenticationManager.authenticate(any()).isAuthenticated()).thenReturn(false);

		MvcResult mvcResult = mockMvc
				.perform(post("/auth/token").content(new ObjectMapper().writeValueAsString(authRequest))
						.contentType(MediaType.APPLICATION_JSON).with(csrf()))
				.andReturn();

		assertEquals("invalid access", mvcResult.getResponse().getContentAsString());
		assertNotNull(mvcResult);

	}

	@WithMockUser(value = "saiprani123@gmail.com", password = "java.util.stream.DoublePipeline$Head@44eef651")
	@Test
	void testAddNewUser() throws Exception, Exception {
		UserCredential credential = new UserCredential(1, "sai", "sai");
		when(service.saveUser(any())).thenReturn("user added to the system");
		MvcResult mvcResult = mockMvc
				.perform(post("/auth/register").content(new ObjectMapper().writeValueAsString(credential))
						.contentType(MediaType.APPLICATION_JSON).with(csrf()))
				.andReturn();
		assertEquals("user added to the system", mvcResult.getResponse().getContentAsString());
		assertNotNull(mvcResult);
	}

	@WithMockUser(value = "saiprani123@gmail.com", password = "java.util.stream.DoublePipeline$Head@44eef651")
	@Test
	void testvalidateToken() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/auth/validate").param("token", "123")).andReturn();

		String result = mvcResult.getResponse().getContentAsString();

		assertEquals("Token is valid", result);

	}

}
