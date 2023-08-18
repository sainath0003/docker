package com.epam.gymapp.dto;

import com.epam.gymapp.model.TrainingType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrainerDtoForWrite {

	private Integer trainerId;

	private String userName;

	private TrainingType trainingType;

	@Override
	public String toString() {
		return "trainerId=" + trainerId + ", userName=" + userName + ", trainingType="
				+ trainingType + "";
	}
	
	

}
