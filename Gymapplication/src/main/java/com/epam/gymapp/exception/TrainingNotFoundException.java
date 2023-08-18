package com.epam.gymapp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrainingNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TrainingNotFoundException(String message) {
		super(message);
		log.info(message);
	}
}
