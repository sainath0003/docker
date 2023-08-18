package com.epam.gymapp.service;

import java.util.List;

import com.epam.gymapp.dto.TrainerDto;
import com.epam.gymapp.dto.TrainerDtoForRead;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;

public interface TrainerService {
	public UserDto save(TrainerDto trainerDto);

	public Trainer addTraining(Trainee trainee, Trainer trainer, Training training);

	public TrainerDto update(TrainerDto trainerDto);

	public void delete(String userName);

	public TrainerDtoForRead view(String userName);

	public Trainer getTrainerByUserName(String userName);

	public List<TrainingDtoForWrite> viewTrainerTrainingList(String userName);

	public List<Trainer> viewAllTrainers();
}
