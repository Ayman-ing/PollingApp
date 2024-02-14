package com.example.polls.domain.dto;


import com.example.polls.domain.entities.RoleEntity;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RegisterRequestDTO {
    private String name;
    private String username;
    private String email;
    private String password;

}
