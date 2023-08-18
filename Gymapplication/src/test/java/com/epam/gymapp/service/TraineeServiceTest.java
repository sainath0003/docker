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

import com.epam.gymapp.conveter.TraineeDtoConverter;
import com.epam.gymapp.dto.TraineeDto;
import com.epam.gymapp.dto.TraineeDtoForRead;
import com.epam.gymapp.dto.TraineeDtoForTrainerUpdate;
import com.epam.gymapp.dto.TrainerDtoForWrite;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.exception.TraineeException;
import com.epam.gymapp.exception.TraineeNotFoundException;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;
import com.epam.gymapp.model.User;
import com.epam.gymapp.repository.TraineeRepository;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {

	@Mock
	private TraineeDtoConverter converter;

	@Mock
	private UserServiceImpl userService;

	@Mock
	private TraineeRepository traineeRepository;

	@Mock
	private TrainerServiceImpl trainerService;

	@InjectMocks
	private TraineeServiceImpl traineeService;

	Trainee trainee;
	User user;
	Trainer trainer;
	TraineeDto traineeDto;
	Training training;
	TrainerDtoForWrite trainerDtoForWrite;
	TrainingDtoForWrite trainingDtoForWrite;
	TraineeDtoForRead traineeDtoForRead;
	List<Trainer> trainers;
	List<Training> trainings;

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
		traineeDtoForRead = new TraineeDtoForRead("sai", "sai", "1-1-2001", "lol", "sai@gmail.com", true, trainers);
		trainerDtoForWrite = new TrainerDtoForWrite(1, trainer.getUserName(), null);
		trainingDtoForWrite = new TrainingDtoForWrite(trainer.getUserName(), trainee.getUserName(),
				training.getTrainingName(), null, null, 12);

	}

	@Test
	 void testSaveTrainee() {

		when(converter.toUser(traineeDto)).thenReturn(user);
		when(converter.toTrainee(traineeDto)).thenReturn(trainee);
		when(userService.save(user)).thenReturn(user);

	
		UserDto result = traineeService.save(traineeDto);

		assertEquals(user.getUserName(), result.getUserName());
		assertEquals(user.getPassword(), result.getPassword());
	}

	@Test
	void testSaveNullTrainee() {

		assertThrows(TraineeException.class, () -> traineeService.save(null));

	}

	@Test
	void testaddTraining() {
		when(traineeRepository.save(any())).thenReturn(trainee);
		Trainee result = traineeService.addTraining(trainee, trainer, training);
		assertEquals(trainee, result);
	}

	@Test
	void testaddNullTraining() {
		when(traineeRepository.save(any())).thenThrow(NullPointerException.class);
		assertThrows(TraineeNotFoundException.class, () -> traineeService.addTraining(trainee, null, null));

	}

	@Test
	void testUpdate() {
		when(converter.toTrainee(any())).thenReturn(trainee);
		when(converter.toUser(any())).thenReturn(user);
		when(traineeRepository.findByUserName(anyString())).thenReturn(Optional.of(trainee));
		when(traineeRepository.save(any())).thenReturn(trainee);
		
		TraineeDto result = traineeService.update(traineeDto);
		
		assertEquals(traineeDto, result);
	}

	@Test
	void testNullUpdate() {
		when(converter.toTrainee(any())).thenReturn(trainee);
		when(converter.toUser(any())).thenReturn(user);
		when(traineeRepository.findByUserName(anyString())).thenReturn(Optional.empty());
			
		assertThrows(TraineeNotFoundException.class, () -> traineeService.update(traineeDto));

	}

	@Test
	void testDelete() {
		traineeService.delete("hello");
		Mockito.verify(userService).delete(anyString());
		Mockito.verify(traineeRepository).deleteByUserName(anyString());

	}

	@Test
	void testView() {
		when(traineeRepository.findByUserName(anyString())).thenReturn(Optional.of(trainee));
		when(converter.toTraineeDtoForRead(any(), any())).thenReturn(traineeDtoForRead);
		TraineeDtoForRead result = traineeService.view("sai@gmail.com");
		assertEquals(traineeDtoForRead, result);
	}

	@Test
	void testNullView() {
		
		when(traineeRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(TraineeNotFoundException.class, () -> traineeService.view("sai@gmail.com"));
	}

	@Test
	void testViewTraineeTrainingList() {

		String userName = "sai";
		when(traineeRepository.findByUserName(userName)).thenReturn(Optional.of(trainee));
		when(converter.toTrainingDtoForWrite(any())).thenReturn(trainingDtoForWrite);

		List<TrainingDtoForWrite> result = traineeService.viewTraineeTrainingList(userName);
		assertEquals(trainee.getTrainingList().get(0).getTrainingName(), result.get(0).getTrainingName());

	}

	@Test
	void testViewNullTraineeTrainingList() {

	
		when(traineeRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(TraineeNotFoundException.class, () -> traineeService.viewTraineeTrainingList("sai@gmail.com"));

	}

	@Test
	void testGetTraineeByUserName() {
		when(traineeRepository.findByUserName(anyString())).thenReturn(Optional.of(trainee));
		Trainee result = traineeService.getTraineeByUserName("sai");		
		assertEquals(trainee, result);
	}

	@Test
	void testNullGetTraineeByUserName() {
		
		when(traineeRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(TraineeNotFoundException.class, () -> traineeService.view("sai@gmail.com"));
	}

	@Test
	void testGetNotAssignedTrainers() {

		Optional<Trainee> optionalTrainee = Optional.of(trainee);
		optionalTrainee.get().setTrainersList(new ArrayList<>());
		when(traineeRepository.findByUserName(anyString())).thenReturn(optionalTrainee);
		when(trainerService.viewAllTrainers()).thenReturn(trainers);
		when(converter.toTrainerDtoForWrite(any())).thenReturn(trainerDtoForWrite);

		List<TrainerDtoForWrite> result = traineeService.getNotAssignedTrainers("sai");
		assertEquals(trainerDtoForWrite.getUserName(), result.get(0).getUserName());

	}

	@Test
	void testGetNotAssignedTrainersforNotExixtingTrainee() {


		when(traineeRepository.findByUserName(anyString())).thenReturn(Optional.empty());
		assertThrows(TraineeNotFoundException.class, () -> traineeService.getNotAssignedTrainers("sai@gmail.com"));

	}

	@Test
	void testUpdateTraineesTrainersList() {

		TraineeDtoForTrainerUpdate traineeDto = new TraineeDtoForTrainerUpdate();
		traineeDto.setUserName("testUser");
		List<String> trainerNames = new ArrayList<>();
		trainerNames.add("hello");
		traineeDto.setTrainers(trainerNames);

		when(traineeRepository.findByUserName(traineeDto.getUserName())).thenReturn(Optional.of(trainee));
		when(trainerService.getTrainerByUserName(anyString())).thenReturn(trainer);
		when(converter.toTrainerDtoForWrite(any())).thenReturn(trainerDtoForWrite);
		List<TrainerDtoForWrite> result = traineeService.updateTraineesTrainersList(traineeDto);

		assertEquals(trainerDtoForWrite, result.get(0));
	}

	@Test
	void testUpdateNullTraineesTrainersList() {

		TraineeDtoForTrainerUpdate traineeDto = new TraineeDtoForTrainerUpdate();
		traineeDto.setUserName("testUser");
		List<String> trainerNames = new ArrayList<>();
		trainerNames.add("hello");
		traineeDto.setTrainers(trainerNames);

		when(traineeRepository.findByUserName(traineeDto.getUserName())).thenReturn(Optional.empty());
		assertThrows(TraineeNotFoundException.class, () -> traineeService.updateTraineesTrainersList(traineeDto));

	}
}
