package com.epam.gymapp.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.gymapp.exception.TraineeException;
import com.epam.gymapp.exception.TraineeNotFoundException;
import com.epam.gymapp.exception.TrainerException;
import com.epam.gymapp.exception.TrainerNotFoundException;
import com.epam.gymapp.exception.TrainingException;
import com.epam.gymapp.exception.TrainingNotFoundException;
import com.epam.gymapp.exception.UserException;
import com.epam.gymapp.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GLobalExceptionHandler {
	
	
	@ExceptionHandler(TraineeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleTraineeException(TraineeException e, WebRequest request, HttpServletRequest serrequest) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
				serrequest.getLocalAddr());
	}

	@ExceptionHandler(TraineeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleTraineeNoyFoundException(TraineeNotFoundException e, WebRequest request,
			HttpServletRequest serrequest) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.NOT_FOUND.toString(), e.getMessage(),
				serrequest.getLocalAddr());
	}
	
	@ExceptionHandler(TrainerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleTrainerException(TrainerException e, WebRequest request, HttpServletRequest serrequest) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
				serrequest.getLocalAddr());
	}

	@ExceptionHandler(TrainerNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleTrainerNoyFoundException(TrainerNotFoundException e, WebRequest request,
			HttpServletRequest serrequest) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.NOT_FOUND.toString(), e.getMessage(),
				serrequest.getLocalAddr());
	}
	
	@ExceptionHandler(UserException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleUserException(UserException e, WebRequest request, HttpServletRequest serrequest) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
				serrequest.getLocalAddr());
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleUserNoyFoundException(UserNotFoundException e, WebRequest request,
			HttpServletRequest serrequest) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.NOT_FOUND.toString(), e.getMessage(),
				serrequest.getLocalAddr());
	}
	
	@ExceptionHandler(TrainingException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleTrainingException(TrainingException e, WebRequest request, HttpServletRequest serrequest) {
		log.error(e.getMessage());
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
				serrequest.getLocalAddr());
	}

	@ExceptionHandler(TrainingNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleTrainingNoyFoundException(TrainingNotFoundException e, WebRequest request,
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
