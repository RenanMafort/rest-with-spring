package com.example.curseforbradesco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequireObjectIsNullException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public RequireObjectIsNullException(){
        super("It is not allowed to persist a null object!");
    }

    public RequireObjectIsNullException(String ex){
        super(ex);
    }



}
