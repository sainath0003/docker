package com.epam.gymapp.dto;

import java.util.List;

import com.epam.gymapp.model.Trainee;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDtoForRead {

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String specialization;
	@Email
	private String email;
	private boolean isActive;
	@JsonIgnore
	private List<Trainee> traineeList;

}
