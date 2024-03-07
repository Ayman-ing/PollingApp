package com.example.polls.service;

import com.example.polls.domain.entities.RoleEntity;
import com.example.polls.domain.entities.RoleName;
import com.example.polls.domain.entities.UserEntity;
import com.example.polls.payload.JwtResponse;
import com.example.polls.payload.MessageResponse;
import com.example.polls.repository.RoleRepository;
import com.example.polls.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.example.polls.domain.entities.RoleName.ROLE_ADMIN;
import static com.example.polls.domain.entities.RoleName.ROLE_USER;

@Service
public class AuthenticationService {
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder encoder;
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtService jwtService,
                                 UserRepository userRepository,
                                 PasswordEncoder encoder,
                                 RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<?> loginUser(String username, String password)   {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,
                        password));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return ResponseEntity.ok(JwtResponse.builder()
                .accessToken(jwtService.GenerateToken(username)).build());

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
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        // Create new user's account
        UserEntity user = new UserEntity(name, username,
                email,
                encoder.encode(password));

        Set<RoleEntity> roles = new HashSet<>();

        RoleEntity userRole = roleRepository.findByName(RoleName.ROLE_USER);
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse(true,"User registered successfully!"));
    }
}
