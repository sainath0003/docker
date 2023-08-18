package com.epam.gymapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapp.conveter.TrainerDtoConverter;
import com.epam.gymapp.dto.TraineeDto;
import com.epam.gymapp.dto.TrainerDto;
import com.epam.gymapp.dto.TrainerDtoForRead;
import com.epam.gymapp.dto.TrainerDtoForWrite;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.exception.TrainerException;
import com.epam.gymapp.exception.TrainerNotFoundException;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;
import com.epam.gymapp.model.User;
import com.epam.gymapp.repository.TrainerRepository;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

	@Mock
	private TrainerDtoConverter converter;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private TrainerRepository trainerRepository;

	@InjectMocks
	private TrainerServiceImpl trainerService;

	Trainee trainee;
	User user;
	Trainer trainer;
	TraineeDto traineeDto;
	TrainerDto trainerDto;
	Training training;
	TrainerDtoForWrite trainerDtoForWrite;
	TrainingDtoForWrite trainingDtoForWrite;
	TrainerDtoForRead trainerDtoForRead;
	List<Trainer> trainers;
	List<Trainee> trainees;
	List<Training> trainings;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		traineeDto = new TraineeDto("sai", "sai", "12-12-2001", "hello", "sai@gmail.com");
		trainerDto = new TrainerDto("sai", "sai", "Yoga", "sai@gmail.com");
		user = new User(1, "sai", "sai", "sai@gmail.com", "123456", true, null, null, null);
		trainer = new Trainer(2, user, "sai", null, null, null);
		trainee = new Trainee(1, user, "sai@gmail.com", "hello", "hello", true, null, null);
		training = new Training(2, trainer, trainee, trainer.getUserName(), null, null, 34);
		trainers = new ArrayList<>();
		trainers.add(trainer);
		trainings = new ArrayList<>();
		trainings.add(training);
		trainees = new ArrayList<>();
		trainees.add(trainee);
		trainer.setTraineesList(trainees);
		trainer.setTrainingsList(trainings);
		trainee.setTrainersList(trainers);
		trainee.setTrainingList(trainings);
		user.setTrainee(trainee);
		trainerDtoForRead = new TrainerDtoForRead("sai", "sai", "Yoga", "sai@gmail.com", true, trainees);
		trainerDtoForWrite = new TrainerDtoForWrite(1, trainer.getUserName(), null);
		trainingDtoForWrite = new TrainingDtoForWrite(trainer.getUserName(), trainee.getUserName(),
				training.getTrainingName(), null, null, 12);

	}

	@Test
	 void testSaveTrainee() {

		when(converter.toUser(trainerDto)).thenReturn(user);
		when(converter.toTrainer(trainerDto)).thenReturn(trainer);
		when(userService.save(user)).thenReturn(user);

	
		UserDto result = trainerService.save(trainerDto);

		assertEquals(user.getUserName(), result.getUserName());
		assertEquals(user.getPassword(), result.getPassword());
	}

	@Test
	void testSaveNullTrainee() {

		assertThrows(TrainerException.class, () -> trainerService.save(null));

	}

	@Test
	void testaddTraining() {
		when(trainerRepository.save(any())).thenReturn(trainer);
		Trainer result = trainerService.addTraining(trainee, trainer, training);
		assertEquals(trainer, result);
	}

	@Test
	void testaddNullTraining() {
		when(trainerRepository.save(any())).thenThrow(NullPointerException.class);
		assertThrows(TrainerException.class, () -> trainerService.addTraining(null, trainer, null));

	}

	@Test
	void testUpdate() {
		when(converter.toTrainer(any())).thenReturn(trainer);
		when(converter.toUser(any())).thenReturn(user);
		when(trainerRepository.findByUserName(anyString())).thenReturn(Optional.of(trainer));
		when(trainerRepository.save(any())).thenReturn(trainer);
		
		TrainerDto result = trainerService.update(trainerDto);
		
		assertEquals(trainerDto, result);
	}

	@Test
	void testNullUpdate() {
		when(converter.toTrainer(any())).thenReturn(trainer);
		when(converter.toUser(any())).thenReturn(user);
		when(trainerRepository.findByUserName(anyString())).thenReturn(Optional.empty());
			
		assertThrows(TrainerNotFoundException.class, () -> trainerService.update(trainerDto));

	}

	@Test
	void testDelete() {
		trainerService.delete("hello");
		Mockito.verify(userService).delete(anyString());
		Mockito.verify(trainerRepository).deleteByUserName(anyString());

	}

	@Test
	void testView() {
		when(trainerRepository.findByUserName(anyString())).thenReturn(Optional.of(trainer));
		when(converter.toTrainerDtoForRead(any(), any())).thenReturn(trainerDtoForRead);
		TrainerDtoForRead result = trainerService.view("sai@gmail.com");
		assertEquals(trainerDtoForRead, result);
	}

	@Test
	void testNullView() {
		
		when(trainerRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(TrainerNotFoundException.class, () -> trainerService.view("sai@gmail.com"));
	}

	@Test
	void testViewTraineeTrainingList() {

		String userName = "sai";
		when(trainerRepository.findByUserName(userName)).thenReturn(Optional.of(trainer));
		when(converter.toTrainingDtoForWrite(any())).thenReturn(trainingDtoForWrite);

		List<TrainingDtoForWrite> result = trainerService.viewTrainerTrainingList(userName);
		assertEquals(trainer.getTrainingsList().get(0).getTrainingName(), result.get(0).getTrainingName());

	}

	@Test
	void testViewNullTraineeTrainingList() {

	
		when(trainerRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(TrainerNotFoundException.class, () -> trainerService.viewTrainerTrainingList("sai@gmail.com"));

	}
	@Test
	void testGetTrainerByUserName() {
		when(trainerRepository.findByUserName(anyString())).thenReturn(Optional.of(trainer));
		Trainer result = trainerService.getTrainerByUserName("sai");		
		assertEquals(trainer, result);
	}

	@Test
	void testNullGetTraineeByUserName() {
		
		when(trainerRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(TrainerNotFoundException.class, () -> trainerService.view("sai@gmail.com"));
	}
	@Test
	void testViewAllTrainers() {
		trainerService.viewAllTrainers();
		Mockito.verify(trainerRepository).findAll();
	}
}
