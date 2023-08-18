package com.epam.gymapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.gymapp.kafka.KafkaProducer;
import com.epam.gymapp.model.Email;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
	@Mock
	KafkaProducer kafkaProducer;
	@InjectMocks
	Notificationservice notificationservice;

	@Test
	void testSendEmail() {
		Email result = notificationservice.sendEmail("sai", "sai123", "sai345");
		Mockito.verify(kafkaProducer).sendEmail(any());
		assertEquals("sai123", result.getSubject());
	}

}
