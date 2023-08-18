package com.epam.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDtoForNewTraining {

	private String traineeUserName;
	private String trainerUserName;
	private String trainingName;
	private String trainingType;
	private Integer trainingDuration;

}
