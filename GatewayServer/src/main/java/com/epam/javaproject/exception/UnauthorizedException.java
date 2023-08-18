package com.epam.javaproject.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String message) {
		super(message);
		log.info(message);
	}
}
