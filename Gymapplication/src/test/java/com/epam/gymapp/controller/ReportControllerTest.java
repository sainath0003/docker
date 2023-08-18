package com.epam.gymapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.kafka.KafkaProducer;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.service.Notificationservice;
import com.epam.gymapp.service.TrainerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ReportController.class)
class ReportControllerTest {

	@MockBean
	TrainerService trainerService;
	@MockBean
	Notificationservice notificationService;
	@MockBean
	KafkaProducer kafkaProducer;
	@Autowired
	private MockMvc mockMvc;

	ReportDto reportDto = new ReportDto("sai", "sai", "sai", true, 12, null);
	Trainer trainer = new Trainer(2, null, "sai", null, null, null);

	@Test
	void testView() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/gymapp/reportservice/view").param("trainerUserName", "sai"))
				.andExpect(status().isOk()).andReturn();
		Mockito.verify(kafkaProducer).sendMessage(anyString());

		assertNotNull(mvcResult);

	}

	@Test
	void testSave() throws Exception {

		Mockito.when(trainerService.getTrainerByUserName(any())).thenReturn(trainer);

		MvcResult mvcResult = mockMvc.perform(post("/gymapp/reportservice/save")
				.content(new ObjectMapper().writeValueAsString(reportDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertNotNull(mvcResult);

	}
	@Test
	void testSaveForNullUser() throws Exception {

		Mockito.when(trainerService.getTrainerByUserName(any())).thenReturn(null);

		MvcResult mvcResult = mockMvc.perform(post("/gymapp/reportservice/save")
				.content(new ObjectMapper().writeValueAsString(reportDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andReturn();

		assertNotNull(mvcResult);

	}

}
