package com.ms.email.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleValidationException(Exception ex, HttpServletRequest request) {
        String errorMessage = ex.getMessage();
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				HttpStatus.INTERNAL_SERVER_ERROR,
				500,
				errorMessage,
				request.getRequestURI()
				);  
        
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationMethodArgumen(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        
        ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				HttpStatus.BAD_REQUEST,
				400,
				errorMessage,
				request.getRequestURI()
				);  
        
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> handleValidationMethodArgumen(UnauthorizedException ex, HttpServletRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				HttpStatus.UNAUTHORIZED,
				401,
				ex.getMessage(),
				request.getRequestURI()
		);

		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}
}
