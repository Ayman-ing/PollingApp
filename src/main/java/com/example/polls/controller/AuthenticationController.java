package com.example.polls.controller;

import com.example.polls.payload.LoginRequest;
import com.example.polls.payload.MessageResponse;
import com.example.polls.payload.RegisterRequest;
import com.example.polls.service.AuthenticationService;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping

public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;








    @PostMapping(path="/api/auth/login")
    public ResponseEntity<?> AuthenticationAndGetToken(@Valid @RequestBody LoginRequest loginRequest) throws ConstraintViolationException,HttpMessageNotReadableException,BadCredentialsException {

        return authenticationService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());



    }

    @PostMapping(path="/api/auth/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) throws ConstraintViolationException, HttpMessageNotReadableException {
            return authenticationService.registerUser(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    signUpRequest.getName(),
                    signUpRequest.getPassword()
            );



    }
}



