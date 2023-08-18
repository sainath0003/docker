package com.epam.gymapp.proxy;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

	@Override
	public String sendEmail(String recipient, String subject, String body) {
		log.info("Entered  sendEmail in NotificationServiceImplin gymapp");
		log.info("Email is not sent");
		return "email Not sent";
	}

}
