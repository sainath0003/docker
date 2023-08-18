package com.epam.gymapp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ReportNotFoundException(String message) {
		super(message);
		log.info(message);
	}
}
