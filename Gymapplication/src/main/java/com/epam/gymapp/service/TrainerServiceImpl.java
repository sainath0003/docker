package com.epam.gymapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.gymapp.conveter.TrainerDtoConverter;
import com.epam.gymapp.dto.TrainerDto;
import com.epam.gymapp.dto.TrainerDtoForRead;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.exception.TrainerException;
import com.epam.gymapp.exception.TrainerNotFoundException;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;
import com.epam.gymapp.model.User;
import com.epam.gymapp.repository.TrainerRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TrainerServiceImpl implements TrainerService {

	TrainerDtoConverter converter;
	UserServiceImpl userService;
	TrainerRepository trainerRepository;

	@Autowired
	public TrainerServiceImpl(TrainerDtoConverter converter, UserServiceImpl userService,
			TrainerRepository trainerRepository) {

		this.converter = converter;
		this.userService = userService;
		this.trainerRepository = trainerRepository;
	}

	public UserDto save(TrainerDto trainerDto) {
		log.info("Entered  save in TrainerServiceImpl");
		try {

			User user = converter.toUser(trainerDto);
			Trainer trainer = converter.toTrainer(trainerDto);
			user.setTrainer(trainer);
			user = userService.save(user);

			trainer.setUser(user);
			trainerRepository.save(trainer);

			return new UserDto(user.getUserName(), user.getPassword());

		} catch (Exception e) {
			throw new TrainerException("Trainer with given UserName Already Exists");
		}
	}

	public Trainer addTraining(Trainee trainee, Trainer trainer, Training training) {
		log.info("Entered  addTraining in TrainerServiceImpl");
		try {
			trainer.getTrainingsList().add(training);
			trainer.getTraineesList().add(trainee);
			trainer.setTrainingsList(trainer.getTrainingsList());

			return trainerRepository.save(trainer);
		} catch (Exception e) {
			throw new TrainerException("Trainer with given UserName  does not Exists");
		}

	}

	public TrainerDto update(TrainerDto trainerDto) {
		log.info("Entered  update in TrainerServiceImpl");

		Trainer trainer = converter.toTrainer(trainerDto);
		User user = converter.toUser(trainerDto);

		Trainer dbTrainer = trainerRepository.findByUserName(trainerDto.getEmail())
				.orElseThrow(() -> new TrainerNotFoundException("Trainer  with given UserName does not Exist"));

		trainer.setTrainerId(dbTrainer.getTrainerId());
		user.setUserId(dbTrainer.getUser().getUserId());
		trainer.setUser(user);

		user.setTrainer(trainer);
		userService.save(user);
		trainerRepository.save(trainer);
		log.info("Exited  update in TrainerServiceImpl");
		return trainerDto;

	}

	public void delete(String userName) {
		log.info("Entered  delete in TrainerServiceImpl");
		userService.delete(userName);
		trainerRepository.deleteByUserName(userName);

	}

	public TrainerDtoForRead view(String userName) {
		log.info("Entered  view in TrainerServiceImpl");
		User user = userService.view(userName);
		Trainer trainer = trainerRepository.findByUserName(userName)
				.orElseThrow(() -> new TrainerNotFoundException("Trainer with given UserName does not Exist"));
		return converter.toTrainerDtoForRead(user, trainer);
	}

	public Trainer getTrainerByUserName(String userName) {
		log.info("Entered  getTrainerByUserName in TrainerServiceImpl");
		return trainerRepository.findByUserName(userName)
				.orElseThrow(() -> new TrainerNotFoundException("Trainer with given UserName does not Exists"));

	}

	public List<TrainingDtoForWrite> viewTrainerTrainingList(String userName) {

		log.info("Entered  viewTrainerTrainingList in TrainerServiceImpl");
		Trainer trainer = trainerRepository.findByUserName(userName)
				.orElseThrow(() -> new TrainerNotFoundException("Trainer with given UserName does not Exists"));

		List<TrainingDtoForWrite> trainingDtoList = new ArrayList<>();
		if (trainer != null) {
			trainingDtoList = trainer.getTrainingsList().stream().map(t -> converter.toTrainingDtoForWrite(t)).toList();

		}

		return trainingDtoList;
	}

	public List<Trainer> viewAllTrainers() {
		log.info("Entered  viewAllTrainers in TrainerServiceImpl");
		return trainerRepository.findAll();
	}

}
