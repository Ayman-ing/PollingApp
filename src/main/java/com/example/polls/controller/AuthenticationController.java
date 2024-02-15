package com.example.polls.controller;

import com.example.polls.payload.LoginRequest;
import com.example.polls.payload.MessageResponse;
import com.example.polls.payload.RegisterRequest;
import com.example.polls.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping

public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;








    @PostMapping(path="/api/auth/login")
    public ResponseEntity<?> AuthenticationAndGetToken(@RequestBody LoginRequest loginRequest) {
        try {
            return authenticationService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MessageResponse("error")
            );
        }
    }

    @PostMapping(path="/api/auth/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        try {
            return authenticationService.registerUser(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    signUpRequest.getName(),
                    signUpRequest.getPassword()
            );
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MessageResponse("error")
            );
        }


    }
}



