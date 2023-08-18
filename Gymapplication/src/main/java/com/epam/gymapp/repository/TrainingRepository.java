package com.epam.gymapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.gymapp.model.Training;

public interface TrainingRepository extends JpaRepository<Training, Integer> {
	
	List<Training> findByTrainingName(String trainingName);
	
}
