package com.epam.gymapp.service;

import java.util.List;

import com.epam.gymapp.dto.TraineeDto;
import com.epam.gymapp.dto.TraineeDtoForRead;
import com.epam.gymapp.dto.TraineeDtoForTrainerUpdate;
import com.epam.gymapp.dto.TrainerDtoForWrite;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.model.Trainee;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.model.Training;

public interface TraineeService {

	public UserDto save(TraineeDto traineeDto);

	public Trainee addTraining(Trainee trainee, Trainer trainer, Training training);

	public TraineeDto update(TraineeDto traineeDto);

	public void delete(String userName);

	public TraineeDtoForRead view(String userName);

	public List<TrainingDtoForWrite> viewTraineeTrainingList(String userName);

	public Trainee getTraineeByUserName(String userName);

	public List<TrainerDtoForWrite> getNotAssignedTrainers(String userName);

	public List<TrainerDtoForWrite> updateTraineesTrainersList(TraineeDtoForTrainerUpdate traineeDto);

}
