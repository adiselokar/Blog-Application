package com.blog.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@Data
public class ResourseNotFoundException extends RuntimeException {

    String messege;
    HttpStatus httpStatus;

    public ResourseNotFoundException( String messege, HttpStatus httpStatus ) {
        this.messege = messege;
        this.httpStatus = httpStatus;
    }
}
