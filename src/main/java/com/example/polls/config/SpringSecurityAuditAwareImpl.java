package com.example.polls.config;

import com.example.polls.domain.CustomUserDetails;
import com.example.polls.domain.entities.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return Optional.ofNullable(customUserDetails.getId());
    }
}
