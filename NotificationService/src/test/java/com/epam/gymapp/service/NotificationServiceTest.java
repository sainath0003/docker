package com.epam.gymapp.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.epam.gymapp.exception.NotificationException;
import com.epam.gymapp.kafka.KafkaProducer;
import com.epam.gymapp.model.Email;
import com.epam.gymapp.repository.NotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

	@Mock
	JavaMailSender mailSender;

	@Mock
	NotificationRepository notificationRepository;

	@Mock
	KafkaProducer kafkaProducer;

	@InjectMocks
	private NotificationServiceImpl notificationService;

	@Test
	void testSendMail() {
		Email email = new Email("sai1", "sai2", "sai3");
		when(notificationRepository.save(any())).thenReturn(email);
		notificationService.sendEmail(email);

		Mockito.verify(kafkaProducer).sendMessage(email);
	}

	@Test
	void testSendMailUnsucessfull() {
		when(notificationRepository.save(any())).thenThrow(NullPointerException.class);
		Email email = new Email("sai1", "sai2", "sai3");

		assertThrows(NotificationException.class, () -> notificationService.sendEmail(email));

	}

	@Test
	void testSendMailUnsucessful() {
		Email email = new Email("sai1", "sai2", "sai3");
		doThrow(new MailParseException("hello")).when(mailSender).send(any(SimpleMailMessage.class));
		assertThrows(NotificationException.class, () -> notificationService.sendEmail(email));

	}
}