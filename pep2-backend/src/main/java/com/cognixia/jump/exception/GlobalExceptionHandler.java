package com.cognixia.jump.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

// advice controller on what to do when certain exceptions are thrown
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValid (MethodArgumentNotValidException ex, WebRequest request){

        StringBuilder errors = new StringBuilder("");
		for(FieldError fe : ex.getBindingResult().getFieldErrors()) {
			errors.append( "[" + fe.getField() + " : " + fe.getDefaultMessage() + "]; " );
		}
		
		// request.getDescription() -> details on the request (usually just includes the uri/url )
		ErrorDetails errorDetails = new ErrorDetails(new Date(), errors.toString(), request.getDescription(false) );

        return ResponseEntity.status(400).body(errorDetails);
    }
    
    // Custom exception handler 
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound( ResourceNotFoundException ex, WebRequest request){
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    	
    	return ResponseEntity.status(404).body(errorDetails);
    }
    
    // Another custom exception
    // This exception will be thrown when the topping's for a pizza exceeds 5
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> userNotValid(UserExistsException ex, WebRequest request){
    	ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    	
    	return ResponseEntity.status(404).body(errorDetails);
    }
}
