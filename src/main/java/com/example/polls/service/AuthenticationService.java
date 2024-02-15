package com.example.polls.service;

import com.example.polls.domain.entities.RoleEntity;
import com.example.polls.domain.entities.UserEntity;
import com.example.polls.payload.JwtResponse;
import com.example.polls.payload.MessageResponse;
import com.example.polls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    UserRepository userRepository;
    PasswordEncoder encoder;
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtService jwtService,
                                 UserRepository userRepository,
                                 PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public ResponseEntity<?> loginUser(String username, String password) {
        try {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,
                        password));
        SecurityContextHolder.getContext().setAuthentication(authentication);


            return ResponseEntity.ok(JwtResponse.builder()
                    .accessToken(jwtService.GenerateToken(username)).build());
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: invalid username or password"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new MessageResponse("error occuried")
            );

        }
    }

    public ResponseEntity<?> registerUser(String username, String email, String name, String password) {
        if (userRepository.existsByUsername(username)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserEntity user = new UserEntity(name, username,
                email,
                encoder.encode(password));

        Set<RoleEntity> roles = new HashSet<>();


        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
