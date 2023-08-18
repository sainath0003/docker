package com.epam.gymapp.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notificationservice", fallback = NotificationServiceImpl.class)
public interface NotificationService {

	@GetMapping("/notification/send-email")
	public String sendEmail(@RequestParam("recipient") String recipient, @RequestParam("subject") String subject,
			@RequestParam("body") String body);
}
