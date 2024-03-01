package com.example.polls.exception;


import com.example.polls.payload.MessageResponse;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSecurityException(Exception e) {

        if (e instanceof SignatureException) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: invalid JWT "));
        }
        if (e instanceof BadCredentialsException) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: invalid username or password"));
        }


        if (e instanceof ResourceNotFoundException) {

            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
        if (e instanceof ConstraintViolationException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MessageResponse("missing or invalid credentiel")
            );
        }

        if (e instanceof HttpMessageNotReadableException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MessageResponse("invalid data")
            );
        }

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("error occurred"));

    }

}
