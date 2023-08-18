package com.epam.gymapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.gymapp.dto.TrainingDtoForNewTraining;
import com.epam.gymapp.service.Notificationservice;
import com.epam.gymapp.service.TrainingService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gymapp/training")
@Slf4j
public class TrainingController {
	@Autowired
	TrainingService trainingService;
	@Autowired
	Notificationservice notificationService;

	@PostMapping("/add")
	@Operation(summary = "add a new Training")
	public ResponseEntity<Void> add(@RequestBody TrainingDtoForNewTraining trainingDto) {

		log.info("Entered  add in TrainingController");
		trainingService.addNewTraining(trainingDto);
		String subject = "You have registered to " + trainingDto.getTrainingName() + "training";
		notificationService.sendEmail(trainingDto.getTraineeUserName(), subject, trainingDto.toString());
		notificationService.sendEmail(trainingDto.getTrainerUserName(), subject, trainingDto.toString());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
