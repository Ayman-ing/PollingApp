package com.example.polls.domain.entities;

import com.example.polls.domain.entities.audit.DateAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
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
    @ManyToMany(fetch = FetchType.LAZY) //the role entity will not be loaded from the db until there
                                        //refrenced in the code
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();
    //constructer without the id filed as it's auto generated
    public UserEntity(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }



}
