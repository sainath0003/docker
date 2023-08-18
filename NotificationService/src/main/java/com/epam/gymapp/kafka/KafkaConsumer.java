package com.epam.gymapp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.epam.gymapp.model.Email;
import com.epam.gymapp.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {
	@Autowired
	NotificationService notificationService;

	@KafkaListener(topics = "email", groupId = "myGroup")
	public void consumeJson(Email email) {
		log.info("Json Message received in reportService {}", email.toString());
		notificationService.sendEmail(email);

	}

}
