package com.epam.gymapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.epam.gymapp.dto.TraineeDto;
import com.epam.gymapp.dto.TraineeDtoForRead;
import com.epam.gymapp.dto.TraineeDtoForTrainerUpdate;
import com.epam.gymapp.dto.TrainerDtoForWrite;
import com.epam.gymapp.dto.TrainingDto;
import com.epam.gymapp.dto.TrainingDtoForWrite;
import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.kafka.KafkaProducer;
import com.epam.gymapp.service.Notificationservice;
import com.epam.gymapp.service.TraineeService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gymapp/trainee")
@Slf4j
public class TraineeController {
	@Autowired
	TraineeService traineeService;
	@Autowired
	Notificationservice notificationService;
	
	@Autowired
	KafkaProducer kafkaProducer;
	@Value("${server.port}")
	int port;

	@PostMapping("/register")
	@Operation(summary = "register a Trainee profile")
	public ResponseEntity<UserDto> save(@RequestBody TraineeDto traineeDto) {

		log.info("Entered  save in TraineeController");
		UserDto userDto = traineeService.save(traineeDto);
		kafkaProducer.sendUserDto(userDto);
		notificationService.sendEmail(userDto.getUserName(), "Registration Sucessfull",
				"Your Login Details are : \n" + userDto.toString());
		return new ResponseEntity<>(userDto, HttpStatus.OK);

	}

	@PutMapping("/update")
	@Operation(summary = "update a Trainee profile")
	public ResponseEntity<TraineeDto> update(@RequestBody TraineeDto traineeDto) {

		log.info("Entered  update in TraineeController");
		traineeDto = traineeService.update(traineeDto);
		notificationService.sendEmail(traineeDto.getEmail(), "Upadtation Sucessfull", traineeDto.toString());

		return new ResponseEntity<>(traineeDto, HttpStatus.OK);
	}

	@PutMapping("/update/trainer")
	@Operation(summary = "update a Trainee Trainers List")
	public ResponseEntity<List<TrainerDtoForWrite>> updateTrainer(@RequestBody TraineeDtoForTrainerUpdate traineeDto) {

		log.info("Entered  updateTrainer in TraineeController");
		return new ResponseEntity<>(traineeService.updateTraineesTrainersList(traineeDto), HttpStatus.OK);
	}

	@GetMapping("/view")
	@Operation(summary = "view a Trainee profile")
	public ResponseEntity<TraineeDtoForRead> view(@RequestParam String userName) {

		log.info("Entered  view in TraineeController");
		return new ResponseEntity<>(traineeService.view(userName), HttpStatus.OK);
	}

	
	@DeleteMapping("/delete")
	public ResponseEntity<UserDto> delete(@RequestParam String userName) {

		log.info("Entered  delete in TraineeController");
		traineeService.delete(userName);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/view/trainingList")
	@Operation(summary = "Get Trainee Trainings List")
	public ResponseEntity<List<TrainingDtoForWrite>> viewTraineesTrainingsList(@RequestBody TrainingDto trainingDto) {

		log.info("Entered  viewTraineesTrainingsList in TraineeController");
		return new ResponseEntity<>(traineeService.viewTraineeTrainingList(trainingDto.getUserName()), HttpStatus.OK);
	}

	@GetMapping("/view/unassignedTrainers")
	@Operation(summary = "Get Unassigned Trainers List")
	public ResponseEntity<List<TrainerDtoForWrite>> viewUnassignedTrainersList(@RequestParam String userName) {

		log.info("Entered  viewUnassignedTrainersList in TraineeController");
		return new ResponseEntity<>(traineeService.getNotAssignedTrainers(userName), HttpStatus.OK);
	}

}
