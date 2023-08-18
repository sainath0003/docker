package com.epam.gymapp.model;

import java.util.List;

import com.epam.gymapp.dto.TrainingDtoForWrite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

	private String trainerUserName;
	private String trainerFirstName;
	private String trainerLastName;
	private Boolean traineeStatus;
	private Integer traineeDuration;
	private List<TrainingDtoForWrite> trainings;
}
