package com.epam.gymapp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@Entity
public class Training {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int trainingId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Trainer trainer;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Trainee trainee;
	@NotNull
	@Column(unique = true)
	private String trainingName;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private TrainingType trainingType;

	private LocalDate trainingDate = LocalDate.now();
	private Integer trainingDuration;

}
