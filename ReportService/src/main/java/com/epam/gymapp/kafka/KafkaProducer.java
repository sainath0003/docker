package com.epam.gymapp.kafka;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.model.Report;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {
	@Autowired
	private KafkaTemplate<String, ReportDto> reportKafkaTemplate;

	public void sendMessage(ReportDto reportDto) {
		log.info("Message sent Report : {}", reportDto.toString());
		Message<ReportDto> message = MessageBuilder.withPayload(reportDto).setHeader(KafkaHeaders.TOPIC, "gymappReport")
				.build();

		reportKafkaTemplate.send(message);
	}
	
	public List<Report> sendReportList(List<Report> reportDto) {
		log.info("Message sent Report : {}", reportDto.toString());
		Message<List<Report>> message = MessageBuilder.withPayload(reportDto).setHeader(KafkaHeaders.TOPIC, "gymappReportList")
				.build();

		reportKafkaTemplate.send(message);
		return reportDto;
	}
	
	

}
