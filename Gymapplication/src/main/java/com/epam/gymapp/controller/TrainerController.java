package com.epam.gymapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.gymapp.dto.TrainerDto;
import com.epam.gymapp.dto.TrainerDtoForRead;
import com.epam.gymapp.dto.TrainingDto;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.kafka.KafkaProducer;
import com.epam.gymapp.service.Notificationservice;
import com.epam.gymapp.service.TrainerService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gymapp/trainer")
@Slf4j
public class TrainerController {
	@Autowired
	TrainerService trainerService;
	@Autowired
	Notificationservice notificationService;
	@Autowired
	KafkaProducer kafkaProducer;

	@PostMapping("/register")
	@Operation(summary = "register a Trainer profile")
	public ResponseEntity<UserDto> save(@RequestBody TrainerDto traineeDto) {
		log.info("Entered  save in TrainerController");
		UserDto userDto = trainerService.save(traineeDto);
		kafkaProducer.sendUserDto(userDto);
		notificationService.sendEmail(userDto.getUserName(), "Registration Sucessfull",
				"Your Login Details are : \n" + userDto.toString());

		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@PutMapping("/update")
	@Operation(summary = "update a Trainer profile")
	public ResponseEntity<TrainerDto> update(@RequestBody TrainerDto trainerDto) {

		log.info("Entered  update in TrainerController");
		trainerDto = trainerService.update(trainerDto);
		notificationService.sendEmail(trainerDto.getEmail(), "Upadtation Sucessfull", trainerDto.toString());

		return new ResponseEntity<>(trainerDto, HttpStatus.OK);

	}

	@GetMapping("/view")
	@Operation(summary = "view a Trainer profile")
	public ResponseEntity<TrainerDtoForRead> view(@RequestParam String userName) {

		log.info("Entered  view in TrainerController");
		return new ResponseEntity<>(trainerService.view(userName), HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<UserDto> delete(@RequestParam String userName) {

		log.info("Entered  delete in TrainerController");
		trainerService.delete(userName);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/view/trainingList")
	@Operation(summary = "Get Trainer Trainings List")
	public ResponseEntity<List<TrainingDtoForWrite>> viewTrainersTrainingsList(@RequestBody TrainingDto trainingDto) {

		log.info("Entered  viewTrainersTrainingsList in TrainerController");
		return new ResponseEntity<>(trainerService.viewTrainerTrainingList(trainingDto.getUserName()), HttpStatus.OK);
	}

}
