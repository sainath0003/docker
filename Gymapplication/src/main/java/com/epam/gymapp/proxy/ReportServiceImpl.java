package com.epam.gymapp.proxy;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.model.Report;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

	@Override
	public ResponseEntity<Report> create(ReportDto report) {
		log.info("Entered  create in ReportServiceImpl in gymapp");
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<Report>> view(String trainerUserName) {
		log.info("Entered  view in ReportServiceImpl in gymapp");
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
