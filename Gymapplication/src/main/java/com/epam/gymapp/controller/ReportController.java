package com.epam.gymapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.exception.TrainerNotFoundException;
import com.epam.gymapp.kafka.KafkaProducer;
import com.epam.gymapp.model.Trainer;
import com.epam.gymapp.service.Notificationservice;
import com.epam.gymapp.service.TrainerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gymapp/reportservice")
@Slf4j
public class ReportController {

	@Autowired
	TrainerService trainerService;

	@Autowired
	Notificationservice notificationService;
	@Autowired
	KafkaProducer kafkaProducer;

	@GetMapping("/view")
	public ResponseEntity<Void> view(@RequestParam String trainerUserName) {

		log.info("Entered  create in ReportController");
		kafkaProducer.sendMessage(trainerUserName);

		log.info("Exited  create in ReportController");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Void> save(@RequestBody ReportDto reportDto) {

		log.info("Entered  save in ReportController");
		Trainer trainer = trainerService.getTrainerByUserName(reportDto.getTrainerUserName());
		if (trainer != null) {

			log.info("Exited  save in ReportController");
			kafkaProducer.sendMessage(reportDto);
			notificationService.sendEmail(reportDto.getTrainerUserName(), "Your Training Report", reportDto.toString());
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			throw new TrainerNotFoundException("Trainer with given UserName is Not Present");
		}

	}

}
