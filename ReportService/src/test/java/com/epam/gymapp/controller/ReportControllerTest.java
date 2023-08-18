package com.epam.gymapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.exception.ReportException;
import com.epam.gymapp.exception.ReportNotFoundException;
import com.epam.gymapp.model.Report;
import com.epam.gymapp.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ReportController.class)
class ReportControllerTest {

	@MockBean
	ReportService reportService;

	@Autowired
	private MockMvc mockMvc;

	Report report = new Report();
	ReportDto reportDto = new ReportDto("Sai", "sai", "sai", true, 34, null);

	@Test
	void testSendMail() throws Exception {

		Mockito.when(reportService.saveReport(any())).thenReturn(report);

		MvcResult mvcResult = mockMvc.perform(post("/reportservice/save")
				.content(new ObjectMapper().writeValueAsString(reportDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String user = new ObjectMapper().writeValueAsString(report);
		assertEquals(mvcResult.getResponse().getContentAsString(), user);
		assertNotNull(mvcResult);

	}

	@Test
	void testSendMailUnsucessfull() throws Exception {

		Mockito.when(reportService.saveReport(any())).thenThrow(ReportException.class);

		MvcResult mvcResult = mockMvc.perform(post("/reportservice/save")
				.content(new ObjectMapper().writeValueAsString(reportDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
		assertNotNull(mvcResult);

	}
	@Test
	void testView() throws Exception {
		Mockito.when(reportService.getReport(anyString())).thenReturn(List.of(report));

		MvcResult mvcResult = mockMvc.perform(get("/reportservice/view").param("trainerUserName", "sai"))
				.andExpect(status().isOk()).andReturn();

		String result = mvcResult.getResponse().getContentAsString();
		String question = new ObjectMapper().writeValueAsString(List.of(report));

		assertEquals(result, question);
	}
	@Test
	void testviewReports() throws Exception {
	when(reportService.getReport(anyString())).thenThrow(ReportNotFoundException.class);
		MvcResult mvcResult = mockMvc.perform(
				get("/notification/sendemail").param("trainerUserName", "sai"))
				.andExpect(status().isNotFound()).andReturn();

		assertNotNull(mvcResult);

	}
}
