package com.epam.gymapp.kafka;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.epam.gymapp.model.Report;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {

	List<Report> reportDtos = null;

	@KafkaListener(topics = "gymapp", groupId = "myGroup")
	public void consume(String message) {
		log.info("Message received {}", message);

	}


	@KafkaListener(topics = "gymappReportList", groupId = "myGroup")
	public void consumeReportList(List<Report> report) {
		log.info("Json Message list received {}", report.toString());

	}

	public List<Report> getReportList() {
		return reportDtos;
	}
}
