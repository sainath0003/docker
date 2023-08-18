package com.epam.gymapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.gymapp.model.Email;
import com.epam.gymapp.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	private final NotificationService notificationService;

	@Autowired
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@GetMapping("/sendemail")
	public String sendEmail(@RequestParam("recipient") String recipient, @RequestParam("subject") String subject,
			@RequestParam("body") String body) {
		notificationService.sendEmail(new Email(recipient, subject, body));
		return "Email sent successfully!";
	}

}
