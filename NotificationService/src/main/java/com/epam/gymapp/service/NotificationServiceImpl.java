package com.epam.gymapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.epam.gymapp.exception.NotificationException;
import com.epam.gymapp.kafka.KafkaProducer;
import com.epam.gymapp.model.Email;
import com.epam.gymapp.repository.NotificationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	KafkaProducer kafkaProducer;

	public void sendEmail(Email email) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			// message.setTo(email.getRecipient());
			message.setTo("ravikanth00003@gmail.com");
			message.setSubject(email.getSubject());
			message.setText(email.getBody());
			mailSender.send(message);

			notificationRepository.save(email);

			kafkaProducer.sendMessage(email);
			log.info("Email saved to Database");
		} catch (MailException e) {
			email.setStatus(false);
			throw new NotificationException("Notification(Email) Not Sent");
		} catch (Exception e) {
			email.setStatus(false);
			throw new NotificationException("Notification(Email) Not Saved to Database");
		}

	}
}
