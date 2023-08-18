package com.epam.gymapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.dto.UserDtoForPasswordChange;
import com.epam.gymapp.exception.UserNotFoundException;
import com.epam.gymapp.model.User;
import com.epam.gymapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

	@MockBean
	UserService userService;
	@Autowired
	private MockMvc mockMvc;

	User user;
	UserDto userDto;

	@BeforeEach
	void setup() {
		user = new User(1, "sai", "sai", "sai@gmail.com", "123456", true, null, null, null);
		userDto = new UserDto("sai", "123456");
	}

	@Test
	void testLogin() throws Exception {
		UserDto userDto = new UserDto("sai", "123456");

		Mockito.when(userService.login(any())).thenReturn(true);

		MvcResult mvcResult = mockMvc.perform(post("/gymapp/user/login")
				.content(new ObjectMapper().writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertNotNull(mvcResult);

	}

	@Test
	void testUnsucessfullLogin() throws Exception {
		UserDto userDto = new UserDto("sai", "123456");

		Mockito.when(userService.login(any())).thenThrow(UserNotFoundException.class);

		MvcResult mvcResult = mockMvc.perform(post("/gymapp/user/login")
				.content(new ObjectMapper().writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
		assertNotNull(mvcResult);

	}

	@Test
	void testUpdateLoginInfo() throws Exception {
		UserDtoForPasswordChange userDto = new UserDtoForPasswordChange("sai", "123456","123");

		Mockito.when(userService.changeLogin(any())).thenReturn(true);

		MvcResult mvcResult = mockMvc.perform(put("/gymapp/user/changepassword")
				.content(new ObjectMapper().writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertNotNull(mvcResult);

	}

	@Test
	void testUpdateLoginInfowithWrongInfo() throws Exception {
		UserDtoForPasswordChange userDto = new UserDtoForPasswordChange("sai", "123456","123");

		Mockito.when(userService.changeLogin(any())).thenThrow(UserNotFoundException.class);

		MvcResult mvcResult = mockMvc.perform(put("/gymapp/user/changepassword")
				.content(new ObjectMapper().writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();
		assertNotNull(mvcResult);

	}
	
	@Test
	void testSetStatus() throws Exception {
		UserDto userDto = new UserDto("sai", "123456");

		Mockito.when(userService.setStatus(any())).thenReturn(true);

		MvcResult mvcResult = mockMvc.perform(get("/gymapp/user/status")
				.content(new ObjectMapper().writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		assertNotNull(mvcResult);

	}


}
