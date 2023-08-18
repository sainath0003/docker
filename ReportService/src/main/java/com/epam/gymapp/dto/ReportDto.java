package com.epam.gymapp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
	
	private String trainerUserName;
	private String trainerFirstName;
	private String trainerLastName;
	private Boolean trainerStatus;
	private Integer traineeDuration;
	@JsonIgnore
	private List<TrainingDtoForWrite> trainings;
}
