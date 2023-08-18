package com.epam.gymapp.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@Entity
public class TrainingType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer trainingId;

	@Column(nullable = false,unique=true)
	@Pattern(regexp = "Fitness|Yoga|Zumba|Stretching|Resistance", message = "Invalid training type. Allowed values are fitness, yoga, Zumba, stretching, resistance.")
	private String trainingTypeName;

	@OneToMany(mappedBy = "trainingType",cascade = CascadeType.ALL)
	private List<Trainer> trainerList = new ArrayList<>();

	@OneToMany(mappedBy = "trainingType",cascade =  CascadeType.ALL)
	private List<Training> trainingList = new ArrayList<>();

}
