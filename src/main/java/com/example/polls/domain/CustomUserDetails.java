package com.example.polls.domain;

import com.example.polls.domain.entities.RoleEntity;
import com.example.polls.domain.entities.UserEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
public class CustomUserDetails extends UserEntity implements UserDetails {
    private String name;
    private String username;
    private String password;
    private String email;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserEntity byUsername) {
        this.username=byUsername.getUsername();
        this.name= byUsername.getName();
        this.password = byUsername.getPassword();
        this.email = byUsername.getEmail();
        List<GrantedAuthority> auths = new ArrayList<>();
        for(RoleEntity role: byUsername.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getName().toString().toUpperCase()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
