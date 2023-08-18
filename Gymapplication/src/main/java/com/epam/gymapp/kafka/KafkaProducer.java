package com.epam.gymapp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.epam.gymapp.dto.ReportDto;
import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.model.Email;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaTemplate<String, ReportDto> reportKafkaTemplate;
	@Autowired
	private KafkaTemplate<String, UserDto> userKafkaTemplate;
	@Autowired
	private KafkaTemplate<String, Email> emailKafkaTemplate;

	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {

		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String message) {
		log.info("Message  sent {}", message);
		kafkaTemplate.send("report", message);
	}

	public void sendEmail(Email email) {
		log.info("Message sent email : {}", email.toString());
		Message<Email> message = MessageBuilder.withPayload(email).setHeader(KafkaHeaders.TOPIC, "email").build();

		emailKafkaTemplate.send(message);
	}

	public void sendUserDto(UserDto userDto) {
		log.info("Message sent security : {}", userDto.toString());
		Message<UserDto> message = MessageBuilder.withPayload(userDto).setHeader(KafkaHeaders.TOPIC, "userDto1").build();

		userKafkaTemplate.send(message);
	}

	public void sendMessage(ReportDto reportDto) {
		log.info("Message sent report : {}", reportDto.toString());
		Message<ReportDto> message = MessageBuilder.withPayload(reportDto).setHeader(KafkaHeaders.TOPIC, "gymappReport")
				.build();

		reportKafkaTemplate.send(message);
	}

}
