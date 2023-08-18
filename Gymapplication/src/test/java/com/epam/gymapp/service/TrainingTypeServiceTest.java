package com.epam.gymapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapp.model.TrainingType;
import com.epam.gymapp.repository.TrainingTypeRepository;
import com.google.common.base.Optional;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceTest {

	@Mock

	TrainingTypeRepository trainingTypeRepository;

	@InjectMocks
	private TrainingTypeServiceImpl trainingTypeService;
	TrainingType trainingType = new TrainingType(1, "Yoga", null, null);

	@Test
	 void testGetTrainingType() {
		
		when(trainingTypeRepository.findBytrainingTypeName(anyString())).thenReturn(Optional.of(trainingType));
		TrainingType result= trainingTypeService.getTrainingType("Yoga");
		assertEquals(trainingType, result);
		}

	@Test
	 void testGetTrainingTypeForNewTrainingType() {
		
		when(trainingTypeRepository.findBytrainingTypeName(anyString())).thenReturn(Optional.absent());
		when(trainingTypeRepository.save(any())).thenReturn(trainingType);
		TrainingType result= trainingTypeService.getTrainingType("Yoga");
		assertEquals(trainingType, result);
		}

}
