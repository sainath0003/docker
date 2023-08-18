package com.epam.gymapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TraineeServiceImpl implements TraineeService {

	TraineeDtoConverter converter;
	UserServiceImpl userService;
	TraineeRepository traineeRepository;
	TrainerServiceImpl trainerService;

	@Autowired
	public TraineeServiceImpl(TraineeDtoConverter converter, UserServiceImpl userService,
			TraineeRepository traineeRepository, TrainerServiceImpl trainerService) {

		this.converter = converter;
		this.userService = userService;
		this.traineeRepository = traineeRepository;
		this.trainerService = trainerService;
	}

	public UserDto save(TraineeDto traineeDto) {

		log.info("Entered  save in TraineeServiceImpl");
		try {
			User user = converter.toUser(traineeDto);
			Trainee trainee = converter.toTrainee(traineeDto);
			user.setTrainee(trainee);

			user = userService.save(user);
			trainee.setUser(user);
			traineeRepository.save(trainee);
			log.info("Exited  save in TraineeServiceImpl");
			return new UserDto(user.getUserName(), user.getPassword());
		} catch (Exception e) {
			log.info("Trainee with given UserName Already Exists");
			throw new TraineeException("Trainee with given UserName Already Exists");
		}

	}
	
	

	public Trainee addTraining(Trainee trainee, Trainer trainer, Training training) {
		log.info("Entered  addTraining in TraineeServiceImpl");
		try {
			trainee.getTrainingList().add(training);
			trainee.getTrainersList().add(trainer);
			trainee.setTrainingList(trainee.getTrainingList());
			log.info("Exited  addTraining in TraineeServiceImpl");
			return traineeRepository.save(trainee);
		} catch (Exception e) {
			log.info("Trainee with given UserName does Not Exists");
			throw new TraineeNotFoundException("Trainee with given UserName does Not Exists");
		}
	}

	public TraineeDto update(TraineeDto traineeDto) {

		log.info("Entered  update in TraineeServiceImpl");
		Trainee trainee = converter.toTrainee(traineeDto);
		User user = converter.toUser(traineeDto);

		Trainee dbTrainee = traineeRepository.findByUserName(traineeDto.getEmail())
				.orElseThrow(() -> new TraineeNotFoundException("Trainee  with given UserName does not Exist"));

		trainee.setTraineeId(dbTrainee.getTraineeId());
		user.setUserId(dbTrainee.getUser().getUserId());
		trainee.setUser(user);

		user.setTrainee(trainee);
		userService.save(user);
		traineeRepository.save(trainee);
		log.info("Exited update in TraineeServiceImpl");
		return traineeDto;

	}

	public void delete(String userName) {
		log.info("Entered  delete in TraineeServiceImpl");
		userService.delete(userName);
		traineeRepository.deleteByUserName(userName);

	}

	public TraineeDtoForRead view(String userName) {
		log.info("Entered  view in TraineeServiceImpl");
		Trainee trainee = traineeRepository.findByUserName(userName)
				.orElseThrow(() -> new TraineeNotFoundException("Trainee with given UserName does not Exist"));

		return converter.toTraineeDtoForRead(trainee.getUser(), trainee);

	}

	public List<TrainingDtoForWrite> viewTraineeTrainingList(String userName) {

		log.info("Entered  viewTraineeTrainingList in TraineeServiceImpl");
		Trainee trainee = traineeRepository.findByUserName(userName)
				.orElseThrow(() -> new TraineeNotFoundException("Trainee with given UserName does not Exists "));
		List<TrainingDtoForWrite> trainingDtoList = new ArrayList<>();
		List<Training> trainings = null;

		trainings = trainee.getTrainingList();

		if (trainings != null) {

			trainingDtoList = trainings.stream().map(t -> converter.toTrainingDtoForWrite(t)).toList();
		}
		log.info("Exited  viewTraineeTrainingList in TraineeServiceImpl");
		return trainingDtoList;
	}

	public Trainee getTraineeByUserName(String userName) {

		log.info("Entered  getTraineeByUserName in TraineeServiceImpl");
		return traineeRepository.findByUserName(userName)
				.orElseThrow(() -> new TraineeNotFoundException(" Trainee with given UserName does not Exists"));

	}

	public List<TrainerDtoForWrite> getNotAssignedTrainers(String userName) {
		log.info("Entered  getNotAssignedTrainers in TraineeServiceImpl");
		Trainee trainee = traineeRepository.findByUserName(userName)
				.orElseThrow(() -> new TraineeNotFoundException("Trainee with given UserName does not Exists "));

		List<Trainer> assignedTrainers = trainee.getTrainersList();

		List<Trainer> trainers = trainerService.viewAllTrainers();
		log.info("Exited  getNotAssignedTrainers in TraineeServiceImpl");

		return trainers.stream().filter(t -> !assignedTrainers.contains(t)).map(t -> converter.toTrainerDtoForWrite(t))
				.toList();

	}

	public List<TrainerDtoForWrite> updateTraineesTrainersList(TraineeDtoForTrainerUpdate traineeDto) {

		log.info("Entered  updateTraineesTrainersList in TraineeServiceImpl");
		Trainee trainee = traineeRepository.findByUserName(traineeDto.getUserName())
				.orElseThrow(() -> new TraineeNotFoundException("Trainee with given UserName does not Exists"));

		List<Trainer> trainers = traineeDto.getTrainers().stream().map(t -> trainerService.getTrainerByUserName(t))
				.filter(t -> t != null).toList();

		trainee.setTrainersList(trainers);

		log.info("Exited  updateTraineesTrainersList in TraineeServiceImpl");
		return trainers.stream().map(t -> converter.toTrainerDtoForWrite(t)).toList();

	}

}
