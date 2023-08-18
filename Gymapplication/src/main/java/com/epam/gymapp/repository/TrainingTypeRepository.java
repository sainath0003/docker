package com.epam.gymapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.gymapp.model.TrainingType;
import com.google.common.base.Optional;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, Integer> {

	Optional<TrainingType> findBytrainingTypeName(String trainingTypeName);

	boolean existsByTrainingTypeName(String trainingTypeName);

}
