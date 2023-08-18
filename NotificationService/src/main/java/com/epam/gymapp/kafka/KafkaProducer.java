package com.epam.gymapp.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.epam.gymapp.model.Email;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {
	@Autowired
	private KafkaTemplate<String, Email> reportKafkaTemplate;

	public void sendMessage(Email email) {
		log.info("Message sent Report : {}", email.toString());
		Message<Email> message = MessageBuilder.withPayload(email).setHeader(KafkaHeaders.TOPIC, "emailOut")
				.build();
		
		reportKafkaTemplate.send(message);
	}

}
