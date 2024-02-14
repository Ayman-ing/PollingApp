package com.example.polls.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {

    private String usernameOrEmail;
    private String password;
}

