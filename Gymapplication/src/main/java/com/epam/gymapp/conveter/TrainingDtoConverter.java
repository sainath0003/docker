package com.epam.gymapp.conveter;

import org.springframework.stereotype.Component;

import com.epam.gymapp.dto.TrainingDtoForNewTraining;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;
import com.epam.gymapp.model.TrainingType;

@Component
public class TrainingDtoConverter {

	public Training toTraining(TrainingDtoForNewTraining trainingDto, Trainer trainer, Trainee trainee) {
		return new Training(0, trainer, trainee, trainingDto.getTrainingName(), null, null,
				trainingDto.getTrainingDuration());
	}

	public TrainingType toTrainingType(Training training, Trainer trainer, String trainingTypeName) {
		TrainingType trainingType = new TrainingType();

		trainingType.setTrainingTypeName(trainingTypeName);
		trainingType.getTrainingList().add(training);
		trainingType.getTrainerList().add(trainer);
		trainingType.setTrainerList(trainingType.getTrainerList());
		trainingType.setTrainingList(trainingType.getTrainingList());
		return trainingType;
	}

	public TrainingDtoForWrite toTrainingDtoForWrite(Training training) {
		return new TrainingDtoForWrite(training.getTrainer().getUserName(), training.getTrainee().getUserName(),
				training.getTrainingName(), training.getTrainingType().getTrainingTypeName(),
				training.getTrainingDate() + "", training.getTrainingDuration());
	}
}
