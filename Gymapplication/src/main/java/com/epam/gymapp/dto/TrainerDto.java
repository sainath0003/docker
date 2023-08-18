package com.epam.gymapp.dto;

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
public class TrainerDto {
	

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String specialization;
	@Email
	private String email;

	

}
