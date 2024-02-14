package com.example.polls.controller;

import com.example.polls.domain.dto.AuthRequestDTO;
import com.example.polls.domain.dto.JwtResponseDTO;
import com.example.polls.domain.dto.MessageResponse;
import com.example.polls.domain.dto.RegisterRequestDTO;
import com.example.polls.domain.entities.RoleEntity;
import com.example.polls.domain.entities.UserEntity;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping

public class AuthController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @GetMapping("/")

    public String helloUserController(){
        return "User access level";
    }



    @PostMapping(path="/api/auth/login")
    public JwtResponseDTO AuthenticationAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        return authenticationService.loginUser(authRequestDTO.getUsernameOrEmail(), authRequestDTO.getPassword());

    }

    @PostMapping(path="/api/auth/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDTO signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserEntity user = new UserEntity(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<RoleEntity> roles = new HashSet<>();


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}



