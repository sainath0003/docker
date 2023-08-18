package com.epam.gymapp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@Entity
public class Trainer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer trainerId;
	@OneToOne
	private User user;
	@Column(unique = true)
	private String userName;

	@ManyToOne
	private TrainingType trainingType;
	@JsonIgnore
	@ManyToMany(mappedBy = "trainersList", cascade = CascadeType.ALL)
	private List<Trainee> traineesList = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
	private List<Training> trainingsList = new ArrayList<>();

}
