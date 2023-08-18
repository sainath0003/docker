package com.epam.gymapp.controller;

import java.util.List;

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
import com.epam.gymapp.model.Report;
import com.epam.gymapp.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reportservice")
@Slf4j
public class ReportController {
	@Autowired
	ReportService reportService;

	@PostMapping("/save")
	public ResponseEntity<Report> create(@RequestBody ReportDto reportDto) {

		log.info("Entered  create in ReportController");
		return new ResponseEntity<>(reportService.saveReport(reportDto), HttpStatus.OK);
	}

	@GetMapping("/view")
	public ResponseEntity<List<Report>> view(@RequestParam String trainerUserName) {
		log.info("Entered  view in ReportController");
		
		return new ResponseEntity<>(reportService.getReport(trainerUserName), HttpStatus.OK);
	}


}
