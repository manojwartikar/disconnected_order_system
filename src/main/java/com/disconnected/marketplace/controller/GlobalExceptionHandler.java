package com.disconnected.marketplace.controller; 
 
import org.springframework.web.bind.MethodArgumentNotValidException; 
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.bind.annotation.RestControllerAdvice; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.validation.FieldError; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import java.util.HashMap; 
import java.util.Map; 
 
@RestControllerAdvice 
public class GlobalExceptionHandler { 
 
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class); 
 
    @ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) { 
        Map<String, String> errors = new HashMap<>(); 
        ex.getBindingResult().getAllErrors().forEach((error) -> { 
            String fieldName = ((FieldError) error).getField(); 
            String errorMessage = error.getDefaultMessage(); 
            errors.put(fieldName, errorMessage); 
        }); 
        logger.error("Validation error: {}", errors); 
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); 
    } 
 
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<String> handleAllExceptions(Exception ex) { 
        logger.error("An error occurred: ", ex); 
        return new ResponseEntity<>("An internal error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR); 
    } 
} 