package com.epam.gymapp.dto;

import java.util.List;

import com.epam.gymapp.model.Trainer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nullable;
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
public class TraineeDtoForRead {

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@Nullable
	private String dob;
	@Nullable
	private String address;
	@Email
	private String email;

	private boolean isActive;
    @JsonIgnore
	private List<Trainer> trainersList;

}
