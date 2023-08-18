package com.epam.gymapp.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.gymapp.exception.NotificationException;
import com.epam.gymapp.exception.NotificationNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GLobalExceptionHandler {

	@ExceptionHandler(NotificationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleTNotificationException(NotificationException e, WebRequest request,
			HttpServletRequest serrequest) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
				serrequest.getLocalAddr());
	}

	@ExceptionHandler(NotificationNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleNotificationNotFoundException(NotificationNotFoundException e, WebRequest request,
			HttpServletRequest serrequest) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.NOT_FOUND.toString(), e.getMessage(),
				serrequest.getLocalAddr());
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleNullPointerException(NullPointerException e, WebRequest request) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
			WebRequest request) {
		log.error(e.getMessage());
		StringBuilder inputerror = new StringBuilder();
		e.getAllErrors().forEach(err -> inputerror.append(err.getDefaultMessage() + "\n"));
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), inputerror.toString(),
				request.getDescription(false));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
			WebRequest request) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleRuntimeException(RuntimeException e, WebRequest request) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage(),
				request.getDescription(false));

	}
}
