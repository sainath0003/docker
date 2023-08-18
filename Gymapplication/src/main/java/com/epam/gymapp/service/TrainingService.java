package com.epam.gymapp.service;

import com.epam.gymapp.dto.TrainingDtoForNewTraining;

public interface TrainingService {
	public boolean addNewTraining(TrainingDtoForNewTraining trainingDto);
}
