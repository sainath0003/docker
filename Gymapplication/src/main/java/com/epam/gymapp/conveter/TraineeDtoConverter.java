package com.epam.gymapp.conveter;

import org.springframework.stereotype.Component;

import com.epam.gymapp.dto.TraineeDto;
import com.epam.gymapp.dto.TraineeDtoForRead;
import com.epam.gymapp.dto.TrainerDtoForWrite;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;
import com.epam.gymapp.model.User;

@Component
public class TraineeDtoConverter {

	public User toUser(TraineeDto traineeDto) {
		User user = new User();
		user.setFirstName(traineeDto.getFirstName());
		user.setLastName(traineeDto.getLastName());
		user.setUserName(traineeDto.getEmail());
		return user;
	}

	public Trainee toTrainee(TraineeDto traineeDto) {
		Trainee trainee = new Trainee();
		trainee.setUserName(traineeDto.getEmail());
		trainee.setDob(traineeDto.getDob());
		trainee.setAddress(traineeDto.getAddress());
		return trainee;

	}

	public TraineeDtoForRead toTraineeDtoForRead(User user, Trainee trainee) {
		return new TraineeDtoForRead(user.getFirstName(), user.getLastName(), trainee.getDob(), trainee.getAddress(),
				user.getUserName(), user.isActive(), trainee.getTrainersList());

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
