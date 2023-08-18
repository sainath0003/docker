package com.epam.gymapp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Trainee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer traineeId;
	@OneToOne
	private User user;
	@Column(unique = true)
	private String userName;
	@Nullable
	private String dob;
	@Nullable
	private String address;

	private boolean isActive=true;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Trainer> trainersList = new ArrayList<>();
	
	@OneToMany(mappedBy = "trainee",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Training> trainingList = new ArrayList<>();
	
}
