package com.epam.gymapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.gymapp.kafka.KafkaProducer;
import com.epam.gymapp.model.Email;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Notificationservice {
	@Autowired
	KafkaProducer kafkaProducer;

	public Email sendEmail(String recipient, String subject, String body) {
		log.info("Entered  sendEmail in NotificationServiceImplin gymapp");
		Email email = new Email(recipient, subject, body);
		kafkaProducer.sendEmail(email);

		log.info("Email is sent");
		return email;
	}

}
