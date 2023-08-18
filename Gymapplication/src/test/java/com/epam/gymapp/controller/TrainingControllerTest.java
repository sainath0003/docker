package com.epam.gymapp.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.gymapp.dto.TraineeDto;
import com.epam.gymapp.dto.TrainingDtoForNewTraining;
import com.epam.gymapp.exception.TrainingException;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;
import com.epam.gymapp.model.User;
import com.epam.gymapp.service.Notificationservice;
import com.epam.gymapp.service.TrainingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = TrainingController.class)
class TrainingControllerTest {

	@MockBean
	TrainingService trainingService;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	Notificationservice notificationService;

	Trainee trainee;
	User user;
	Trainer trainer;
	TraineeDto traineeDto;
	Training training;
	List<Trainer> trainers;
	List<Training> trainings;
	TrainingDtoForNewTraining trainingDto;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		traineeDto = new TraineeDto("sai", "sai", "12-12-2001", "hello", "sai@gmail.com");
		user = new User(1, "sai", "sai", "sai@gmail.com", "123456", true, null, null, null);
		trainer = new Trainer(2, user, "sai", null, null, null);
		trainee = new Trainee(1, user, "sai@gmail.com", "hello", "hello", true, null, null);
		training = new Training(2, trainer, trainee, trainer.getUserName(), null, null, 34);
		trainers = new ArrayList<>();
		trainers.add(trainer);
		trainings = new ArrayList<>();
		trainings.add(training);

		trainee.setTrainersList(trainers);
		trainee.setTrainingList(trainings);
		user.setTrainee(trainee);
		trainingDto = new TrainingDtoForNewTraining("sai", "sai", "sai1", null, 34);
	}

	@Test
	void testadd() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/gymapp/training/add")
				.content(new ObjectMapper().writeValueAsString(trainingDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertNotNull(mvcResult);

	}

	@Test
	void testsaveForNullUser() throws Exception {
		
		when(trainingService.addNewTraining(any())).thenThrow(TrainingException.class);
		MvcResult mvcResult = mockMvc.perform(post("/gymapp/training/add")
				.content(new ObjectMapper().writeValueAsString(trainingDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
		assertNotNull(mvcResult);

	}

}
