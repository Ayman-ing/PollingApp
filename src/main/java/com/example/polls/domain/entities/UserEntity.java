package com.example.polls.domain.entities;

import com.example.polls.domain.entities.audit.DateAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="users" , uniqueConstraints = {
        @UniqueConstraint(columnNames = { "username"}),
        @UniqueConstraint(columnNames = { "email"})
})
public class UserEntity extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max=40)
    private String name;
    @NotBlank
    @Size(max=15)
    private String username;
    @NotBlank
    @Size(max=40)
    @NaturalId // unique value that has a business significance
    @Email // make the field should match the emails pattern
    private String email;
    @NotBlank
    @Size(max = 100)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();
    //for normal users
    public UserEntity(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    //for the admin
    public UserEntity(String name, String username, String email, String password,Set<RoleEntity> roles) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles= roles;
    }



}
