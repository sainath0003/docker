package com.epam.security.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.epam.gymapp.dto.UserDto;
import com.epam.security.entity.UserCredential;
import com.epam.security.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaConsumer {
	@Autowired
	private AuthService authService;

	@KafkaListener(topics = "userDto1", groupId = "myGroup")
	public void consumeReportList(UserDto userDto) {
		
		log.info("Json Message list received {}", userDto.toString());
		authService.saveUser(new UserCredential(0, userDto.getUserName(), userDto.getPassword()));
		log.info("Json Message list received {}", userDto.toString());

	}
}
