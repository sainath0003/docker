package com.epam.gymapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.gymapp.dto.UserDto;
import com.epam.gymapp.dto.UserDtoForPasswordChange;
import com.epam.gymapp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/gymapp/user")
@Slf4j
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/login")
	@Operation(summary = "Logging in a user")
	public ResponseEntity<Void> login(@RequestBody UserDto userDto) {

		log.info("Entered  login in UserController");
		ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		if (Boolean.TRUE.equals(userService.login(userDto))) {
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/changepassword")
	@Operation(summary = "Update a User Signin Details")
	public ResponseEntity<Void> updateLoginInfo(@RequestBody UserDtoForPasswordChange userDto) {

		log.info("Entered  UpdateLoginInfo in UserController");
		ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		if (Boolean.TRUE.equals(userService.changeLogin(userDto))) {
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
		}
		return responseEntity;
	}

	@GetMapping("/status")
	@Operation(summary = "view a Trainee profile")
	public ResponseEntity<Void> setStauts(@RequestBody UserDto userDto) {

		log.info("Entered  view in TraineeController");
		userService.setStatus(userDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
