package com.epam.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDto {

	private String userName;
	private String periodFrom;
	private String periodTo;
	private String trainerName;
	private String trainingType;
}
