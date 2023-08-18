package com.epam.gymapp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {

	private String trainerUserName;
	private String trainerFirstName;
	private String trainerLastName;
	private Boolean trainerStatus;
	private Integer traineeDuration;
	@JsonIgnore
	private List<TrainingDtoForWrite> trainings;
	@Override
	public String toString() {
		return "trainerUserName=" + trainerUserName + ", trainerFirstName=" + trainerFirstName
				+ ", trainerLastName=" + trainerLastName + ", traineeStatus=" + trainerStatus + ", traineeDuration="
				+ traineeDuration + ", trainings=" + trainings + "s";
	}
	
	
}
