package com.epam.gymapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDtoForWrite {

	private String trainerUserName;

	private String traineeUserName;

	private String trainingName;

	private String trainingTypeName;

	private String trainingDate;

	private Integer trainingDuration;

}
