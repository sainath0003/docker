package com.epam.gymapp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {
	@Autowired
	ReportService reportService;

	@KafkaListener(topics = "report", groupId = "myGroup")
	public String consume(String message) {
		log.info("Message received {}", message);
		reportService.getReport(message);
		return message;

	}

	@KafkaListener(topics = "gymappReport", groupId = "myGroup")
	public ReportDto consumeJson(ReportDto reportDto) {
		log.info("Json Message received in reportService {}", reportDto.toString());
		reportService.saveReport(reportDto);
		return reportDto;
	}

}
