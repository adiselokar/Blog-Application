package com.blog.exceptions;

import com.blog.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<ApiResponse> resourseNotFoundExceptionHandler(ResourseNotFoundException ex){
        String messege = ex.getMessege();
        HttpStatus httpStatus = ex.getHttpStatus();
        ApiResponse apiResponse = new ApiResponse(messege, httpStatus, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String, String> response = new HashMap <>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error ->{
                    String fieldName = ((FieldError) error).getField();
                    String defaultMessage = error.getDefaultMessage();
                    response.put(fieldName, defaultMessage);
                });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiResponse> sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException ex){
        String message = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiResponse apiResponse = new ApiResponse(message, httpStatus, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiResponse> fileNotFoundExceptionHandler( FileNotFoundException ex )
    {
        String message = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiResponse apiResponse = new ApiResponse(message, httpStatus, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> badCredentialsExceptionHandler(BadCredentialsException ex)
    {
        String message = ex.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiResponse response = new ApiResponse(message, httpStatus, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
