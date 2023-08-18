package com.epam.gymapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.gymapp.model.TrainingType;
import com.epam.gymapp.repository.TrainingTypeRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TrainingTypeServiceImpl implements TrainingTypeService {
	@Autowired
	TrainingTypeRepository trainingTypeRepository;

	@Override
	public TrainingType getTrainingType(String trainingTypeName) {
		
		log.info("Entered getTrainingType in TrainingTypeServiceImpl");
		TrainingType trainingType = trainingTypeRepository.findBytrainingTypeName(trainingTypeName).orNull();

		if (trainingType == null) {
			trainingType = new TrainingType();
			trainingType.setTrainingTypeName(trainingTypeName);
			trainingType = trainingTypeRepository.save(trainingType);
		}
		return trainingType;
	}

}
