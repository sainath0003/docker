package com.epam.gymapp.conveter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.gymapp.dto.TrainerDto;
import com.epam.gymapp.dto.TrainerDtoForRead;
import com.epam.gymapp.dto.TrainerDtoForWrite;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;
import com.epam.gymapp.model.User;
import com.epam.gymapp.service.TrainingTypeService;

@Component
public class TrainerDtoConverter {
	@Autowired
	TrainingTypeService trainingTypeService;

	public User toUser(TrainerDto trainerDto) {
		User user = new User();
		user.setFirstName(trainerDto.getFirstName());
		user.setLastName(trainerDto.getLastName());
		user.setUserName(trainerDto.getEmail());
		return user;
	}

	public Trainer toTrainer(TrainerDto trainerDto) {
		Trainer trainer = new Trainer();
		trainer.setTrainingType(trainingTypeService.getTrainingType(trainerDto.getSpecialization()));
		trainer.setUserName(trainerDto.getEmail());

		return trainer;

	}

	public TrainerDtoForRead toTrainerDtoForRead(User user, Trainer trainer) {
		return new TrainerDtoForRead(user.getFirstName(), user.getLastName(),
				trainer.getTrainingType().getTrainingTypeName(), user.getUserName(), user.isActive(),
				trainer.getTraineesList());

	}

	public TrainerDtoForWrite toTrainerDtoForWrite(Trainer trainer) {
		return new TrainerDtoForWrite(trainer.getTrainerId(), trainer.getUserName(), trainer.getTrainingType());

	}
	
	public TrainingDtoForWrite toTrainingDtoForWrite(Training training) {
		return new TrainingDtoForWrite(training.getTrainer().getUserName(), training.getTrainee().getUserName(),
				training.getTrainingName(), training.getTrainingType().getTrainingTypeName(),
				training.getTrainingDate()+"", training.getTrainingDuration());
	}

}
