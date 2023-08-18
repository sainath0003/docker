package com.epam.gymapp.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
	String timeStamp;
	String status;
	String error;
	String path;
	
}
