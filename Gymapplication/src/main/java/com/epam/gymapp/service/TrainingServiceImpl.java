package com.epam.gymapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.gymapp.conveter.TrainingDtoConverter;
import com.epam.gymapp.dto.TrainingDtoForNewTraining;
import com.epam.gymapp.exception.TrainingException;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;
import com.epam.gymapp.repository.TrainingRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	TrainingRepository trainingRepository;

	@Autowired
	TrainerServiceImpl trainerService;
	@Autowired
	TraineeServiceImpl traineeService;

	@Autowired
	TrainingDtoConverter converter;

	public boolean addNewTraining(TrainingDtoForNewTraining trainingDto) {
		log.info("Entered  addNewTraining in TrainingServiceImpl");

		try {
			Trainee trainee = traineeService.getTraineeByUserName(trainingDto.getTraineeUserName());
			Trainer trainer = trainerService.getTrainerByUserName(trainingDto.getTrainerUserName());
			Training training = converter.toTraining(trainingDto, trainer, trainee);

			training.setTrainingType(trainer.getTrainingType());
			trainingRepository.save(training);

			trainee = traineeService.addTraining(trainee, trainer, training);
			trainerService.addTraining(trainee, trainer, training);
			log.info("Exited addNewTraining in TrainingServiceImpl");
			return true;
		} catch (Exception e) {
			throw new TrainingException("Training cannot be created,Give Correct Input Details");
		}

	}

}
