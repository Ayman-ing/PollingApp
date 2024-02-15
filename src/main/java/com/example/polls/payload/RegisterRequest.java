package com.example.polls.payload;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegisterRequest {
    private String name;
    private String username;
    private String email;
    private String password;

}
