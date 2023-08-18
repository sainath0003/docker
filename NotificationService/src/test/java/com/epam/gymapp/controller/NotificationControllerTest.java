package com.epam.gymapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.gymapp.exception.NotificationException;
import com.epam.gymapp.service.NotificationService;

@WebMvcTest(controllers = NotificationController.class)
class NotificationControllerTest {

	@MockBean
	NotificationService notificationService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testSendMail() throws Exception {

		MvcResult mvcResult = mockMvc.perform(
				get("/notification/sendemail").param("recipient", "sai").param("subject", "sai1").param("body", "sai2"))
				.andExpect(status().isOk()).andReturn();

		assertNotNull(mvcResult);

	}

	@Test
	void testSendMailUnsucessful() throws Exception {
		doThrow(new NotificationException("hello")).when(notificationService).sendEmail(any());
		
		MvcResult mvcResult = mockMvc.perform(
				get("/notification/sendemail").param("recipient", "sai").param("subject", "sai1").param("body", "sai2"))
				.andExpect(status().isBadRequest()).andReturn();

		assertNotNull(mvcResult);

	}
}
