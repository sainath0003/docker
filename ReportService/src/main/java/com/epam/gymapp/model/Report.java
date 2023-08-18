package com.epam.gymapp.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.dto.TrainingDtoForWrite;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "reports")
@Data
@NoArgsConstructor
public class Report {

	private String trainerUserName;
	private String trainerFirstName;
	private String trainerLastName;
	private Boolean trainerStatus;
	private Integer traineeDuration;

	private List<TrainingDtoForWrite> trainingsList;

	public Report(ReportDto reportDto) {
		
		this.trainerUserName = reportDto.getTrainerUserName();
		this.trainerFirstName = reportDto.getTrainerFirstName();
		this.trainerLastName = reportDto.getTrainerLastName();
		this.trainerStatus = reportDto.getTrainerStatus();
		this.traineeDuration = reportDto.getTraineeDuration();
		this.trainingsList = reportDto.getTrainings();
	}

}
