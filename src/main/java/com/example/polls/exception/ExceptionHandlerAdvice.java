package com.example.polls.exception;


import com.example.polls.payload.MessageResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSecurityException(Exception e) {
        if (e instanceof SignatureException){
            return ResponseEntity.status(401).body(new MessageResponse("invalid token" + e.getMessage()));
        }
        if (e instanceof AccessDeniedException) {
            return ResponseEntity
                    .status(403)
                    .body(new MessageResponse("you don't have  access to  this resource"));
        }
      
        if (e instanceof ConstraintViolationException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MessageResponse("missing or invalid input")
            );
        }
        if (e instanceof BadCredentialsException) {
            return ResponseEntity
                    .status(401)
                    .body(new MessageResponse("Error: invalid username or password"));
        }


        if (e instanceof ResourceNotFoundException) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new MessageResponse( e.getMessage())
            );
        }


        if (e instanceof HttpMessageNotReadableException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MessageResponse("invalid JSON object")
            );
        }

        if( e instanceof InsufficientAuthenticationException){
            return ResponseEntity
                    .status(401)
                    .body(new MessageResponse("Error: you need to sign in first"));
        }


        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("error occurred"+ e.toString()));

    }



}
