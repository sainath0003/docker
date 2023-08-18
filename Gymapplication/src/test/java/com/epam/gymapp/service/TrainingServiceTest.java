package com.epam.gymapp.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapp.conveter.TrainingDtoConverter;
import com.epam.gymapp.dto.TraineeDto;
import com.epam.gymapp.dto.TrainingDtoForNewTraining;
import com.epam.gymapp.exception.TrainingException;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;
import com.epam.gymapp.model.User;
import com.epam.gymapp.repository.TrainingRepository;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

	@Mock

	TrainingRepository trainingRepository;

	@Mock
	TrainerServiceImpl trainerService;
	@Mock
	TraineeServiceImpl traineeService;
	@Mock
	TrainingDtoConverter converter;

	@InjectMocks
	private TrainingServiceImpl trainingService;

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
	 void testaddNewTraining() {
		
		when(traineeService.getTraineeByUserName(anyString())).thenReturn(trainee);
		when(trainerService.getTrainerByUserName(anyString())).thenReturn(trainer);
		when(converter.toTraining(any(), any(), any())).thenReturn(training);
		boolean result = trainingService.addNewTraining(trainingDto);
		assertTrue(result);

		}

	@Test
	 void testaddNewTrainingUnsucessfull() {
		
		when(traineeService.getTraineeByUserName(anyString())).thenThrow(NullPointerException.class);
		assertThrows(TrainingException.class, ()->trainingService.addNewTraining(trainingDto));		

		}

}
